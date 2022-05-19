const {Router}= require('express');
const router = Router();
const filterController = require('../controllers/Filter');     




router.get('/filter',filterController.filtrage)







module.exports = router;