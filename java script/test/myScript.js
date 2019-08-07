const Websocket = require('ws');
var wsUri = 'ws://test.domain.com:8080/SampleWeb/endpoint';
var websocket = new WebSocket(wsUri);
var num_pkg = 0;

websocket.addEventListener('message', function(e) {
	if (typeof e.data !== 'string') {
		num_pkg++;
		console.log('Receive ' + num_pkg +' package');
	}
}, false);



////////////////////////////////////////////////
// Button handle
////////////////////////////////////////////////
//*** Config ***
function configRTP() {
	var selectMulticastIFObj = document.getElementById("selectMulticastIFNum");
	var lSel = selectMulticastIFObj.options[selectMulticastIFObj.selectedIndex].value;
	var str;
	//str = "Config IP:" + document.control.setip1.value + "; port:" + document.control.setport1.value + "; ifnumb:" + lSel;
	str = "Config IP:224.4.0.1 ; port:50420; ifnumb:0";
	console.log('Send string ' + str);
	websocket.send(str);
}
//*** Play ***
function videoplay() {
	//websocket.send('Start');
	if (Wfs.isSupported()) {
		var video1 = document.querySelector('video');
        //var video1 = document.getElementById("video1"),
        wfs = new Wfs();    
        wfs.attachMedia(video1, websocket);
    } else {
    	console.log(" Wfs not support");
    }
}
//*** Stop ***
function videostop() {
	websocket.send('Stop');
}
//*** Stop ***
function videopause() {
	websocket.send('Pause');
}
videoplay();

