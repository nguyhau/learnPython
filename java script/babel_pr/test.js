//import child from './class5.js';
//var u = new child();
//u.pubGet();
//import display_message from './ex5.js';
//display_message();
//var myex = require(ex)
//myex();
var myMod = require("./ModuleTutorial/my-module-2.js")
myMod.hello();
var fun = require("./ex5.js").default;
fun();
var child = require("./class5.js").default;
var run = new child();
run.pubGet();
//var a = new ch();
//a.pubGet();
var arr = ["orange", "mango", "banana", "sugar", "tea"];  
console.log("Before change " + arr);
var removed = arr.splice(2, 2, "water");  
console.log("After splice(2,1,water) " + arr );  
console.log("removed is: " + removed); 

removed = arr.splice(3, 1);  
console.log("After splice(3,1): " + arr );  
console.log("removed is: " + removed);   
