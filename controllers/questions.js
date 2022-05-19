const Quetions = require('../models/questions')
const res = require('express/lib/response');
const agency1 = require('../models/agency1')

//handling errors
const handleErrors = (error)=>{
    console.log(error.message , error.code);
    let err = {text:''};
    //validation errors
    if (error.message.includes('questions validation failed')){
        Object.values(error.errors).forEach( ({properties}) => {
            err[properties.path] = properties.message;
        });
    }
  return err;
}


getQuestions = async(req,res)=>{
   let { search} = req.query
  const queryobject = {}
  const userid = req.cookies.userid
  const userinfo = await agency1.findOne({_id: userid})
  let sortList
  if(search){
    if(search.charAt(0)=== '@'){
        search = search.substring(1)
        queryobject.tags = { $regex: search, $options: 'i' }
    }else{
        queryobject.text = { $regex: search, $options: 'i' };
    }
    let sort = ('likes','dislikes','questionDate')
    sortList = sort.split(',').join(' ')
  }else{
   // queryobject.tags = userinfo.location
    sortList = 'questionDate'
  }
  
  const questions = await Quetions.find(queryobject).sort(sortList)
    res.status(200).json({questions , nbHits: questions.length })
}
createQuestions = async(req,res)=>{
    try {
        const picture = req.file
        const {text } = req.body
        const tags = req.body.tags.replace(/\s/g,'').split(',')
        const userid = req.cookies.userid
        const userinfo = await agency1.findOne({_id: userid})
        let newquestion
        if(typeof picture != 'undefined'){
            const {path} = picture
            newquestion = await Quetions.create({text, picture: path , tags, username: userinfo.name , userlocation : userinfo.location , userpicture : userinfo.picture})
        }else{
            newquestion = await Quetions.create({text, tags, username: userinfo.name , userlocation : userinfo.location , userpicture : userinfo.picture})
        }
    console.log('creation succes..');
    res.status(200).json({
            sucuss:true,
            data: {
                "text": newquestion.text,
                "picture": newquestion.picture,
                "tags": newquestion.tags,
                "username": newquestion.username,
                "userlocation": newquestion.userlocation,
                "userpicture": newquestion.userpicture,
                "likes": newquestion.likes,
                "dislikes": newquestion.dislikes,
                "replynumber": newquestion.replynumber,
                "questionDate": newquestion.questionDate
            }
    })
    } catch (error) {
        const err = handleErrors(error)
        res.status(400).json({err})
    }
    
}
getsinglequestion = async (req,res)=>{
    try {
        const {id:questionid} = req.params
        const newquestion = await Quetions.findOne({_id:questionid})
         if (newquestion){
             console.log('question find..');
           return   res.status(200).json({
            sucuss:true,
            data: {
                "text": newquestion.text,
                "picture": newquestion.picture,
                "tags": newquestion.tags,
                "username": newquestion.username,
                "userlocation": newquestion.userlocation,
                "userpicture": newquestion.userpicture,
                "likes": newquestion.likes,
                "dislikes": newquestion.dislikes,
                "replynumber": newquestion.replynumber,
                "questionDate": newquestion.questionDate
            }
           })
         }
         res.status(400).json({msg:'no question with id :'+ questionid})
    } catch (error) {
        const err = handleErrors(error)
        res.status(400).json({err})
    }
}
 deleteQuestions= async(req,res)=>{
    try {
        const {id: questionid}= req.params
        const deleted = await Quetions.findOneAndDelete({_id: questionid})
         if(!deleted){
             return res.status(400).json({msg:'no question with id :'+ questionid})
         }
         res.status(200).json({deleted})
    } catch (error) {
        const err = handleErrors(error)
        res.status(400).json({err})
    }
}
updateQuestions = async(req,res)=>{
    try {
        const {id: questionid}= req.params
        const image = req.file
        const {text, tags } = req.body
        let updated
        if(typeof image != 'undefined'){
            const {path} = image
            updated = await Quetions.findOneAndUpdate({_id: questionid},{text, tags , picture:path},{
                runValidators:true,
                new:true
            })
        }else{
            updated = await Quetions.findOneAndUpdate({_id: questionid},{text , tags , picture: '' },{
                runValidators:true,
                new:true
            })
        }
       
         if(!updated){
             return res.status(400).json({msg:'no question with id :'+ questionid})
         }
         res.status(200).json({
             sucuss: true,
             data: {
                "text": newquestion.text,
                "picture": newquestion.picture,
                "tags": newquestion.tags,
                "username": newquestion.username,
                "userlocation": newquestion.userlocation,
                "userpicture": newquestion.userpicture,
                "likes": newquestion.likes,
                "dislikes": newquestion.dislikes,
                "replynumber": newquestion.replynumber,
                "questionDate": newquestion.questionDate
        }})
    } catch (error) {
        const err = handleErrors(error)
        res.status(400).json({err})
    }
}



module.exports = {getQuestions,
    createQuestions,
    updateQuestions,
    deleteQuestions,
    getsinglequestion
}