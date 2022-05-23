const timespan = require('jsonwebtoken/lib/timespan');
const mongoose = require('mongoose')



const tripsShcema = new mongoose.Schema({
   date : {
    type : Date
   },
    minduration : {
        type: Number
    },
    maxduration : {
        type: Number
    },
    minprice : {
        type: Number,
        default:0
    },
    maxprice : {
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
        type:String,
        required:[true, 'please provid a text in your trip']
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
        type: Date,
        default: Date.now
    },
    tags:[String]
})







const trips = mongoose.model('trips',tripsShcema)
module.exports = trips;