const {Router}= require('express');
const router = Router();
const replyController = require('../controllers/reply');     
      
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





router.route('/discuss/questions/:questionId/reply/:replyId')
            .delete(replyController.deleteReply)
            .patch(upload.single('picture'),replyController.updateReply)
            .get(replyController.getsinglereply)
router.route('/discuss/questions/:questionId/reply/')
            .get(replyController.getReply)
            .post(upload.single('picture'),replyController.createReply)






module.exports = router;