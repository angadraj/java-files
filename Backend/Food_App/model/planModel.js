const mongoose = require('mongoose');
const {connectDB} = require('../secrets');

connectDB();

const planSchema = mongoose.Schema({
    name: {
        type: String,
        required: true,
        unique: true,
        maxLength: [20, "plan name should not exceed more than 20 characters"]
    },
    duration: {
        type: Number,
        required: true,
    },
    price: {
        type: Number,
        required: [true, "price not entered"]
    },
    ratingsAverage: {
        type: Number
    },
    discount: {
        type: Number,
        validate: [
            function() {
                return this.discount < 100;
            },
            "discount is not valid"
        ]
    },
})

const planModel = mongoose.model('planModel', planSchema);
module.exports = planModel;