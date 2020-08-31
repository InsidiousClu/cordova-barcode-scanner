var exec = require('cordova/exec');

function BarcodeScannerMV() {}

BarcodeScannerMV.prototype.scan = function(successCallback, errorCallback, opts)  {
    opts = opts || []
    exec(successCallback, errorCallback, "BarcodeScanner", "scan", opts)
}
var mvScanner = new BarcodeScannerMV();
module.exports = mvScanner;

