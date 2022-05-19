const mongoose = require('mongoose')

const DislikesShcema = new mongoose.Schema({
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
        type: String
    }
})


const Dislikes = mongoose.model('Dislikes',DislikesShcema)
module.exports = Dislikes

