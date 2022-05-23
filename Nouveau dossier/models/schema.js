const mongoose = require('mongoose') ;

const PictureSchema = new mongoose.Schema ({
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
        type: String  ,
        //required : [true,'Enter id user please']
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
    
})
module.exports = mongoose.model('Story',PictureSchema);