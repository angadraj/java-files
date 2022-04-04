const fileModel = require('./models/fileModel');
async function cleanUp() {
    try {
        let pastDate = new Date(Date.now() - (24 * 60 * 60 * 1000));
        let files = await fileModel.find({createdAt: {$lt: pastDate}});
        if (files.length) {
            for (let file of files) {
                
            }
        }

    } catch (e) {
        console.log(e);
    }
}