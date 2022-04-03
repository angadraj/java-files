const mongoose = require('mongoose');
const db_link = "";

mongoose.connect(db_link)
.then(function() {
    console.log("file sharing db connected");
})
.catch(function(err) {
    console.log(err);
})