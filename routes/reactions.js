const {Router}= require('express');
const router = Router();
const reactionsController = require('../controllers/reactions'); 



router.route('/discuss/questions/:questionid/like/').post(reactionsController.createlikequestion)
router.route('/discuss/questions/:questionid/dislike/').post(reactionsController.createdislikequestion)
router.route('/discuss/questions/:questionid/reply/:replyid/like/').post(reactionsController.createlikereply)
router.route('/discuss/questions/:questionid/reply/:replyid/dislike/').post(reactionsController.createdislikereply)
router.route('/news/trips/:tripid/like').post(reactionsController.createliketrip)
router.route('/news/trips/:tripid/dislike').post(reactionsController.createdisliketrip)
router.route('/news/trips/:tripid/reply/:replyid/like').post(reactionsController.createliketripreply)
router.route('/news/trips/:tripid/reply/:replyid/dislike').post(reactionsController.createdisliketripreply)

router.route('/story/:idLocation/:idPicture/like').post(reactionsController.createlikepicture)
router.route('/story/:idLocation/:idPicture/dislike').post(reactionsController.createdislikepicture)


module.exports = router;