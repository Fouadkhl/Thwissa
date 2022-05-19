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

    //email error
    if(error.message === 'email incorrect..'){
        err.email= 'that email is no register ..'
    }
    //password error
    if(error.message === 'password incorrect'){
        err.password= 'that password is inncorect ..'
    }
    //duplicate key error 
    if (error.code === 11000){
        err.email = 'that email already exist ..'
        return err;
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


postSignUp = async(req,res)=>{
    try {
        const picture = req.file;
        const {name, email , password , confirmepassword, location , phonenumber , rate } = req.body
        let newUser;
        if(password===confirmepassword){
          if(phonenumber){
            if(typeof picture != "undefined" ){
                const {path} = picture;
                newUser = await Agency1.create({name, email , password , confirmepassword, location , phonenumber, rate, picture:path , role:'Agency' })
                res.cookie('userid',newUser._id)
               }else {
                newUser = await Agency1.create({name, email , password , confirmepassword, location , phonenumber, rate , role: 'Agency'  })
                res.cookie('userid',newUser._id)
               }
               
               /* res.status(201).json({success:true,
                    data:{
                        "id":newUser._id,
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
                    message:"Succefully created your account!"})*/
           /////////////////////////////////////////

         
        newUser.save().then((result)=>{
            sendOTPVerificationEmail(result,res)
        })
          
          }else{
            res.status(400).json({err: 'please enter you phone number..'})
          }
          
        }else{
            res.status(400).json({err: 'Password confirmation incorrect'})
        }
      
    } catch (error) {
        const err = handleErrors(error)
        res.status(400).json({
            success:false,
            message: err
         })
    }
    
}
postLogin = async(req,res)=>{
   const {email,password} = req.body
    try {
     const agency = await Agency1.login(email,password)
     const user = await Agency1.findOne({email})
     res.cookie('userid',user._id)
     res.status(201).json({
        success:true, 
        data: {
            "id":user._id,
            "name":user.name,
            "email":user.email,
            "location":user.location,
            "Phone number":user.phonenumber,
            "picture": user.picture,
            "rate":user.rate,
            "certification":user.certification,
            "classification":user.classification,
            "sex":user.sex,
            "description":user.description,
            "birthdayDate":user.birthdayDate,
            "role": user.role,
            "nbfollowers" : user.nbfollowers
        }
    })

    } catch (error) {
        const err = handleErrors(error)
        res.status(400).json({
            success:false,
            message:err
        })
    }
}

updateSignUp = async(req,res)=>{
    const [certification,classification] = req.files
    if((typeof certification != "undefined")&&(typeof classification != "undefined")) {
        const userid = req.cookies.userid
        const path1 = certification.path
        const path2 = classification.path
        try {
        newUser = await Agency1.findOneAndUpdate({_id:userid},{certification: path1 ,classification: path2},{
            new:true,
            runValidators:true
        })
            res.status(201).json({
                success:true,
                data:{
                    "id":newUser._id,
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
                }
            })
          }  catch (error) {
                const err = handleErrors(error)
                res.status(400).json({ 
                    success:false,
                    message:err
                 })
            }
    }
    else {
        res.status(400).json({
            success:false,
            message:'you must provid both of the certification and the classification'
        })
       }
    
    
}

postSignupUser = async (req, res)=>{
    const picture = req.file

    const {name,email,password,location,sex, confirmepassword} = req.body;

    if(password != confirmepassword) {
        return res.status(500).json({message:"passwords do not match!"});
    }else{
        try{
            let newUser;
            if(typeof picture != "undefined" ){
                const {path} = picture;
                newUser = await Agency1.create({name,email,password,location,sex,picture:path , role:'User'});
                res.cookie('userid',newUser._id)
               
            }else {
                newUser = await Agency1.create({name,email,password,location,sex , role: 'User'});
                
                res.cookie('userid',newUser._id)
                
            }
            
            
            
             /*res.status(200).json({
                success:true,
                data:{
                    "id":newUser._id,
                    "name":newUser.name,
                    "email":newUser.email,
                    "location":newUser.location,
                    "Phone number":newUser.phonenumber,
                    "picture": newUser.picture,
                    "sex":newUser.sex,
                    "birthdayDate":newUser.birthdayDate,
                    "role": newUser.role
                },
                message:"Succefully created your account!"
            })*/
            newUser.save().then((result)=>{
                sendOTPVerificationEmail(result,res)
            })
            
        }
        catch(err){
            const errors= handleErrors(err);
             res.status(400).json({
                success:false,
                message:errors
            });
        }
    }
    
}


getLogout =(req,res)=>{
    res.cookie('jwt','',{maxAge:1})
    res.cookie('userid','',{maxAge:1})
    res.json({success:true})
}


// send otp verification email
const sendOTPVerificationEmail = async ({_id, email, name},res)=>{
    try {
        const otp = `${Math.floor(1000 + Math.random()*9000)}`
        
       const mailoption={
        from: user,
        to: email,
        subject: "Please confirm your account",
        html: `<h1>Email Confirmation</h1>
            <h2>Hello ${name}</h2>
            <p>Thank you for subscribing. please enter <b>${otp}</b> in the app to verify your email adress and complete the sign up 
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

verifyotp = async (req,res)=>{
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
                            await Agency1.findOneAndUpdate({_id: userid},{verifed: true},{
                                new:true,
                                runValidators:true
                            })
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

resendOTPVerification = async(req,res)=>{
    try {
        const {email} = req.body
        if( !email){
            throw Error("Empty user details are not allowed")
        }else{
            const userinfo = await Agency1.findOne({email})
            await UserOTPVerification.deleteMany({userid:userinfo._id})
            sendOTPVerificationEmail({_id:userinfo._id,email,name:userinfo.name},res)
        }
    } catch (error) {
        res.status(200).json({
            status : "failed",
            message: error.message
        })
    }
}


module.exports = {
 postSignUp,
 postLogin,
 updateSignUp,
 postSignupUser,
 getLogout,
 verifyotp,
 resendOTPVerification
}