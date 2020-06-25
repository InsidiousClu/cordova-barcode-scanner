package com.incidiousclu.cordova.barcodescanner;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.vision.barcode.Barcode;
import androidx.appcompat.app.AppCompatActivity;
import info.androidhive.barcode.BarcodeReader;
import android.util.SparseArray;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.incidiousclu.cordova.barcodescanner.BarcodeScannerReceiver.BARCODE_SINGLE;
import static com.incidiousclu.cordova.barcodescanner.BarcodeScannerReceiver.FLUSH_AWAY;
import static com.incidiousclu.cordova.barcodescanner.BarcodeScannerReceiver.BARCODE_MULTIPLE;

public class BarcodeScannerActivity extends AppCompatActivity implements BarcodeReader.BarcodeReaderListener {
    private static int LIST_EDITED = 255;
    private ArrayList<String> scannedCodes = new ArrayList<String>(10);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);
        handleButtonCreate();
        handleOpenListButtonCreate();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }


    private void handleListIntentCreate() {
        Intent list = new Intent(this.getBaseContext(), ScannedCodesActivity.class);
        list.putStringArrayListExtra("scanned", scannedCodes);
        startActivityForResult(list, LIST_EDITED);
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

    private void handleButtonCreate() {
        Intent intent = new Intent();
        intent.setAction(FLUSH_AWAY);
        Button button = (Button) findViewById(R.id.submit_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putStringArrayListExtra("scannedBarcodes", scannedCodes);
                sendBroadcast(intent);
                finish();
            }
        });
    }

    @Override
    public void onScanned(Barcode barcode) {
        Intent intent = new Intent();
        intent.setAction(BARCODE_SINGLE);
        if(!scannedCodes.contains(barcode.displayValue)) {
            scannedCodes.add(barcode.displayValue);
        }
        sendBroadcast(intent);
    }

    @Override
    public void onScannedMultiple(List<Barcode> list) {
        Intent intent = new Intent();
        intent.setAction(BARCODE_MULTIPLE);
        for(int i = 0; i < list.size(); i++) {
            final Barcode scanned = list.get(i);
            if(!scannedCodes.contains(scanned.displayValue)) {
                scannedCodes.add(scanned.displayValue);
            }
        }
        sendBroadcast(intent);
    }




    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {
        //TODO:implement me
    }

    @Override
    public void onScanError(String s) {
        //TODO:implement me
    }

    @Override
    public void onCameraPermissionDenied() {
        Toast.makeText(this.getApplicationContext(), "Camera permission denied!", Toast.LENGTH_LONG).show();
    }
}