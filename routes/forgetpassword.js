const {Router}= require('express');
const router = Router();
const passController = require('../controllers/forgetpassword');            






router.post('/forgetpassword/validation',passController.validateemail)

router.post('/forgetpassword/sendmail',passController.sendmail)

router.post('/forgetpassword/setnewpass',passController.setnewpass)



module.exports = router