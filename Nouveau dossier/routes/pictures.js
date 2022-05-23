const express = require('express')
const router = express.Router()

const {
  getPictures,
} = require('../controllers/picture')

router.route('/').get(getPictures)

module.exports = router
