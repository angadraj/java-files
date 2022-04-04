const express = require('express');
const fileRouter = express.Router();
const {fileUpload, fileDownload, getFile, send} = require('../controllers/fileController');
const multer = require('multer');
const path = require('path');

const storage = multer.diskStorage({
    destination: function(req, file, cb) {
        cb(null, './uploads')
    },
    filename: function(req, file, cb) {
        const uniqueName = `${Date.now()}-${Math.round(Math.random() * 1E9)}${path.extname(file.originalname)}`;
        cb(null, uniqueName);
    }   
});

const upload = multer({
    storage,
    limit: {
        fileSize: 1000000 * 100
    }
});

fileRouter.post('/upload', upload.single('myfile'), function(req, res) {
    fileUpload(req, res);
});

fileRouter.route('/:uuid')
.get(fileDownload)

fileRouter.route('/download/:uuid')
.get(getFile);

fileRouter.route('/send')
.post(function(req, res) {
    send(req, res);
});

module.exports = fileRouter;
