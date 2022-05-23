const Agency1 = require('../models/agency1')


updateuserprofil = async (req, res)=>{
    const picture = req.file
    const userid = req.cookies.userid
    const {name,email,password,location,sex,birthdayDate} = req.body;

    try {
        let newprofil
        if(typeof picture != 'undefined'){
            const {path} = picture
            newprofil = await Agency1.findOneAndUpdate({_id:userid},{name,picture:path,email,password,location,sex,birthdayDate},{
                new:true,
                runValidators:true
            })
               
        }else{
            newprofil = await Agency1.findOneAndUpdate({_id:userid},{name,email,password,location,sex,birthdayDate},{
                new:true,
                runValidators:true
            })
        }
        res.status(201).json({
            success:true,
            data:newprofil
        })


      
          }  catch (error) {
                res.status(400).json({ 
                    success:false,
                    message:error
                 })
            }
    
}

module.exports = {
    updateuserprofil
   }