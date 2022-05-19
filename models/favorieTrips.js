const mongoose = require('mongoose')

const FavorieShcema = new mongoose.Schema({
    userid:{
        type: String
    },
    tripid:{
        type: String
    },
    type :{
        type: String
    },
    date : {
        type : Date
    },
    duration : {
        type: Number
    },
    price : {
        type: Number,
        default:0
    },
    meetingplace : {
        type: String
    },
    destination : {
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
    tripDate:{
        type: Date
    },
    tags:[String]
})


const favorie = mongoose.model('favorie',FavorieShcema)
module.exports = favorie

