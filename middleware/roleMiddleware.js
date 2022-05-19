const user = require('../models/agency1')

const requireAgency = async (req,res,next)=>{
    try {
        const userid = req.cookies.userid
        const theuser = await user.findOne({_id: userid})
          const role = theuser.role
        if(role == 'Agency'){
            next()
        }else{
            res.status(200).json({
                error: "Only agency acount can post trips.."})
        }
    } catch (error) {
       res.status(400).json({error}) 
    }
  
}

module.exports ={requireAgency}