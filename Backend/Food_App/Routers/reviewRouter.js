const express = require('express');
const reviewRouter = express.Router();
const { protectRoute } = require('../controller/authController');
const {getAllReviews, top3Reviews, getPlanReviews, createReview, updateReview, deleteReview} = require('../controller/reviewController');

reviewRouter.route('/all')
.get(getAllReviews);

reviewRouter.route('/top3')
.get(top3Reviews);

// in this route you will get plan id, this can be integerated with route: /plans/:id
reviewRouter.use(protectRoute);
reviewRouter.route('/:id')
.get(getPlanReviews);

reviewRouter.route('/crud/:plan')
.patch(createReview)
.post(function(req, res) {
    updateReview(req, res);
})
.delete(deleteReview);

module.exports = reviewRouter;