@objc(BarcodeScanner) class BarcodeScanner : CDVPlugin {
        
    @available(iOS 11.0, *)
    @objc(scan:)
    func scan(command: CDVInvokedUrlCommand) {
        let ctrl = BarcodeScannerCtrl()
        ctrl.pluginContext = self
        ctrl.command = command
        if #available(iOS 13.0, *) {
            ctrl.isModalInPresentation = true
        }
        self.viewController.present(ctrl, animated: false)
    }
}
