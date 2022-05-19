const Quetions = require('../models/questions')
const Reply = require('../models/reply')
const Trips = require('../models/trips')
const res = require('express/lib/response');
const Likes = require('../models/Likes')
const Dislikes = require('../models/Dislikes')
const Story = require('../models/Story')

createlikequestion = async(req,res)=>{
    try {       
        const {questionid}= req.params
        const userid = req.cookies.userid
        const exist = await Likes.find({userid,questionid})
        if(Number(exist.length) == 1){
            const deletelike = await Likes.findOneAndDelete({userid,questionid })

            const nboflikes = await Likes.find({questionid})
            const updated = await Quetions.findOneAndUpdate({_id: questionid},{ likes : nboflikes.length},{
                runValidators:true,
                new:true
            })
            res.status(200).json({sucuss : true,
            data : deletelike,
            msg : "like deleted",
            question : updated})
        }else{
            const createlike = await Likes.create({questionid , userid })

            const nboflikes = await Likes.find({questionid})
            
    
            const updated = await Quetions.findOneAndUpdate({_id: questionid},{ likes : nboflikes.length},{
                runValidators:true,
                new:true
            })
            res.status(200).json({sucuss : true,
            data : createlike,
            msg : "like created",
            question : updated})
        }
       
    } catch (error) {
        res.status(400).json(error) 
    }
}

createdislikequestion = async(req,res)=>{
    try {       
        const {questionid}= req.params
        const userid = req.cookies.userid
        const exist = await Dislikes.find({userid,questionid})
        if(Number(exist.length) == 1){
            const deletedislike = await Dislikes.findOneAndDelete({userid ,questionid })

            const nbofdislikes = await Dislikes.find({questionid})
            const updated = await Quetions.findOneAndUpdate({_id: questionid},{ dislikes : nbofdislikes.length},{
                runValidators:true,
                new:true
            })
            res.status(200).json({sucuss : true,
            data : deletedislike,
            msg : "dislike deleted",
            question : updated})
        }else{
            const createdislike = await Dislikes.create({questionid , userid })

            const nbofdislikes = await Dislikes.find({questionid})
            
    
            const updated = await Quetions.findOneAndUpdate({_id: questionid},{ dislikes : nbofdislikes.length},{
                runValidators:true,
                new:true
            })
            res.status(200).json({sucuss : true,
            data : createdislike,
            msg : "dislike created",
            question : updated})
        }
       
    } catch (error) {
        res.status(400).json(error) 
    }
}

createlikereply = async(req,res)=>{
    try {       
        const {replyid}= req.params
        const userid = req.cookies.userid
        const exist = await Likes.find({userid,replyid})
        if(Number(exist.length) == 1){
            const deletelike = await Likes.findOneAndDelete({userid , replyid})

            const nboflikes = await Likes.find({replyid})
            const updated = await Reply.findOneAndUpdate({_id: replyid},{ likes : nboflikes.length},{
                runValidators:true,
                new:true
            })
            res.status(200).json({sucuss : true,
            data : deletelike,
            msg : "like deleted",
            question : updated})
        }else{
            const createlike = await Likes.create({replyid , userid })

            const nboflikes = await Likes.find({replyid})
            
    
            const updated = await Reply.findOneAndUpdate({_id: replyid},{ likes : nboflikes.length},{
                runValidators:true,
                new:true
            })
            res.status(200).json({sucuss : true,
            data : createlike,
            msg : "like created",
            question : updated})
        }
       
    } catch (error) {
        res.status(400).json(error) 
    }
}

