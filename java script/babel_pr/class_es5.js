"use strict";

var _get = function get(object, property, receiver) { if (object === null) object = Function.prototype; var desc = Object.getOwnPropertyDescriptor(object, property); if (desc === undefined) { var parent = Object.getPrototypeOf(object); if (parent === null) { return undefined; } else { return get(parent, property, receiver); } } else if ("value" in desc) { return desc.value; } else { var getter = desc.get; if (getter === undefined) { return undefined; } return getter.call(receiver); } };

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

var myClass = function () {
    function myClass() {
        _classCallCheck(this, myClass);

        this.pro1 = "val1";
        this.pro2 = "val2";
    }

    _createClass(myClass, [{
        key: "privateSet",
        value: function privateSet(new_val1, new_val2) {
            this.pro1 = new_val1;
            this.pro2 = new_val2;
        }
    }, {
        key: "pubGet",
        value: function pubGet() {
            console.log("pro1: " + this.pro1 + " pro2: " + this.pro2);
        }
    }]);

    return myClass;
}();

var child = function (_myClass) {
    _inherits(child, _myClass);

    function child() {
        _classCallCheck(this, child);

        var _this = _possibleConstructorReturn(this, (child.__proto__ || Object.getPrototypeOf(child)).call(this));

        _this.pro3 = "val of child";
        return _this;
    }

    _createClass(child, [{
        key: "privateSet",
        value: function privateSet(new_val1, new_val2, new_val3) {
            this.pro1 = new_val1;
            this.pro2 = new_val2;
            this.pro3 = new_val3;
        }
    }, {
        key: "pubGet",
        value: function pubGet() {
            _get(child.prototype.__proto__ || Object.getPrototypeOf(child.prototype), "pubGet", this).call(this);
            console.log("pro3: " + this.pro3);
        }
    }]);

    return child;
}(myClass);

var child1 = new child();
child1.pubGet();
