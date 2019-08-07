function write(file, data) {
	var fs = require('fs');
	var filePath = file;
	var fd = fs.openSync(filePath, 'a+');
	var buff = new Buffer(data, 'base64');
	fs.write(fd, buff, 0, buff.length, 0, function(err,written){
    
	});    
}
var fs1 = require("fs");
var data = '';

// Create a readable stream
var readerStream = fs1.createReadStream('chunk.mp4');

// Set the encoding to be utf8. 
//readerStream.setEncoding('base64');

// Handle stream events --> data, end, and error
readerStream.on('data', function(chunk) {
   data += chunk;
   write('video-cp.mp4', chunk);
});

readerStream.on('end',function() {
   //console.log(data);
});

readerStream.on('error', function(err) {
   console.log(err.stack);
});

