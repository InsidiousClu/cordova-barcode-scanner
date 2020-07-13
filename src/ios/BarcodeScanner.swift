@objc(BarcodeScanner) class BarcodeScanner : CDVPlugin {
        
    @available(iOS 11.0, *)
    @objc(scan:)
    func scan(command: CDVInvokedUrlCommand) {
        let ctrl = BarcodeScannerCtrl()
        ctrl.pluginContext = self
        ctrl.command = command
        self.viewController.present(ctrl, animated: false)
    }
}
