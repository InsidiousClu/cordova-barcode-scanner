import UIKit

class ScannedCodesOverview: UIViewController, UITableViewDelegate, UITableViewDataSource {
    
    @IBOutlet weak var tableView: UITableView!
    @IBOutlet weak var navigationBar: UINavigationBar!
    @IBOutlet weak var navItem: UINavigationItem!
    public var scannedCodes: [String] = [String]()
    
    private let cellReuseIdentifier = "BarcodeCell"
    
    var pluginContext: CDVPlugin!
    var command: CDVInvokedUrlCommand!

    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        tableView.frame.size.width = view.frame.size.width
        tableView.isScrollEnabled = true
        configureTopBar()
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        tableView.delegate = self
        tableView.dataSource = self
    }
    
    func configureTopBar() {
        navItem.title = "Scanned Codes"
        navItem.leftBarButtonItem = UIBarButtonItem(title: "Back", style: .plain, target: self, action: #selector(handleBackButtonPressed))
        navItem.rightBarButtonItem = UIBarButtonItem(title: "Submit", style: .plain, target: self, action: #selector(handleBarcodesSubmit))
    }
    
    @objc func handleBackButtonPressed() {
        dismiss(animated: true)
    }
    
    @objc func handleBarcodesSubmit() {
        let result = CDVPluginResult(status: CDVCommandStatus_OK, messageAs: scannedCodes)
        pluginContext.commandDelegate!.send(result, callbackId: command.callbackId)
        pluginContext.viewController.dismiss(animated: true)
    }

    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }

    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return scannedCodes.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        var cell = tableView.dequeueReusableCell(withIdentifier: cellReuseIdentifier)
         if (cell == nil) {
            cell = UITableViewCell(style: UITableViewCellStyle.subtitle, reuseIdentifier: cellReuseIdentifier)
         }
        cell!.textLabel!.text = scannedCodes[indexPath.row]
        return cell!

    }

    
    func tableView(_ tableView: UITableView, titleForHeaderInSection section: Int) -> String? {
        return nil
    }
    
    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        return nil
    }


    // Override to support editing the table view.
    func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCell.EditingStyle, forRowAt indexPath: IndexPath) {
        print("commitEditingStyle", scannedCodes)
        print("commentEditingRow", indexPath.row)
        if editingStyle == .delete {
             scannedCodes.remove(at: indexPath.row)
             tableView.deleteRows(at: [indexPath], with: .fade)
        }
    }

}
