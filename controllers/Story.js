const res = require("express/lib/response");
const Picture = require('../models/Story');
const Location = require('../models/Wilaya');
const user = require('../models/agency1');

const CreateStory = async (req,res) =>{
    try {
            const {idLocation}=req.params
            const userid = req.cookies.userid
            const picture=req.file 
            const {path} = picture
            const userinfo = await user.findOne({_id: userid})
            const newPicture = await Picture.create({picture:path,idLocation,idUser:userid , username: userinfo.name , userlocation : userinfo.location , userpicture : userinfo.picture })
            res.status(200).json({newPicture})
    } catch (error) {
        res.status(500).json({msg : error}) ;
    }
}
const getPicture = async (req,res) =>{
   try {
        const {idLocation}=req.params 
        const getPicturs= await Picture.find({idLocation})
        if(getPicturs){
            return res.status(200).json({getPicturs})
        }
        return res.status(400).json('there is no pictures with this id try again')
   } catch (error) {
       return res.status(400).json(error) 
   }}
const deletePicture = async (req,res) =>{
    try {
            const {idPicture}=req.params
            const deletepicture =await Picture.findOneAndDelete({_id:idPicture});
            if(deletepicture){
                return res.status(200).json("The picture has been deleted ")
            }
            return res.statuts(400).json('there is no picture with this id ')
    } catch (error) {
        return res.status(400).json(error)
    }
}


const updatePicture = async (req,res) =>{
    try {
            const {idPicture}=req.params
            const {like,dislike} = req.body
            if (req.body.report >= 5){
                const deletepicture =await Picture.findOneAndDelete({_id:idPicture});   
            }else {
            const updatepicture =await Picture.findOneAndUpdate({_id:idPicture},{like,dislike},{
                new:true,
                runValidators:true
            });
            if(updatepicture){
                return res.status(200).json({updatepicture})
            }
            return res.statuts(400).json('there is no picture with this id ')}
    } catch (error) {
        return res.status(400).json(error)
    }
}


module.exports = {CreateStory,getPicture,deletePicture,updatePicture} ;