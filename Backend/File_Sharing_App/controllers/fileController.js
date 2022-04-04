const express = require('express');
const fileModel = require('../models/fileModel');
const {v4: uuid4} = require('uuid');
const {sendMail} = require('../utility/nodeMailer');
const {getEmailTemplate} = require('../utility/emailTemplate');

module.exports.fileUpload = async function fileUpload(req, res) {
    try {
        if (!req.file) {
            return res.json({
                message: "Please fill all fields!"
            });
        }
        const createdFile = new fileModel({
            fileName: req.file.filename,
            path: req.file.path,
            size: req.file.size,
            uuid: uuid4(),
        })

        let responseFile = await createdFile.save();
        return res.json({
            message: "file uploaded!",
            uploadLink: `${req.protocol}://${req.get("host")}/files/${createdFile.uuid}`,
            data: responseFile 
        })
    } catch (err) {
        res.json({
            message: err.message
        });
    }
} 

module.exports.fileDownload = async function fileDownload(req, res) {
    try {
        let uuid = req.params.uuid;
        let file = await fileModel.findOne({uuid: uuid});
        if (!file) {
            return res.status(500).render('download', {
                error: "Link has been expired"
            })
        }
        
        return res.render('download', {
            uuid: file.uuid,
            fileName: file.fileName,
            fileSize: file.size,
            downloadLink: `${req.protocol}://${req.get("host")}/files/download/${file.uuid}`,
        });

    } catch (e) {
        res.status(500).render('download', {
            error: 'Something went wrong'
        });
    }
}

module.exports.getFile = async function getFile(req, res) {
    try {
        let uuid = req.params.uuid;
        let file = await fileModel.findOne({uuid: uuid});
        if (!file) {
            return res.render('download', {
                error: "Link expired!"
            })
        }
        const filePath = `${__dirname}/../${file.path}`;
        res.download(filePath);
    } catch (e) {
        return res.status(500).render('download', {
            error: "Server Error!"
        })
    }
}

module.exports.send = async function send(req, res) {
    try {
        const {uuid, reciever, sender} = req.body;
        if (!uuid || !reciever || !sender) {
            return res.status(422).send({
                error: 'all fields are required!'
            });
        }
        const file = await fileModel.findOne({uuid: uuid});
        // intercept the req object and add sender and reciever
        // if (file.sender) {
        //     return res.status(422).send({
        //         error: "Email already sent!"
        //     });
        // }
        file.sender = sender;
        file.reciever = reciever;
        let response = await file.save();
        // send mail
        await sendMail({
            from: response.sender,
            to: response.reciever,
            subject: "File Sharing",
            text: `${response.sender} shared a file with you!`,
            html: getEmailTemplate({
                    emailFrom: response.sender,
                    downloadLink: `${req.protocol}://${req.get("host")}/files/${response.uuid}`,
                    size: parseInt(response.size / 1000) + 'KB',
                    expires: '24 Hours'
                }),
        });

        return res.json({
            message: "mail sent!"
        })

    } catch (e) {
        return res.status(500).json({
            message: e.message
        })
    }
}

