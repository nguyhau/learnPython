let user = {
	  userName: "haophan",
	  displayName: "HaoPhan",
	  sendMessage: function (message) {
		      console.log(`Sending ${message} to ${this.displayName}`);
		    }
}
let student = {
	  displayName: "rohan"
}

user.sendMessage("Hello..."); // Sending Hello... to HaoPhan.
user.sendMessage.call(student, "Hello from HaoPah"); // Sending Hello from HaoPah to rohan.
user.sendMessage.apply(student, ["Hello from HaoPah"]); // Sending Hello from HaoPah to rohan.
user.sendMessage.apply(user, ["echo"]);
let sendMsg = user.sendMessage.bind(student);
sendMsg("AAA"); // Sending AAA to rohan.
