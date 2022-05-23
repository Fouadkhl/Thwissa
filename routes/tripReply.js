const {Router}= require('express');
const router = Router();
const tripReplyController = require('../controllers/tripReply');     
      
const multer = require('multer')

const Storage = multer.diskStorage({
    destination: function(req,file,cb){
        cb(null,'./uploads')
    },
    filename : function(req,file,cb){
        cb(null, file.originalname )
    }
})

const upload = multer({storage: Storage})





router.route('/news/trips/:tripid/reply/:replyId')
            .delete(tripReplyController.deleteReply)
            .patch(upload.single('picture'),tripReplyController.updateReply)
            .get(tripReplyController.getsinglereply)
router.route('/news/trips/:tripid/reply')
            .get(tripReplyController.getReply)
            .post(upload.single('picture'),tripReplyController.createReply)






module.exports = router;