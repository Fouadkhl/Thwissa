const Agency1 = require('../models/agency1')
const jwt = require('jsonwebtoken');
const res = require('express/lib/response');
const nodemailer = require('nodemailer')
const UserOTPVerification =require('../models/UserOTPVerification')
const config = require("../config/auth.config");
const bcrypt = require('bcrypt')

const user = config.user;
const pass = config.pass;

const transport = nodemailer.createTransport({
  service: "Gmail",
  auth: {
    user: user,
    pass: pass,
  },
});



//handling errors
const handleErrors = (error)=>{
    console.log(error.message , error.code );
    let err = {email:'', password:'' , phonenumber:'', confirmepassword:'', location:'', name:''};
    if(error.message === 'confirme password incorrect'){
        err.confirmepassword= 'confirme password incorrect'
    }
    //validation errors
    if (error.message.includes('user validation failed')){
        Object.values(error.errors).forEach( ({properties}) => {
            err[properties.path] = properties.message;
        });
    }
    if (error.message.includes('agency1 validation failed')){
        Object.values(error.errors).forEach( ({properties}) => {
            err[properties.path] = properties.message;
        });
    }
  return err;
}
//json web token
const creatToken = (id)=>{
    return jwt.sign({id},process.env.Secret,{
        expiresIn : 3*24*60*60,
    })
}





// send otp verification email
const sendOTPVerificationEmail = async ({_id, email, name},res)=>{
    try {
        const otp = `${Math.floor(1000 + Math.random()*9000)}`
        
       const mailoption={
        from: user,
        to: email,
        subject: "Password recuperation",
        html: `<h1>password recuperation</h1>
            <h2>Hello ${name}</h2>
            <p>please enter <b>${otp}</b> in the app to verify your email adress and complete the recuperation of the password
            <p>This code <b>expires in 1 hour</b>.</p>`,
       }

       //hash the otp
       const seltRounds = 10;

       const hashedOTP= await bcrypt.hash(otp, seltRounds)
       
       const jj = await UserOTPVerification.create({
           userid: _id,
           otp: hashedOTP,
           createdAt: Date.now(),
           expiresAt: Date.now()+ 3600000
       })
       
       // save otp record 
      
       const gg = await transport.sendMail(mailoption);
      
       res.status(200).json({
           status : "PENDING",
           message: "Verification otp email sent",
           data: {
               userid : _id ,
               email
           }
       })
    } catch (error) {
        res.status(400).json({
            status: "FAILED",
            error,
        })
       
    }
}

validateemail= async (req,res)=>{
    try {
        const {otp,userid} = req.body
         if(!otp || !userid){
             throw Error("empty otp details are not allowed")
         }else{
            const UserOTPVerificationRecords = await UserOTPVerification.find({userid})
              if(UserOTPVerificationRecords.length <=0){
                  throw new Error("account records doesn't exist or has been verifed already . please sign up or log in")
              }else{
                  const hashedotp = UserOTPVerificationRecords[0].otp
                  
                  const {expiresAt} =  UserOTPVerificationRecords[0]
                   if(expiresAt < Date.now()){
                        await UserOTPVerification.deleteMany({userid})
                        throw new Error("code has een expired. please request again")
                   }else{
                       
                       const validotp = await bcrypt.compare(otp,hashedotp)
                       
                       if(!validotp){
                            throw new Error("incorrect code. please check your email")
                       }else{
                            await Agency1.findOneAndUpdate({_id: userid},{verifed: true})
                            await UserOTPVerification.deleteMany({userid})
                            res.status(200).json({
                                status : "verified",
                                message: "user email is verified successfuly"
                            })
                             const token = creatToken(userid)
                            res.cookie('jwt',token,{
                                httpOnly: true,
                                maxAge:30*24*60*60*1000
                            })
                       }
                   }
              }
         }
    } catch (error) {
        res.status(200).json({
            status : "failed",
            message: error.message
        })
    }
}

sendmail = async(req,res)=>{
    try {
        const {email} = req.body
        if( !email){
            throw Error("Empty user details are not allowed")
        }else{
            const userinfo = await Agency1.findOne({email})
            console.log(userinfo);
            if(userinfo){
                await UserOTPVerification.deleteMany({userid: userinfo._id})
                sendOTPVerificationEmail({_id:userinfo._id,email,name:userinfo.name},res)
            }else{
              throw Error("email incorrect")
            }
            
        }
    } catch (error) {
        res.status(200).json({
            status : "failed",
            message: error.message
        })
    }
}

setnewpass = async(req,res)=>{
   try {
    const {userid} = req.body
    let {newpassword,confirmenewpassword} = req.body
    if(newpassword == confirmenewpassword){
        const salt = await bcrypt.genSalt();
        newpassword = await bcrypt.hash(newpassword,salt)
        const newUser= await Agency1.findOneAndUpdate({_id:userid},{password:newpassword},{
            new:true,
            runValidators:true
        })
        res.status(201).json({
            success:true,
            data:{
                "id":newUser._id,
                "password": newUser.password,
                "name":newUser.name,
                "email":newUser.email,
                "location":newUser.location,
                "Phone number":newUser.phonenumber,
                "picture": newUser.picture,
                "rate":newUser.rate,
                "certification":newUser.certification,
                "classification":newUser.classification,
                "sex":newUser.sex,
                "description":newUser.description,
                "birthdayDate":newUser.birthdayDate,
                "role": newUser.role,
                "nbfollowers" : newUser.nbfollowers
            },
            msg:"password recuperation success"
        })
    }else{
        throw Error('confirme password incorrect')
    }
   } catch (error) {
    const err = handleErrors(error)
    res.status(400).json({
        success:false,
        message: err
     })
   }
}
module.exports = {
 sendmail,
 validateemail,
 setnewpass
}