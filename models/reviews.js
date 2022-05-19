const mongoose = require('mongoose')



const reviewShcema = new mongoose.Schema({
    text:{
        type:String,
        required:[true, 'please provid a text in your review..']
    },
    username:{
        type:String
    },
    userpicture:{
        type:String
    },
    userlocation:{
        type: String
    },
    reviewDate:{
        type: Date,
        default: Date.now
    },
    agencyId:{
        type:String
    },
    rate:{
        type:Number,
        default:0
    }
})







const reviews = mongoose.model('reviews',reviewShcema)
module.exports = reviews;