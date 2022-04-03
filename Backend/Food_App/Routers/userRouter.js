const express = require('express');
const userRouter = express.Router();
const multer = require('multer');
const {getUser, getAllUser, updateUser, deleteUser, updateProfileImage} = require('../controller/userController');
const {signup, login, isAuthorised, protectRoute, forgetpassword, resetpassword, logout} = require('../controller/authController');

userRouter.route('/:id')
.patch(updateUser)
.delete(deleteUser);

userRouter
.route('/signup')
.post(function(req, res) {
    signup(req, res);
});

userRouter
.route('/login')
.post(function(req, res) {
    login(req, res);
});

userRouter
.route('/forgetPassword')
.post(function(req, res) {
    forgetpassword(req, res);
});

userRouter
.route('/resetPassword/:token')
.post(function(req, res) {
    resetpassword(req, res);
});

userRouter
.route('/logout')
.get(logout);

// multer for file uploads
const multerStorage = multer.diskStorage({
    destination: function(req, file, cb) {
        cb(null, 'public/images');
    },
    filename: function(req, file, cb) {
        cb(null, `user-${Date.now()}.jpeg`);
    }
});

const filter = function(req, file, cb) {
    if (file.mimetype.startsWith("image")) {
        cb(null, true);
    } else {
        cb(new Error("Not an image! Please upload an image"), false);
    }
}

const upload = multer({
    storage: multerStorage,
    fileFilter: filter
});

userRouter.post('/profileImage', upload.single('photo'), updateProfileImage);
userRouter.get('/profileImage', function(req, res) {
    res.sendFile('/Users/akashverma/Desktop/java-files/Backend/multer.html')
});

// profile page, login required for accessing profile
userRouter.use(protectRoute);
userRouter
.route('/userProfile')
.get(getUser);

// admin specific function
userRouter.use(isAuthorised(['admin']));
userRouter
.route('/')
.get(getAllUser);

module.exports = userRouter;