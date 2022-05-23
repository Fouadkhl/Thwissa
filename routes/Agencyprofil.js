const {Router}= require('express');
const router = Router();
const profilController = require('../controllers/Agencyprofil');            

const multer = require('multer')

const storage = multer.diskStorage({
    destination: function(req,file,cb){
        cb(null,'./uploads')
    },
    filename:function(req,file,cb){
        cb(null,  file.originalname)
    }
})

const upload = multer({storage: storage})




router.patch('/agencyprofiledit',upload.single('picture'),profilController.updateagencyprofil)




module.exports = router