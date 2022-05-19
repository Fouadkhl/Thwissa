const {Router}= require('express');
const router = Router();
const favorieTripsController = require('../controllers/favorieTrips'); 
const favorieQuestionController = require('../controllers/favorieQuestion')



router.route('/discuss/questions/:questionid/favorie').post(favorieQuestionController.favoriequestion)

router.route('/news/trips/:tripid/favorie').post(favorieTripsController.favorietrip)

router.route('/Favorie').get(favorieTripsController.getFavorie)



module.exports = router;