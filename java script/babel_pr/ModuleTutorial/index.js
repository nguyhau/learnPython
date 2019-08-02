 var myModule = require("./my-module-2")
// var myModule = require("./my_modules/my-module-1")
//var myModule = require("my-module")

myModule.hello()

console.log('PI: ' + myModule.PI)

var rectangle = new myModule.Rectangle(2,3)
rectangle.showInfo()
rectangle.showArea()
rectangle.showCircuit()
