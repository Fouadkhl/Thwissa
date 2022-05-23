const mongoose = require('mongoose')

const LikesShcema = new mongoose.Schema({
    questionid:{
        type: String
    },
    userid:{
        type: String
    },
    replyid:{
        type: String
    },
    tripid:{
        type: String
    },
    pictureid:{
        type:String
    }
})


const Likes = mongoose.model('Likes',LikesShcema)
module.exports = Likes

