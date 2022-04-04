const nodemailer = require('nodemailer');

module.exports.sendMail = async function sendMail({from, to, subject, text, html}) {
    let transporter = nodemailer.createTransport({
        host: 'smtp.gmail.com',
        port: 587,
        secure: false,
        auth: {
            user: 'angadstyle98@gmail.com',
            pass: process.env.mailPass
        }
    });

    let info = await transporter.sendMail({
        from: `inShare <${from}>`, 
        to, 
        subject,
        text, 
        html
    });
    console.log("mail sent");
}