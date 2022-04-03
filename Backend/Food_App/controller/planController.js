const planModel = require('../model/planModel');

module.exports.getAllPlans = async function getAllPlans(req, res) {
    try {
        let plans = await planModel.find();
        if (plans) {
            return res.json({
                message: "all plans retrieved!",
                data: plans
            })
        } else {
            return res.json({
                message: "plans not found!"
            })
        }
    } catch (err) {
        res.status(500).json({
            message: err.message
        })
    }
} 

module.exports.top3Plans = async function top3Plans(req, res) {
    try {
        let plans = await planModel.find().sort({
            ratingsAverage: -1 
        }).limit(3);
        
        return res.json({
            message: "top3 plans retrieved!",
            data: plans
        })

    } catch (e) {
        res.status(500).json({
            message: e.message
        })
    }
}

// logged in *
module.exports.getPlan = async function getPlan(req, res) {
    try {
        let planId = req.params.id;
        let plan = await planModel.findById(planId);
        if (plan) {
            return res.json({
                message: "plan retrived",
                data: plan
            })
        } else {
            return res.json({
                message: "plan not found"
            })
        }
    } catch (e) {
        res.status(500).json({
            message: e.message
        })
    }
}

// authority *
module.exports.createPlan = async function createPlan(req, res) {
    try {
        let planData = req.body;
        // here we are not verifying the data comming in req
        let createdPlan = await planModel.create(planData);
        return res.json({
            message: "plan created successfully",
            data: createdPlan
        })
    } catch (e) {
        res.status(500).json({
            message: e.message
        })
    }
}

module.exports.deletePlan = async function deletePlan(req, res) {
    try {
        let pid = req.params.id;
        let deletedPlan = await planModel.findByIdAndDelete(pid);
        return res.json({
            message: "plan deleted",
            data: deletedPlan
        })
    } catch (e) {
        res.status(500).json({
            message: e.message
        })
    }
}

module.exports.updatePlan = async function updatePlan(req, res) {
    try {   
        let data = req.body;
        let plan = await planModel.findById(req.params.id);
        if (plan) {
            Object.keys(data).forEach(function(key) {
                plan[key] = data[key];
            }) 
            await plan.save();
            return res.json({
                message: "plan updated!",
                data: plan
            });

        } else {
            res.json({
                message: "plan not found!"
            })
        }
    } catch (e) {
        res.json({
            message: e.message,
        })
    }
}