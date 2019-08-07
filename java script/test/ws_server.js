const WebSocket = require('ws')
 
const wss = new WebSocket.Server({ port: 8090 })
function write(file, data) {
	var fs = require('fs');
	var filePath = file;
    
	var fd = fs.openSync(filePath, 'a+');
	var buff = new Buffer(data, 'base64');
    //var buff = Buffer.from(data, 'base64');
	fs.write(fd, buff, 0, buff.length, 0, function(err,written){
        //console.log("Write faile");
	});
        
}
function doAppending(){
    var segment = data.shift();
    write ('cp.mp4', segment);
}

var count = 0; 
var data = [];
wss.on('connection', ws => {
  ws.on('message', message => {
        
    if (!data) {
      data = [ message ];
    } else {
      data.push(message); 
    }
    //doAppending();
    
	count++;
    console.log("count is " + count);
    //console.log(`Received message => ${message}`)
  })
  console.log('Client just connect')
  //ws.send('Hello! Message From Server!!')
  ws.on('close', ws => {
    console.log('Client close');
    //write ('cp.mp4', data);
    var segment = data.shift();
    while (segment) {        
        console.log('write data to file');
        write ('cp.mp4', segment);
        segment = data.shift();
    }
    })
})

