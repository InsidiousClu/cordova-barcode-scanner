package com.incidiousclu.cordova.barcodescanner;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.samples.vision.barcodereader.BarcodeCapture;
import com.google.android.gms.samples.vision.barcodereader.BarcodeGraphic;
import com.google.android.gms.vision.barcode.Barcode;

import androidx.appcompat.app.AppCompatActivity;
import xyz.belvi.mobilevisionbarcodescanner.BarcodeRetriever;

import android.util.SparseArray;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import static com.incidiousclu.cordova.barcodescanner.BarcodeScannerReceiver.BARCODE_SINGLE;
import static com.incidiousclu.cordova.barcodescanner.BarcodeScannerReceiver.BARCODE_MULTIPLE;

public class BarcodeScannerActivity extends AppCompatActivity implements BarcodeRetriever {
    private ArrayList<String> scannedCodes = new ArrayList<String>(10);
    private static int LIST_OF_BARCODES = 255;
    public String mode = "BARCODE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);

        final Intent intent = getIntent();
        this.mode = intent.getStringExtra("MODE");

        BarcodeCapture barcodeCapture = (BarcodeCapture) getSupportFragmentManager().findFragmentById(R.id.barcode);

        if(barcodeCapture != null) {
            barcodeCapture.setRetrieval(this);
            barcodeCapture.setShowDrawRect(true)
                    .setShouldShowText(true)
                    .setBarcodeFormat(this.mode.equals("QR") ? Barcode.QR_CODE : Barcode.CODABAR | Barcode.CODE_39 | Barcode.CODE_93 | Barcode.CODE_128 | Barcode.UPC_A | Barcode.UPC_E | Barcode.EAN_8 | Barcode.EAN_13 | Barcode.ITF);
            barcodeCapture.refresh();
        }

        handleOpenListButtonCreate();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }


    private void handleListIntentCreate() {
        Intent list = new Intent(this.getBaseContext(), ScannedCodesActivity.class);
        list.putStringArrayListExtra("scanned", scannedCodes);
        startActivityForResult(list, LIST_OF_BARCODES);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == LIST_OF_BARCODES && resultCode == RESULT_OK) {
            finish();
        }
    }

    private void handleOpenListButtonCreate() {
        Button button = (Button) findViewById(R.id.open_list);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleListIntentCreate();
            }
        });
    }


    @Override
    public void onRetrieved(Barcode barcode) {
        Intent intent = new Intent();
        intent.setAction(BARCODE_SINGLE);
        if(!scannedCodes.contains(barcode.displayValue)) {
            scannedCodes.add(barcode.displayValue);
        }
        sendBroadcast(intent);
    }

    @Override
    public void onRetrievedMultiple(Barcode closetToClick, List<BarcodeGraphic> list) {
        Intent intent = new Intent();
        intent.setAction(BARCODE_MULTIPLE);
        for(int i = 0; i < list.size(); i++) {
            final BarcodeGraphic scanned = list.get(i);
            if(!scannedCodes.contains(scanned.getBarcode().displayValue)) {
                scannedCodes.add(scanned.getBarcode().displayValue);
            }
        }
        sendBroadcast(intent);
    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {
        //TODO:implement me
    }

    @Override
    public void onRetrievedFailed(String reason) {

    }

    @Override
    public void onPermissionRequestDenied() {
        Toast.makeText(this.getApplicationContext(), "Camera permission denied!", Toast.LENGTH_LONG).show();
    }

}