const userModel = require('../model/userModel');

module.exports.getUser = async function getUser(req, res) {
    let id = req.id;
    let user = await userModel.findById(id);
    if (user) {
        return res.json({user});
    } else {
        return res.json({
            message: "user not found"
        })  
    }
}

module.exports.updateUser = async function updateUser(req, res) {
    try {
        let id = req.params.id;
        let user = await userModel.findById(id);
        let dataToBeUpdated = req.body;
        if (user) {
            for (let key in dataToBeUpdated) {
                user[key] = dataToBeUpdated[key];
            }
             // to be tested
            user.confirmPassword = user.password;
            const updatedData = await user.save();
            res.json({
                message: "user data updated!",
                data: updatedData
            })
        } else {
            res.json({
                message: "user not found!"
            })
        }

    } catch(e) {
        res.json({
            message: err.message,
        })
    }
}

module.exports.deleteUser = async function deleteUser(req, res) {
    try {
        let id = req.params.id;
        let user = await userModel.findByIdAndDelete(id);
        if (!user) {
            res.json({
                message: "user not found!"
            })
        } else {
            res.json({
                message: "user deleted",
                data: user,
            })
        }

    } catch (e) {
        res.json({
            message: e.message,
        })
    }
} 

module.exports.getAllUser = async function getAllUser(req, res) {
    try {
        let users = await userModel.find();
        if (users) {
            res.json({
                message: 'users retrieved!',
                data: users
            })
        }
    } catch(e) {
        res.json({
            message: e.message,
        })
    }
} 

module.exports.updateProfileImage = function updateProfileImage(req, res) {
    res.json({
        message: 'file uploaded successfully',
    })
}

// use jWT instead
// function setCookie(req, res) {
//     res.setHeader('Set-Cookie', 'isLoggedIn=true');
//     res.cookie('isLoggedIn', true, {
//         maxAge: 1000 * 60 * 60 * 24,
//         secure: true,
//         httpOnly: true,
//     });
//     res.cookie('isPrimeMember', true);
//     res.send('cookies has been set');
// }

// function getCookies(req, res) {
//     let cookies = req.cookies.isLoggedIn;
//     console.log(cookies);
//     res.send('cookies recieved');
// }