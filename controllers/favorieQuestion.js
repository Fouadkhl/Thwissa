const res = require('express/lib/response');
const Favorie = require('../models/favorieQuestions');
const Questions = require('../models/questions');



favoriequestion = async(req,res)=>{
    try {
        const {questionid} = req.params
        const userid = req.cookies.userid
        const exist = await Favorie.find({userid,questionid})
        if(Number(exist.length) == 1){
            const deletefav = await Favorie.findOneAndDelete({userid,questionid })
           
            res.status(200).json({sucuss : true,
            data : deletefav,
            msg : "delete fav sucuss"
            })
        }else{
            console.log(questionid);
            const question = await Questions.findOne({_id:questionid})
            
            const createfav = await Favorie.create({questionid , userid , type: 'question',text: question.text,picture: question.picture , 
                                                    tags: question.tags , username: question.username ,questiondate: question.questiondate,
                                                    userlocation : question.userlocation , userpicture : question.userpicture,
                                                    likes: question.likes, dislikes: question.dislikes,replynumber: question.replynumber
                                                     })
            res.status(200).json({sucuss : true,
            data : createfav,
            question : question,
            msg : "create fav sucuss"
            })
        }
       
    
} catch (error) {
    res.status(400).json(error) 
}


}






module.exports = {
    favoriequestion
}