const {Router}= require('express');
const router = Router();
const profilController = require('../controllers/Userprofil');            

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




router.patch('/userprofiledit',upload.single('picture'),profilController.updateuserprofil)




module.exports = router