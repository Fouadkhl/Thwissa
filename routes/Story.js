const {Router} = require('express');
const router = Router (); 
const storyController= require('../controllers/Story');


const multer = require ('multer'); 
const { route } = require('express/lib/application');

const storage = multer.diskStorage({
    destination: function(req,file,cb){
        cb(null,'./uploads')
    } ,
    filename : function(req,file , cb){
        cb(null,  file.originalname);
    }
})
const uploads = multer({storage:storage})

router.post('/story/:idLocation',uploads.single('picture'),storyController.CreateStory)
router.get('/story/:idLocation',storyController.getPicture)
router.delete('/story/:idLocation/:idPicture',storyController.deletePicture)
router.patch('/story/:idLocation/:idPicture',storyController.updatePicture)
module.exports=router ; 
