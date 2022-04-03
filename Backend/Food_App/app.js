const express = require('express');
const app = express();
var cors = require('cors');
const cookieParser = require('cookie-parser');
// middlewares
app.use(cors());
app.use(express.json());
app.use(cookieParser());

const port = 3000;
app.listen(port, function() {
    console.log('server listening on port' + port);
});

// mini app
const userRouter = require('./Routers/userRouter');
const planRouter = require('./Routers/planRouter');
const reviewRouter = require('./Routers/reviewRouter');

// main route
app.use('/user', userRouter);
app.use('/plans', planRouter);
app.use('/reviews', reviewRouter);


module.exports = app;