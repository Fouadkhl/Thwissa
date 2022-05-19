const mongoose = require('mongoose')

const UserOTPVerificationShcema = new mongoose.Schema({
    otp: String,
    userid: String,
    createdAt: Date,
    expiresAt: Date,
})


const UserOTPVerification = mongoose.model('UserOTPVerification',UserOTPVerificationShcema)
module.exports = UserOTPVerification

