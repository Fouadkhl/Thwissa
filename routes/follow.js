const {Router}= require('express');
const router = Router();
const followController = require('../controllers/follow');     



router.post('/agency/:agencyid/follow',followController.Following)







module.exports = router;