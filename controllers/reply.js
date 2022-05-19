const Reply = require('../models/reply')
const jwt = require('jsonwebtoken');
const res = require('express/lib/response');
const agency1 = require('../models/agency1');
const questions = require('../models/questions');




//handling errors
const handleErrors = (error)=>{
    console.log(error.message , error.code)
    let err = {text:''};
    //validation errors
    if (error.message.includes('Validation failed')){
        Object.values(error.errors).forEach( ({properties}) => {
            err[properties.path] = properties.message;
        });
    }
  return err;
}


getReply = async(req,res)=>{
    const {questionId}= req.params
  const reply = await Reply.find({questionId})
    res.status(200).json({reply , nbHits : reply.length})
}
createReply = async(req,res)=>{
    try {
        const image = req.file
        const {questionId}= req.params
        const {text} = req.body
        const userid = req.cookies.userid
        const userinfo = await agency1.findOne({_id: userid})
        let newreply
        
        if(typeof image != 'undefined'){
            const {path} = image
        newreply = await Reply.create({text, questionId , userid , picture: path , userlocation: userinfo.location , userpicture : userinfo.picture, username : userinfo.name})
        }else{
        newreply = await Reply.create({text, questionId , userid , userlocation: userinfo.location , userpicture : userinfo.picture, username : userinfo.name})
        }
        var flag = 1
        const questionupdated = await questions.findOneAndUpdate({_id:questionId},{$inc: {
            replynumber: flag
          }},{
            new:true,
            runValidators:true
        })
        res.status(200).json({
            success: true,
            data: {
                "text": newreply.text,
                "picture": newreply.picture,
                "tags": newreply.tags,
                "username": newreply.username,
                "userlocation": newreply.userlocation,
                "userpicture": newreply.userpicture,
                "likes": newreply.likes,
                "dislikes": newreply.dislikes,
                "replyDate": newreply.replyDate,
                "questionId": newreply.questionId
            },
            question: questionupdated
        })
    } catch (error) {
        const err = handleErrors(error)
        res.status(400).json({err})
    }
    
}
getsinglereply = async (req,res)=>{
    try {
        const {replyId} = req.params
        const thereply = await Reply.findOne({_id:replyId})
         if (thereply){
             console.log('reply find..');
           return  res.status(200).json({
                                data: {
                                    "text": newreply.text,
                                    "picture": newreply.picture,
                                    "tags": newreply.tags,
                                    "username": newreply.username,
                                    "userlocation": newreply.userlocation,
                                    "userpicture": newreply.userpicture,
                                    "likes": newreply.likes,
                                    "dislikes": newreply.dislikes,
                                    "replyDate": newreply.replyDate,
                                    "questionId": newreply.questionId
                                }})
         }
         res.status(400).json('no reply with id :'+ replyId)
    } catch (error) {
        const err = handleErrors(error)
        res.status(400).json({err})
    }
}
 deleteReply= async(req,res)=>{
    try {
        const {questionId,replyId}= req.params
        console.log(questionId,replyId);
        const deleted = await Reply.findOneAndDelete({_id: replyId})
         if(!deleted){
             return res.status(400).json('no reply with id :'+ replyId)
         }
         var flag = -1
         const questionupdated = await questions.findOneAndUpdate({_id:questionId},{$inc: {
            replynumber: flag
          }},{
            new:true,
            runValidators:true
        })
         res.status(200).json({
             succuss : true,
             reply: deleted,
             question : questionupdated
         })
    } catch (error) {
        const err = handleErrors(error)
        res.status(400).json({err})
    }
}
updateReply = async(req,res)=>{
    try {
        const {replyId}= req.params
        const image = req.file
        const {text } = req.body
        let updated
        if(typeof image != 'undefined'){
            const {path} = image
            updated = await Reply.findOneAndUpdate({_id: replyId},{text, picture:path},{
                runValidators:true,
                new:true
            })
        }else{
            updated = await Reply.findOneAndUpdate({_id: replyId},{text , picture: '' },{
                runValidators:true,
                new:true
            })
        }
       
         if(!updated){
             return res.status(400).json({msg:'no reply with id :'+ replyId})
         }
         res.status(200).json({
            data: {
                "text": newreply.text,
                "picture": newreply.picture,
                "tags": newreply.tags,
                "username": newreply.username,
                "userlocation": newreply.userlocation,
                "userpicture": newreply.userpicture,
                "likes": newreply.likes,
                "dislikes": newreply.dislikes,
                "replyDate": newreply.replyDate,
                "questionId": newreply.questionId
            }
         })
    } catch (error) {
        const err = handleErrors(error)
        res.status(400).json({err})
    }
}

module.exports = {getReply,
    createReply,
    updateReply,
    deleteReply,
    getsinglereply
}