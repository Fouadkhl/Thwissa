const Reviews = require('../models/reviews')
const res = require('express/lib/response');
const agency1 = require('../models/agency1')
//handling errors
const handleErrors = (error)=>{
    console.log(error.message , error.code);
    let err = {text:''};
     //text error
     if(error.message === 'reviews validation failed'){
        err.text= 'please provid a text in your review..'
    }
    //validation errors
    if (error.message.includes('reviews validation failed')){
        Object.values(error.errors).forEach( ({properties}) => {
            err[properties.path] = properties.message;
        });
    }
  return err;
}


getReviews = async(req,res)=>{
    const {agencyId}= req.params
    console.log(agencyId);
    const reviews = await Reviews.find({agencyId}).sort("reviewDate")
      res.status(200).json({reviews , nbHits: reviews.length})
}
createReviews = async(req,res)=>{
    try {
        const {agencyId}= req.params
        const {text , rate} = req.body
        const userid = req.cookies.userid
        const userinfo = await agency1.findOne({_id: userid})
        const update = await agency1.findOneAndUpdate({_id: agencyId},{rate},{
            runValidators:true,
            new:true
        })
        const newreview = await Reviews.create({text, rate , username: userinfo.name , userlocation : userinfo.location , userpicture : userinfo.picture, agencyId})
    console.log('creation succes..');
    res.status(200).json({
            sucuss:true,
            data: newreview
    })
    } catch (error) {
        const err = handleErrors(error)
        res.status(400).json({err})
    }
    
}

 deleteReviews= async(req,res)=>{
    try {
        const {id: reviewid}= req.params
        const deleted = await Reviews.findOneAndDelete({_id: reviewid})
         if(!deleted){
             return res.status(400).json({msg:'no review with id :'+ reviewid})
         }
         res.status(200).json({deleted})
    } catch (error) {
        const err = handleErrors(error)
        res.status(400).json({err})
    }
}
updateReviews = async(req,res)=>{
    try {
        const {id: reviewid , agencyId}= req.params
        const {text , rate} = req.body
        const update = await agency1.findOneAndUpdate({_id: agencyId},{rate},{
            runValidators:true,
            new:true
        })
        const updated = await Reviews.findOneAndUpdate({_id: reviewid},{text , rate},{
            runValidators:true,
            new:true
        })
       
         if(!updated){
             return res.status(400).json({msg:'no review with id :'+ reviewid})
         }
         res.status(200).json({updated})
    } catch (error) {
        const err = handleErrors(error)
        res.status(400).json({err})
    }
}

module.exports = {getReviews,
    createReviews,
    updateReviews,
    deleteReviews
}