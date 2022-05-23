const express = require('express')
const mongoose = require('mongoose')
const autghRoutes = require('./routes/authentication')
const discussRoutes = require('./routes/questions')
const replyRoutes = require('./routes/reply')
const reviewsRoutes = require('./routes/reviews')
const followRoutes = require('./routes/follow')
const tripsRoutes = require('./routes/trips')
const userprofilRoutes = require('./routes/Userprofil')
const agencyprofilRoutes = require('./routes/Agencyprofil')
const FilterRoutes = require('./routes/Filter')
const reactionRoutes = require('./routes/reactions')
const tripReplyRoutes = require('./routes/tripReply')
const favorieRoutes = require('./routes/favorie')
const story = require('./routes/Story');
const forgetpasswordRoutes = require('./routes/forgetpassword')
require('dotenv').config();

const cookieParser = require('cookie-parser')
const {requireAuth} = require('./middleware/authMiddleware')
const app = express()

//middelware 
app.use(express.json())
app.use(express.static('public'))
app.use(cookieParser())

app.use(
  tripsRoutes,
  tripReplyRoutes,
  favorieRoutes,
  userprofilRoutes,
  agencyprofilRoutes,
  FilterRoutes,
  reactionRoutes,
  followRoutes,
  reviewsRoutes,
  replyRoutes,
  discussRoutes,
  autghRoutes,
  story,
  forgetpasswordRoutes
  )







// database connection

mongoose.connect(process.env.MONGODB_URI, { useNewUrlParser: true, useUnifiedTopology: true, 
})
  .then((result) => app.listen(5000))
  .catch((err) => console.log(err));





//mongodb+srv://kousailaben:mp08082005@cluster0.vfqrk.mongodb.net/node-auth