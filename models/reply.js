const mongoose = require('mongoose')



const replyShcema = new mongoose.Schema({
    text:{
        type:String,
        required:[true, 'please provid a text ..']
    },
    picture:{
        type:String
    },
    username:{
        type:String
    },
    userlocation:{
        type:String
    },
    userpicture:{
        type:String
    },
    likes:{
        type:Number,
        default:0
    },
    dislikes:{
        type:Number,
        default:0
    },
    questionId:{
        type:String
    },
    replyDate:{
        type:Date,
        default: Date.now
    },
    tripid:{
        type: String
    }

})







const reply = mongoose.model('reply',replyShcema)
module.exports = reply;