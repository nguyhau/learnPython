const WebSocket = require('ws')
const url = 'ws://localhost:8090'
//const url = 'ws://test.domain.com:8080/SampleWeb/endpoint'
const connection = new WebSocket(url)
connection.binaryType = 'arraybuffer';
//var Wfs = require("./dist/wfs.js")
function sendFile(file) {
    
    var fs = require("fs");
    //var data = '';
   /*
    * Read content in asynchronous form.
    **/
    var data = fs.readFileSync(file);
    console.log("Synchronous read: " + file);
    connection.send(data);
   /*
    * Read content in asynchronous form.
    **/
    //// Create a readable stream
    //var readerStream = fs.createReadStream(file);
    //console.log ("send file " + file);
    //
    //// Set the encoding to be utf8. 
    ////readerStream.setEncoding('UTF8');
    //
    //// Handle stream events --> data, end, and error
    //readerStream.on('data', function(chunk) {
    //   //data += chunk;
    //   connection.send(chunk);
    //});
    //
    //readerStream.on('end',function() {
    //   //console.log(data);
    //});
    //
    //readerStream.on('error', function(err) {
    //   console.log(err.stack);
    //});

    console.log("Program Ended");
	
}
connection.onopen = () => {  
    sendFile('./video/chunk_dashinit.mp4');
    sendFile('./video/chunk_dash1.m4s');
    sendFile('./video/chunk_dash2.m4s');
    sendFile('./video/chunk_dash3.m4s');
    sendFile('./video/chunk_dash4.m4s');
    sendFile('./video/chunk_dash5.m4s');
}

connection.onerror = (error) => {
  console.log(`WebSocket error: ${error}`)
}

connection.onmessage = (e) => {
  console.log(e.data)
}