createdislikereply = async(req,res)=>{
    try {       
        const {replyid}= req.params
        const userid = req.cookies.userid
        const exist = await Dislikes.find({userid,replyid})
        if(Number(exist.length) == 1){
            const deletedislike = await Dislikes.findOneAndDelete({userid , replyid})

            const nbofdislikes = await Dislikes.find({replyid})
            const updated = await Reply.findOneAndUpdate({_id: replyid},{ dislikes : nbofdislikes.length},{
                runValidators:true,
                new:true
            })
            res.status(200).json({sucuss : true,
            data : deletedislike,
            msg : "dislike deleted",
            reply : updated})
        }else{
            const createdislike = await Dislikes.create({replyid , userid })

            const nbofdislikes = await Dislikes.find({replyid})
            
    
            const updated = await Reply.findOneAndUpdate({_id: replyid},{ dislikes : nbofdislikes.length},{
                runValidators:true,
                new:true
            })
            res.status(200).json({sucuss : true,
            data : createdislike,
            msg : "dislike created",
            reply : updated})
        }
       
    } catch (error) {
        res.status(400).json(error) 
    }
}

createliketrip = async(req,res)=>{
    try {       
        const {tripid}= req.params
        const userid = req.cookies.userid
        const exist = await Likes.find({userid,tripid})
        if(Number(exist.length) == 1){
            const deletelike = await Likes.findOneAndDelete({userid ,tripid })

            const nboflikes = await Likes.find({tripid})
            const updated = await Trips.findOneAndUpdate({_id: tripid},{ likes : nboflikes.length},{
                runValidators:true,
                new:true
            })
            res.status(200).json({sucuss : true,
            data : deletelike,
            msg : "like deleted",
            trip : updated})
        }else{
            const createlike = await Likes.create({tripid , userid })

            const nboflikes = await Likes.find({tripid})
            
    
            const updated = await Trips.findOneAndUpdate({_id: tripid},{ likes : nboflikes.length},{
                runValidators:true,
                new:true
            })
            res.status(200).json({sucuss : true,
            data : createlike,
            msg : "like created",
            trip : updated})
        }
       
    } catch (error) {
        res.status(400).json(error) 
    }
}

createdisliketrip = async(req,res)=>{
    try {       
        const {tripid}= req.params
        const userid = req.cookies.userid
        const exist = await Dislikes.find({userid,tripid})
        if(Number(exist.length) == 1){
            const deletedislike = await Dislikes.findOneAndDelete({userid ,tripid})

            const nbofdislikes = await Dislikes.find({tripid})
            const updated = await Trips.findOneAndUpdate({_id: tripid},{ dislikes : nbofdislikes.length},{
                runValidators:true,
                new:true
            })
            res.status(200).json({sucuss : true,
            data : deletedislike,
            msg : "dislike deleted",
            Trips : updated})
        }else{
            const createdislike = await Dislikes.create({tripid , userid })

            const nbofdislikes = await Dislikes.find({tripid})
            
    
            const updated = await Trips.findOneAndUpdate({_id: tripid},{ dislikes : nbofdislikes.length},{
                runValidators:true,
                new:true
            })
            res.status(200).json({sucuss : true,
            data : createdislike,
            msg : "dislike created",
            Trips : updated})
        }
       
    } catch (error) {
        res.status(400).json(error) 
    }
}

createliketripreply = async(req,res)=>{
    try {       
        const {replyid}= req.params
        const userid = req.cookies.userid
        const exist = await Likes.find({userid,replyid})
        if(Number(exist.length) == 1){
            const deletelike = await Likes.findOneAndDelete({userid ,replyid })

            const nboflikes = await Likes.find({replyid})
            const updated = await Reply.findOneAndUpdate({_id: replyid},{ likes : nboflikes.length},{
                runValidators:true,
                new:true
            })
            res.status(200).json({sucuss : true,
            data : deletelike,
            msg : "like deleted",
            reply : updated})
        }else{
            const createlike = await Likes.create({replyid , userid })

            const nboflikes = await Likes.find({replyid})
            
    
            const updated = await Reply.findOneAndUpdate({_id: replyid},{ likes : nboflikes.length},{
                runValidators:true,
                new:true
            })
            res.status(200).json({sucuss : true,
            data : createlike,
            msg : "like created",
            reply : updated})
        }
       
    } catch (error) {
        res.status(400).json(error) 
    }
}

