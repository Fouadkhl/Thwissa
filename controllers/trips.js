const Trips = require('../models/trips')
const res = require('express/lib/response');
const agency1 = require('../models/agency1')
const Favorie = require('../models/favorieTrips');
const favorie = require('../models/favorieTrips');
//handling errors
const handleErrors = (error)=>{
    console.log(error.message , error.code);
    let err = {text:''};
    //validation errors
    if (error.message.includes('reviews validation failed')){
        Object.values(error.errors).forEach( ({properties}) => {
            err[properties.path] = properties.message;
        });
    }
  return err;
}


/*getTrips = async(req,res)=>{
    const {agencyId}= req.params
    const trips = await Trips.findOne({agencyId})
      res.status(200).json(trips)
}
createTrips = async(req,res)=>{
    try {
        const {agencyId}= req.params
        const {date , duration , price , meetingplace , destination} = req.body
        const newtrip = await Trips.create({date , duration , price , meetingplace , destination})
    console.log('creation succes..');
    res.status(200).json({
            sucuss:true,
            data: newtrip
    })
    } catch (error) {
        const err = handleErrors(error)
        res.status(400).json({err})
    }
    
}

 deleteTrips= async(req,res)=>{
    try {
        const {id: tripid}= req.params
        const deleted = await Trips.findOneAndDelete({_id: tripid})
         if(!deleted){
             return res.status(400).json({msg:'no trip with id :'+ tripid})
         }
         res.status(200).json({deleted})
    } catch (error) {
        const err = handleErrors(error)
        res.status(400).json({err})
    }
}
updateTrips = async(req,res)=>{
    try {
        const {id: tripid}= req.params
        const {date , duration , price , meetingplace , destination} = req.body
        const updated = await Trips.findOneAndUpdate({_id: tripid},{date , duration , price , meetingplace , destination},{
            runValidators:true,
            new:true
        })
       
         if(!updated){
             return res.status(400).json({msg:'no trip with id :'+ tripid})
         }
         res.status(200).json({updated})
    } catch (error) {
        const err = handleErrors(error)
        res.status(400).json({err})
    }
}*/


getTrips = async(req,res)=>{
    let { search} = req.query
   const queryobject = {}
   const userid = req.cookies.userid
   const userinfo = await agency1.findOne({_id: userid})
   
   if(search){
     if(search.charAt(0)=== '@'){
         search = search.substring(1)
         queryobject.tags = { $regex: search, $options: 'i' }
     }else{
         queryobject.text = { $regex: search, $options: 'i' };
     }
    /* let sort = ('likes','dislikes','questionDate')
     let sortList = sort.split(',').join(' ')*/
   }else{
     //queryobject.tags = userinfo.location
     sortList = 'tripDate'
   }
   
 
   
   const trips = await Trips.find(queryobject).sort(sortList)
     res.status(200).json({trips , nbHits: trips.length})
 }
 createTrips = async(req,res)=>{
     try {
         const picture = req.file
         
         const {text ,  date , duration , price , meetingplace , destination} = req.body
         
         const tags = req.body.tags.replace(/\s/g,'').split(',')
         const userid = req.cookies.userid
         const userinfo = await agency1.findOne({_id: userid})
         let newtrip
         if(typeof picture != 'undefined'){
            const {path} = picture
             newtrip = await Trips.create({text, picture : path , tags, username: userinfo.name ,
                 userlocation : userinfo.location , userpicture : userinfo.picture,
                 date , minduration:duration , maxduration:duration , minprice: price , maxprice: price , meetingplace , destination})
         }else{
            newtrip = await Trips.create({text, tags, username: userinfo.name ,
                userlocation : userinfo.location , userpicture : userinfo.picture,
                date , minduration:duration , maxduration:duration , minprice: price , maxprice: price , meetingplace , destination})
         }
     console.log('creation succes..');
     res.status(200).json({
             sucuss:true,
             data: newtrip
     })
     } catch (error) {
         const err = handleErrors(error)
         res.status(400).json({err})
     }
     
 }
 getsingleTrip = async (req,res)=>{
     try {
         const {tripid} = req.params
         const thetrip = await Trips.findOne({_id:tripid})
          if (thetrip){
              console.log('question find..');
            return   res.status(200).json({
             sucuss:true,
             data:thetrip
            })
          }
          res.status(400).json({msg:'no trip with id :'+ tripid})
     } catch (error) {
         const err = handleErrors(error)
         res.status(400).json({err})
     }
 }
  deleteTrips= async(req,res)=>{
     try {
         const {tripid}= req.params
         const deleted = await Trips.findOneAndDelete({_id: tripid})
         const favupdate = await favorie.findOneAndDelete({tripid})
          if(!deleted){
              return res.status(400).json({msg:'no trip with id :'+ tripid})
          }
          res.status(200).json({deleted,
        favupdate})
     } catch (error) {
         const err = handleErrors(error)
         res.status(400).json({err})
     }
 }
 updateTrips = async(req,res)=>{
     try {
         const {tripid}= req.params
         const image = req.file
         const {text, likes ,dislikes, tags, date , duration , price , meetingplace , destination } = req.body
         let updated
         if(typeof image != 'undefined'){
             const {path} = image
             updated = await Trips.findOneAndUpdate({_id: tripid},{text, likes, dislikes , tags , picture:path , date , minduration:duration , maxduration:duration ,  minprice: price , maxprice: price , meetingplace , destination},{
                 runValidators:true,
                 new:true
             })
         }else{
             updated = await Trips.findOneAndUpdate({_id: tripid},{text, likes, dislikes , tags , picture: '' , date , minduration:duration , maxduration:duration ,  minprice: price , maxprice: price , meetingplace , destination },{
                 runValidators:true,
                 new:true
             })
         }
        
          if(!updated){
              return res.status(400).json({msg:'no trip with id :'+ tripid})
          }
          res.status(200).json({updated})
     } catch (error) {
         const err = handleErrors(error)
         res.status(400).json({err})
     }
 }

module.exports = {getTrips,
    createTrips,
    updateTrips,
    deleteTrips,
    getsingleTrip
}