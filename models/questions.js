const timespan = require('jsonwebtoken/lib/timespan');
const mongoose = require('mongoose')



const quetionShcema = new mongoose.Schema({
    text:{
        type:String,
        required:[true, 'please provid a text in your question']
    },
    picture:{
        type:String
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
    likes:{
        type:Number,
        default:0
    },
    dislikes:{
        type:Number,
        default:0
    },
    replynumber:{
        type:Number,
        default:0
    },
    questionDate:{
        type: Date,
        default: Date.now
    },
    tags:[String]
})







const questions = mongoose.model('questions',quetionShcema)
module.exports = questions;