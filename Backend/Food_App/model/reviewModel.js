const mongoose = require('mongoose');
const {connectDB} = require('../secrets');

connectDB();

// from front end, I only want user to send review text and rating
// user id and plan id will be controlled on backend
const reviewSchema = mongoose.Schema({
    review: {
        type: String,
        required: [true, 'review is required!']
    },
    rating: {
        type: Number,
        min: 1,
        max: 10,
        required: [true, 'rating is required!']
    }, 
    createdAt: {
        type: Date,
        default: Date.now(),
    },
    // user writes a review(s) about a plan
    user: {
        type: mongoose.Schema.ObjectId,
        ref: 'userModel',
        // required: [true, 'review must belong to a user!']
    },
    plan: {
        type: mongoose.Schema.ObjectId,
        ref: 'planModel',
        // required: [true, 'review must belong to a plan!']
    }
});

reviewSchema.pre(/^find/, function(next) {
    this.populate({
        path: "user",
        select: "name profileImage"
    }).populate({
        path: "plan"
    })
    next();
});

const reviewModel = mongoose.model('reviewModel', reviewSchema);
module.exports = reviewModel;