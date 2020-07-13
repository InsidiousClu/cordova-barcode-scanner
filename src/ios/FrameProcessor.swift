import UIKit
import AVFoundation
import Vision

@available(iOS 11.0, *)
protocol FrameProcessorDelegate: class {
    func onBarcodesRead(barcodes: [VNBarcodeObservation])
}

@available(iOS 11.0, *)
class FrameProcessor: NSObject, AVCaptureVideoDataOutputSampleBufferDelegate {
    
    private let position = AVCaptureDevice.Position.back
    private let quality = AVCaptureSession.Preset.hd1280x720
    
    private var permissionGranted = false
    private let sessionQueue = DispatchQueue(label: "session_queue")
    public let captureSession = AVCaptureSession()
    private let context = CIContext()
    private var barcodeRequest: VNDetectBarcodesRequest?
    weak var delegate: FrameProcessorDelegate?
    
    func barcodeRequesterHandler(request: VNRequest, error: Error?) {
        guard let results = request.results else {
            return
        }
        if let barcodes = results as? [VNBarcodeObservation] {
            self.delegate?.onBarcodesRead(barcodes: barcodes)
        }
    }
    
    
    override init() {
        super.init()
        checkPermission()
        sessionQueue.async { [unowned self] in
            self.configureSession()
            self.barcodeRequest = VNDetectBarcodesRequest(completionHandler: self.barcodeRequesterHandler)
            self.captureSession.startRunning()
        }
    }
    
    func close() {
        self.captureSession.stopRunning()
    }

    
    private func checkPermission() {
        switch AVCaptureDevice.authorizationStatus(for: AVMediaType.video) {
        case .authorized:
            permissionGranted = true
        case .notDetermined:
            requestPermission()
        default:
            permissionGranted = false
        }
    }

    
    private func requestPermission() {
        sessionQueue.suspend()
        AVCaptureDevice.requestAccess(for: AVMediaType.video) { [unowned self] granted in
            self.permissionGranted = granted
            self.sessionQueue.resume()
        }
    }
    
    private func configureSession() {
        guard permissionGranted else { return }
        captureSession.sessionPreset = quality
        guard let captureDevice = selectCaptureDevice() else { return }
        guard let captureDeviceInput = try? AVCaptureDeviceInput(device: captureDevice) else { return }
        
        guard captureSession.canAddInput(captureDeviceInput) else { return }
        captureSession.addInput(captureDeviceInput)
        
        let videoOutput = AVCaptureVideoDataOutput()
        videoOutput.setSampleBufferDelegate(self, queue: DispatchQueue.global(qos:DispatchQoS.QoSClass.default))
        guard captureSession.canAddOutput(videoOutput) else { return }
        captureSession.addOutput(videoOutput)
        guard let connection = videoOutput.connection(with: AVFoundation.AVMediaType.video) else { return }
        guard connection.isVideoOrientationSupported else { return }
        guard connection.isVideoMirroringSupported else { return }
        connection.videoOrientation = .portrait
        connection.isVideoMirrored = position == .front
    }
    
    private func selectCaptureDevice() -> AVCaptureDevice? {
        var currentDevice: AVCaptureDevice?
        let deviceDiscoverySession = AVCaptureDevice.DiscoverySession(deviceTypes: [AVCaptureDevice.DeviceType.builtInWideAngleCamera], mediaType: AVMediaType.video, position: .back)
        let devices = deviceDiscoverySession.devices
        for device in devices {
            if device.position == AVCaptureDevice.Position.back {
                currentDevice = device
            }
        }
        return currentDevice
    }
    

    
    func captureOutput(_ output: AVCaptureOutput, didOutput sampleBuffer: CMSampleBuffer, from connection: AVCaptureConnection) {
        guard
            let pixelBuffer = CMSampleBufferGetImageBuffer(sampleBuffer)
            else { return }
        
       
        var requestOptions:[VNImageOption : Any] = [:]

        if let camData = CMGetAttachment(sampleBuffer, kCMSampleBufferAttachmentKey_CameraIntrinsicMatrix, nil) {
            requestOptions = [.cameraIntrinsics:camData]
        }

        let handler = VNImageRequestHandler(cvPixelBuffer: pixelBuffer, orientation: CGImagePropertyOrientation(rawValue: 1)!, options: requestOptions)

        guard let _ = try? handler.perform([self.barcodeRequest!]) else {
            return print("Could not perform barcode-request!")
        }
    }
}
