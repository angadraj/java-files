const mongoose = require('mongoose');

const fileSchema = mongoose.Schema({
    fileName: {
        type: String,
        required: [true, "enter a valid fileName!"]
    },
    path: {
        type: String,
        required: [true, "enter path!"]
    },
    size: {
        type: Number,
        required: true
    },
    uuid: {
        type: String,
        required: true
    },
    sender: {
        type: String
    },
    reciever: {
        type: String
    }
}, { timestamps: true });

const fileModel = mongoose.model('fileModel', fileSchema);
module.exports = fileModel;