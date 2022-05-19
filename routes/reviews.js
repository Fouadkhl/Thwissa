const {Router}= require('express');
const router = Router();
const reviewsController = require('../controllers/reviews');     




router.get('/agency/:agencyId/reviews', reviewsController.getReviews)
router.post('/agency/:agencyId/reviews',reviewsController.createReviews)
router.route('/agency/:agencyId/reviews/:id')
            .delete(reviewsController.deleteReviews)
            .patch(reviewsController.updateReviews)






module.exports = router;