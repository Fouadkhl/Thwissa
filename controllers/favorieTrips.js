const res = require('express/lib/response');
const Favorie = require('../models/favorieTrips');
const trips = require('../models/trips');


favorietrip = async(req,res)=>{
    try {
        const {tripid} = req.params
        const userid = req.cookies.userid
        const exist = await Favorie.find({userid,tripid})
        if(Number(exist.length) == 1){
            const deletefav = await Favorie.findOneAndDelete({userid,tripid })
           
            res.status(200).json({sucuss : true,
            data : deletefav,
            msg : "delete fav sucuss"
            })
        }else{
            console.log(tripid);
            const trip = await trips.findOne({_id:tripid})
            
            const createfav = await Favorie.create({tripid , userid , type: 'trip',text: trip.text,picture: trip.picture , 
                                                    tags: trip.tags , username: trip.username ,destination: trip.destination,
                                                    userlocation : trip.userlocation , userpicture : trip.userpicture,
                                                    date: trip.date , duration: trip.maxduration ,price: trip.maxprice,
                                                    meetingplace :trip.meetingplace , likes: trip.likes, dislikes: trip.dislikes,
                                                    replynumber: trip.replynumber, tripdate: trip.tripdate})
            res.status(200).json({sucuss : true,
            data : createfav,
            trip : trip,
            msg : "create fav sucuss"
            })
        }
       
    
} catch (error) {
    res.status(400).json(error) 
}
}



getFavorie = async(req,res)=>{
   try {
    const userid = req.cookies.userid
    const {type}= req.query
    if(type == 'question'){
        const questionFav = await Favorie.find({userid})
        res.status(200).json({questionFav , nbHits : questionFav.length})
    }else if(type == 'trip'){
        const tripsFav = await Favorie.find({userid})
        res.status(200).json({tripsFav , nbHits : tripsFav.length})
    }
   } catch (error) {
       res.status(400).json(error)
   } 
  
}




module.exports = {
    favorietrip,
    getFavorie
}