@objc(BarcodeScanner) class BarcodeScanner : CDVPlugin {
        
    @available(iOS 11.0, *)
    @objc(scan:)
    func scan(command: CDVInvokedUrlCommand) {
        let ctrl = BarcodeScannerCtrl()
        if command.arguments.count >= 1 {
            ctrl.mode = command.arguments[0] as! String
        }
        ctrl.pluginContext = self
        ctrl.command = command
        if #available(iOS 13.0, *) {
            ctrl.isModalInPresentation = true
        }

        self.viewController.present(ctrl, animated: false)
    }
}
