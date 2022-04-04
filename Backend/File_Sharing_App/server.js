const express = require('express');
const db = require('./dataBase/db');
const path = require('path');
const app = express();
const PORT = process.env.PORT || 3000;
const cors = require('cors')

// cors
const corsOptions = {
    origin: process.env.ALLOWED_CLIENTS.split(",")
}
app.use(cors(corsOptions));

// use static files
app.use(express.static('public'));

// template engines
app.set('views', path.join(__dirname, '/views'));
app.set('view engine', 'ejs');

// middlewares
app.use(express.json());

// mini app
const fileRouter = require('./routers/fileRouter');

app.use('/files', fileRouter);

app.listen(PORT, function() {
    console.log("file sharing server listening at port " + PORT);
})