createdisliketripreply = async(req,res)=>{
    try {       
        const {replyid}= req.params
        const userid = req.cookies.userid
        const exist = await Dislikes.find({userid,replyid})
        if(Number(exist.length) == 1){
            const deletedislike = await Dislikes.findOneAndDelete({userid ,replyid})

            const nbofdislikes = await Dislikes.find({replyid})
            const updated = await Reply.findOneAndUpdate({_id: replyid},{ dislikes : nbofdislikes.length},{
                runValidators:true,
                new:true
            })
            res.status(200).json({sucuss : true,
            data : deletedislike,
            msg : "dislike deleted",
            Reply : updated})
        }else{
            const createdislike = await Dislikes.create({replyid , userid })

            const nbofdislikes = await Dislikes.find({replyid})
            
    
            const updated = await Reply.findOneAndUpdate({_id: replyid},{ dislikes : nbofdislikes.length},{
                runValidators:true,
                new:true
            })
            res.status(200).json({sucuss : true,
            data : createdislike,
            msg : "dislike created",
            Reply : updated})
        }
       
    } catch (error) {
        res.status(400).json(error) 
    }
}

createlikepicture = async(req,res)=>{
    try {       
        const {idPicture}= req.params
        const userid = req.cookies.userid
        const exist = await Likes.find({userid,pictureid: idPicture})
        if(Number(exist.length) == 1){
            const deletelike = await Likes.findOneAndDelete({userid,pictureid: idPicture })

            const nboflikes = await Likes.find({pictureid: idPicture})
            const updated = await Story.findOneAndUpdate({_id: idPicture},{ like : nboflikes.length},{
                runValidators:true,
                new:true
            })
            res.status(200).json({sucuss : true,
            data : deletelike,
            msg : "like deleted",
            storyPicture : updated})
        }else{
            const createlike = await Likes.create({pictureid: idPicture , userid })

            const nboflikes = await Likes.find({pictureid: idPicture})
            const updated = await Story.findOneAndUpdate({_id: idPicture},{ like : nboflikes.length},{
                runValidators:true,
                new:true
            })
            res.status(200).json({sucuss : true,
            data : createlike,
            msg : "like created",
            storyPicture : updated})
        }
       
    } catch (error) {
        res.status(400).json(error) 
    }
}

createdislikepicture = async(req,res)=>{
    try {       
        const {idPicture}= req.params
        const userid = req.cookies.userid
        const exist = await Dislikes.find({userid,pictureid: idPicture})
        if(Number(exist.length) == 1){
            const deletedislike = await Dislikes.findOneAndDelete({userid ,pictureid : idPicture })

            const nbofdislikes = await Dislikes.find({pictureid: idPicture})
            const updated = await Story.findOneAndUpdate({_id: idPicture},{ dislike : nbofdislikes.length},{
                runValidators:true,
                new:true
            })
            console.log(updated);
            res.status(200).json({sucuss : true,
            data : deletedislike,
            msg : "dislike deleted",
            storyPicture : updated})
        }else{
            const createdislike = await Dislikes.create({pictureid: idPicture , userid })

            const nbofdislikes = await Dislikes.find({pictureid: idPicture})
            
    
            const updated = await Story.findOneAndUpdate({_id: idPicture},{ dislike : nbofdislikes.length},{
                runValidators:true,
                new:true
            })
            res.status(200).json({sucuss : true,
            data : createdislike,
            msg : "dislike created",
            storyPicture : updated})
        }
       
    } catch (error) {
        res.status(400).json(error) 
    }
}


module.exports = {
    createlikequestion,
    createdislikequestion,
    createlikereply,
    createdislikereply,
    createliketrip,
    createdisliketrip,
    createliketripreply,
    createdisliketripreply,
    createlikepicture,
    createdislikepicture
}