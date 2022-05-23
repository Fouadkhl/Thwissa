const {Router}= require('express');
const router = Router();
const quetionsController = require('../controllers/questions');     
const multer = require('multer')
const { requireAuth } = require('../middleware/authMiddleware');
const Storage = multer.diskStorage({
    destination: function(req,file,cb){
        cb(null,'./uploads')
    },
    filename : function(req,file,cb){
        cb(null, file.originalname )
    }
})

const upload = multer({storage: Storage})



router.get('/discuss/questions',quetionsController.getQuestions)
router.post('/discuss/questions',[upload.single('picture'),requireAuth],quetionsController.createQuestions)
router.route('/discuss/questions/:id')
            .delete(quetionsController.deleteQuestions)
            .patch(upload.single('picture'),quetionsController.updateQuestions)
            .get(quetionsController.getsinglequestion)






module.exports = router;