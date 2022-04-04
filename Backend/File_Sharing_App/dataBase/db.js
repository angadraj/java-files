const mongoose = require('mongoose');
require('dotenv').config();

mongoose.connect(process.env.DB_LINK)
.then(function() {
    console.log("file sharing db connected");
})
.catch(function(err) {
    console.log(err);
})