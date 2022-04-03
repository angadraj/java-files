const mongoose = require('mongoose');
const emailValidator = require('email-validator');
const bcrypt = require('bcrypt');
const crypto = require('crypto');
const db_link = 'mongodb+srv://AngadRajSingh:root@cluster0.ogdrv.mongodb.net/myFirstDatabase?retryWrites=true&w=majority';

mongoose.connect(db_link)
.then(function() {
    console.log('user db connected');
}).catch(function(err) {
    console.log(err);
})

const userSchema = mongoose.Schema({
    // this = current doc
    name: {
        type: String,
        required: true,
    },
    email: {
        type: String,
        required: true,
        unique: true,
        validate: function() {
            return emailValidator.validate(this.email);
        }
    },
    password: {
        type: String,
        required: true,
        minLength: 8
    },
    confirmPassword: {
        type: String,
        required: true,
        minLength: 8,
        validate: function() {
            return this.confirmPassword == this.password;
        }
    },
    role: {
        // role is for some authorised routes in which selected people can perform things
        type: String,
        enum: ['admin', 'user', 'restaurantowner', 'deliveryboy'],
        default: 'user',
    },
    profileImage: {
        type: String, 
        default: '../Images/userIcon.png'
    },
    // for reset of password in case he foregets
    resetToken: String,
});

// mng hoooks are used to perform actions before & after a doc is saved
userSchema.pre('save', function() {
    this.confirmPassword = undefined;
    // as password and confirm password are same so only store one entry
});

userSchema.methods.createResetToken = function() {
    const resetToken = crypto.randomBytes(32).toString('hex');
    this.resetToken = resetToken;
    this.confirmPassword = this.password;
    return resetToken;
}

userSchema.methods.resetPasswordHandler = function(password, confirmPassword) {
    this.password = password;
    this.confirmPassword = confirmPassword;
    // also no need to save reset token afer user has changed his email, pass
    this.resetToken = undefined;
}

// init model 
const userModel = mongoose.model('userModel', userSchema);
module.exports = userModel;