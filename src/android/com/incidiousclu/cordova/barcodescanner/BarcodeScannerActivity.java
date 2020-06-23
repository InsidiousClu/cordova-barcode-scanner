package org.apache.cordova.plugin;;

import com.example.tested.R;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.vision.barcode.Barcode;
import androidx.appcompat.app.AppCompatActivity;
import info.androidhive.barcode.BarcodeReader;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class BarcodeScannerActivity extends AppCompatActivity implements BarcodeReader.BarcodeReaderListener {

    public static final String BARCODE_MULTIPLE = "com.incidiousclu.action.BARCODE_MULTIPLE";
    private static final String FLUSH_AWAY = "com.incidiousclu.action.FLUSH_AWAY";
    List<String> barcodeList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);
        Button button = (Button) findViewById(R.id.submit_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FLUSH_AWAY);
                sendBroadcast(i);
                finish();
            }
        });

    }

    @Override
    public void onScanned(Barcode barcode) {
    }

    @Override
    public void onScannedMultiple(List<Barcode> list) {
        Intent intent = new Intent();
        intent.setAction(BARCODE_MULTIPLE);
        for(int i = 0; i < list.size(); i++) {
            Barcode code = list.get(i);
            if(!barcodeList.contains(code.rawValue)) {
                barcodeList.add(code.rawValue);
            }
        }
        intent.putExtra("barcodes","");
        sendBroadcast(intent);
    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {

    }

    @Override
    public void onScanError(String s) {

    }

    @Override
    public void onCameraPermissionDenied() {
        Toast.makeText(this.getApplicationContext(), "Camera permission denied!", Toast.LENGTH_LONG).show();
    }
}