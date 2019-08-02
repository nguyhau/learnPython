class myClass {
    constructor() {
        this.pro1 = "val1";
        this.pro2 = "val2";
    }
    privateSet(new_val1, new_val2) {
        this.pro1 = new_val1;
        this.pro2 = new_val2;
    }
    pubGet() {
        console.log("pro1: " + this.pro1 + " pro2: " + this.pro2);
    }
}

class child extends myClass{
    constructor(){
        super();
        this.pro3 = "val of child";
    }
    privateSet(new_val1, new_val2, new_val3) {
        this.pro1 = new_val1;
        this.pro2 = new_val2;
        this.pro3 = new_val3;
    }
    pubGet() {
        super.pubGet();        
        console.log("pro3: " + this.pro3);
    }
}
//var chil = new child();
//chil.pubGet();
export default child;
