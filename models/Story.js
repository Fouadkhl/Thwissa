const mongoose = require('mongoose') ;

const StorySchema = new mongoose.Schema ({
    addAT :{
        type : Date,
        default: Date.now
    },
    picture : {
        type: String  ,
        required :[true,'Enter a picture please ']
    } ,
    idLocation : {
        type:String ,
        required : true
    } , 
    idUser : {
        type: String  
    } , 
    like:{
        type : Number ,
        default:0
    } ,
    dislike : {
        type : Number ,
        default:0
    } , 
    report : {
        type : Number ,
         default:0
    } ,
    username:{
        type:String
    },
    userpicture:{
        type:String
    },
    userlocation:{
        type: String
    }
    
})
module.exports = mongoose.model('Story',StorySchema);
