const {Router}= require('express');
const router = Router();
const tripsController = require('../controllers/trips');     
const multer = require('multer')
const { requireAuth } = require('../middleware/authMiddleware');
const { requireAgency } = require('../middleware/roleMiddleware');
const Storage = multer.diskStorage({
    destination: function(req,file,cb){
        cb(null,'./uploads')
    },
    filename : function(req,file,cb){
        cb(null, file.originalname )
    }
})

const upload = multer({storage: Storage})



router.get('/news/trips', tripsController.getTrips)
router.post('/news/trips',[upload.single('picture'),requireAuth,requireAgency],tripsController.createTrips)
router.route('/news/Trips/:tripid')
            .delete(tripsController.deleteTrips)
            .patch(upload.single('picture'),tripsController.updateTrips)
            .get(tripsController.getsingleTrip)





module.exports = router;