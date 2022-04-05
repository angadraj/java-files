const fileModel = require('./models/fileModel');
const fs = require('fs');
const db = require('./dataBase/db');

async function cleanUp() {
    try {
        let pastDate = new Date(Date.now() - (24 * 60 * 60 * 1000));
        let files = await fileModel.find({createdAt: {$lt: pastDate}});
        if (files.length) {
            for (let file of files) {
                fs.unlinkSync(file.path);
                await file.remove();
                console.log("successfully deleted");
            }
        }
    } catch (e) {
        console.log(e);
    }
}

cleanUp()
.then(function() {
    console.log("files deleted!");
    process.exit();
})