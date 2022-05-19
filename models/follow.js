const mongoose = require('mongoose')

const FollowShcema = new mongoose.Schema({
    agencyid:{
        type: String
    },
    userid:{
        type: String
    }
})

const Follow = mongoose.model('Follow',FollowShcema)
module.exports = Follow

