const mongoose = require('mongoose')
const {isEmail} = require('validator')
const bcrypt = require('bcrypt')


const agency1Shcema = new mongoose.Schema({
    name:{
        type:String,
        required:[true,'please enter your name..']
    },
    email:{
        type:String,
        required:[true,'please enter a email..'],
        validate:[isEmail, 'please enter a valide mail ..'],
        unique:true,
        lowercase:true,
        
    },
    password:{
        type:String,
        required:[true,'please enter a password..'],
        minlength:6
    },
    location:{
        type:String,
        required:[true,'please enter your location..']
    },
    phonenumber:{
        type:String
    },
    rate:{
        type:Number,
        default:0
    },
    picture:{
        type:String
    },
    certification:{
        type:String
        
    },
    classification:{
        type:String
        
    },
    isvalidate : {
        default:false
    },
    role:{
        type:String
    },
    sex :{
        type : String
    },
    birthdayDate:{
        type : String
    },
    description :{
        type : String
    },
    nbfollowers :{
        type: Number,
        default : 0
    },
    verifed:{
        type: Boolean,
        default: false
    },
    confirmationCode:{
        type: String
    }
})



//fire a function before the doc save in the db
agency1Shcema.pre('save', async function(next){

 const salt = await bcrypt.genSalt();
 this.password = await bcrypt.hash(this.password,salt)
 next();
})

//static method to login user
agency1Shcema.statics.login= async function(email,password){
    const user = await this.findOne({email})
    
    if(user){
        const auth = await bcrypt.compare(password, user.password)
        if(auth){
         const token = creatToken(agency._id)
         res.cookie('jwt',token,{
             httpOnly: true,
             maxAge:30*24*60*60*1000
         })
         return user;
        }
      throw Error('password incorrect')
       
    }
    throw Error('email incorrect..')
}



const agency1 = mongoose.model('agency1',agency1Shcema)
module.exports = agency1;

