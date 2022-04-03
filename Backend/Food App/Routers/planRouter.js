const express = require('express');
const planRouter = express.Router();
const { protectRoute, isAuthorised } = require('../controller/authController'); 

const {getPlan, getAllPlans, createPlan, updatePlan, deletePlan, top3Plans} = require('../controller/planController');

planRouter.route('/allPlans')
.get(getAllPlans);

planRouter.route('/top3')
.get(top3Plans);

// logged in important
planRouter.use(protectRoute);
planRouter.route('/plan/:id')
.get(getPlan);

// admin and authority can update, make or delete plans
planRouter.use(isAuthorised(['admin']));
planRouter.route('/crudPlan')
.post(function(req, res) {
    createPlan(req, res)
})

planRouter.route('/plan/:id')
.patch(updatePlan)
.delete(deletePlan)

module.exports = planRouter;