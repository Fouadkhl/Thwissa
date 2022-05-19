const res = require('express/lib/response');
const Agency1 = require('../models/agency1')
const Follow = require('../models/follow')

Following = async(req,res)=>{
    try {
        const {agencyid} = req.params
        const userid = req.cookies.userid
        const exist = await Follow.find({userid,agencyid})
        if(Number(exist.length) == 1){
            const infollow = await Follow.findOneAndDelete({userid,agencyid })

            const nbofFollow = await Follow.find({agencyid})
            const updated = await Agency1.findOneAndUpdate({_id: agencyid},{ nbfollowers : nbofFollow.length},{
                runValidators:true,
                new:true
            })
            res.status(200).json({sucuss : true,
            data : infollow,
            msg : "infollow sucuss",
            Agency : updated})
        }else{
            const createFollow = await Follow.create({agencyid , userid })

            const nbofFollow = await Follow.find({agencyid})
            
    
            const updated = await Agency1.findOneAndUpdate({_id: agencyid},{ nbfollowers : nbofFollow.length},{
                runValidators:true,
                new:true
            })
            res.status(200).json({sucuss : true,
            data : createFollow,
            msg : "Follow sucuss",
            Agency : updated})
        }
       
    
} catch (error) {
    res.status(400).json(error) 
}
}



module.exports = {Following}