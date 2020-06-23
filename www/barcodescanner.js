var exec = require('cordova/exec');

function BarcodeScanner() {}

BarcodeScanner.prototype.scan = function(successCallback, errorCallback, opts)  {
    opts = opts || {}
    exec(successCallback, errorCallback, "BarcodeScanner", "scan", [opts])
}

BarcodeScanner.install = function() {
    if (!window.plugins) {
        window.plugins = {};
    }
    window.plugins.barcodeScanner = new BarcodeScanner();
    return window.plugins.barcodeScanner;
}

cordova.addConstructor(BarcodeScanner.install);