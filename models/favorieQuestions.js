const mongoose = require('mongoose')

const QuestionShcema = new mongoose.Schema({
    userid:{
        type: String
    },
    questionid:{
        type: String
    },
    type :{
        type: String
    },
    picture:{
        type:String
    },
    text:{
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
        type:Number
    },
    dislikes:{
        type:Number
    },
    replynumber:{
        type:Number,
        default:0
    },
    questionDate:{
        type: Date
    },
    tags:[String]
})


const questionfav = mongoose.model('questionfav',QuestionShcema)
module.exports = questionfav

