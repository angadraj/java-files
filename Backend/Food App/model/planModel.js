const mongoose = require('mongoose');
const db_link = 'mongodb+srv://AngadRajSingh:root@cluster0.ogdrv.mongodb.net/myFirstDatabase?retryWrites=true&w=majority';

mongoose.connect(db_link)
.then(function() {
    console.log("plan db connected");
})
.catch(function(err) {
    console.log(err);
});

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