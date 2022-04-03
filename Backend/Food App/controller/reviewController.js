// const {getAllReviews, top3Reviews, getPlanReviews, createReview, updateReview, deleteReview} = require('../controller/reviewController');
const reviewModel = require('../model/reviewModel');
const planModel = require('../model/planModel');
const userModel = require('../model/userModel');

module.exports.getAllReviews = async function getAllReviews(req, res) {
    try {   
        const reviews = await reviewModel.find();
        if (reviews) {
            res.json({
                message: "reviews found!",
                data: reviews
            });
        } else {
            res.json({
                message: "reviews not found!"
            })
        }
    } catch (e) {
        res.json({
            message: e.message
        })
    }
}

module.exports.top3Reviews = async function top3Reviews(req, res) {
    try {
        const reviews = await reviewModel.find().sort({
            rating: -1
        }).limit(3);
        if (reviews) {
            res.json({
                message: "top 3 reviews found",
                data: reviews
            })
        } else {
            res.json({
                message: "reviews not found!"
            })
        }
    } catch (e) {
        res.json({
            message: e.message
        })
    }
}

module.exports.getPlanReviews = async function getPlanReviews(req, res) {
    try {
        let pid = req.params.id;
        let reviews = await reviewModel.find();
        reviews = reviews.filter(function(r) {
            return r.plan["_id"] == pid;
        });
        res.json({
            message: "plan reviews found!",
            data: reviews
        });
    } catch (e) {
        res.status(500).json({
            message: e.message
        })
    }
}

module.exports.createReview = async function createReview(req, res) {
    try {
        let userId = req.id;
        let pid = req.params.plan;
        let plan = await planModel.findById(pid)
        ;
        let review = await reviewModel.create(req.body);
        // now we don't need to send user id & plan id from front end to save with reviews
        review.user = userId;
        review.plan = pid;
        //
        plan.ratingsAverage = (plan.ratingsAverage + req.body.rating) / 2;
        // 2 => totalusers + 1
        await plan.save();
        await review.save();

        res.json({
            message: "review created",
            data: review
        })

    } catch (e) {
        res.json({
            message: e.message
        })
    }
}

module.exports.updateReview = async function updateReview(req, res) {
    try {
        let rid = req.body.id;
        let data = req.body;
        let review = await reviewModel.findById(rid);
        Object.keys(data).forEach(function(key) {
            review[key] = data[key];
        })
        await review.save();
        return res.json({
            message: "review updated!",
            data: review
        })

    } catch (e) {
        res.json({
            message: e.message
        })
    }
}

module.exports.deleteReview = async function deleteReview(req, res) {
    try {
        let rid = req.body.id;
        let review = await reviewModel.findByIdAndDelete(rid);
        return res.json({
            message: "review deleted!",
            data: review
        });
    } catch (e) {
        res.json({
            message: e.message
        });
    }
}