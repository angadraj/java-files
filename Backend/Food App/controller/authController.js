const express = require('express');
const userModel = require('../model/userModel');
const jwt = require('jsonwebtoken');
const {sendMail} = require('../utility/nodeMailer');
const {JWT_KEY} = require('../secrets');

// sign up a user
module.exports.signup = async function signup(req, res) {
    try {
        let dataObj = req.body;
        let user = await userModel.create(dataObj);
        sendMail("signup", user);
        if (user) {
            return res.json({
                message: "user signed up",
                data: user
            });
        } else {
            res.json({
                message: "error in signing up!"
            });
        }

    } catch(e) {
        res.json({
            message: e.message,
        })
    }
}

// login user
module.exports.login = async function login(req, res) {
    try {
        let data = req.body;
        if (data.email) {
            let user = await userModel.findOne({email: data.email});
            if (user) {
                if (user.password == data.password) {
                    let uid = user["_id"];
                    let token = jwt.sign({payload: uid}, JWT_KEY);
                    res.cookie("login", token, {httpOnly: true});
                    return res.json({
                        message: "user logged in",
                        data: user
                    });
                } else {
                    return res.json({
                        message: "credentials are not correct"
                    });
                } 
            } else {
                res.json({
                    message: "user not found"
                });
            }
        } else {
            res.json({
                message: "empty field found",
            });
        }

    } catch(e) {
        return res.status(500).json({
            message: e.message,
        })
    }
}

module.exports.isAuthorised = function isAuthorised(roles) {
    // this custom func returns a middle ware 
    // why returning a middleware, to use custom params. ie roles
    return function(req, res, next) {
        if (roles.includes(req.role)) {
            next();
        } else {
            res.json({
                message: "Operation not allowed"
            });
        }
    }
}

module.exports.protectRoute = async function protectRoute(req, res, next) {
    try {
        let token;
        if (req.cookies.login) {
            token = req.cookies.login;
            let payload = jwt.verify(token, JWT_KEY);
            if (payload) {
                const user = await userModel.findById(payload.payload);
                req.role = user.role;
                req.id = user.id;
                next();
            } else {
                return res.json({
                    message: "please login again"
                })
            }
        } else {
            const client = req.get('User-Agent');
            if (client.includes('Mozilla') == true) {
                return res.redirect('/login');
            }
            // postman
            res.json({
                message: "please login"
            });
        }
    } catch(e) {
        return res.json({
            message: e.message
        });
    }
}

// forget password
module.exports.forgetpassword = async function forgetpassword(req, res) {
    let {email} = req.body;
    try {
        const user = await userModel.findOne({email: email});
        if (user) {
            const resetToken = user.createResetToken();
            await user.save();
            let resetPasswordLink = `${req.protocol}://${req.get("host")}/user/resetPassword/${resetToken}`;
            let obj = {
                resetPasswordLink: resetPasswordLink,
                email: email,
            }
            sendMail("resetpassword", obj);
            return res.json({
                message: "reset password link sent",
                data: resetPasswordLink
            });
        } else {
            return res.json({
                message: "Please signup"
            });
        }
    } catch(e) {
        return res.status(500).json({
            message: e.message
        });
    }
}

// reset password
module.exports.resetpassword = async function resetpassword(req, res) {
    try {
        const token = req.params.token;
        let {password, confirmPassword} = req.body;
        const user = await userModel.findOne({resetToken: token});
        if (user) {
            user.resetPasswordHandler(password, confirmPassword);
            await user.save();
            res.json({
                message: "password updated. Please login again!"
            });
        } else {
            res.json({
                message: "user not found"
            })
        } 
    } catch(e) {
        res.json({
            message: e.message,
        });
    }
}

module.exports.logout = async function logout(req, res) {
    try {
        let token = req.cookies.login;
        let payload = jwt.verify(token, JWT_KEY);
        let user; 
        if (payload) {
            user = await userModel.findById(payload.payload);
        }
        res.cookie('login', '', {maxAge: 1});
        if (user) {
            res.json({
                message: "user logged out!",
                data: user
            })
        } else {
            res.json({
                message: "user logged out!",
                data: "user not found!"
            });
        }
    } catch (e) {
        res.json({
            message: e.message,
        })
    }
}
