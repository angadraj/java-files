const express = require('express');
const app = express();
const PORT = process.env.PORT || 3000;

app.listen(PORT, function() {
    console.log("file sharing server listening at port " + PORT);
})