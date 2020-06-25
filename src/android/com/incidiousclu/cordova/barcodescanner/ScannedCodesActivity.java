package com.incidiousclu.cordova.barcodescanner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.incidiousclu.cordova.barcodescanner.BarcodeScannerReceiver.FLUSH_AWAY;

public class ScannedCodesActivity extends Activity {
    ArrayList<String> scannedCodes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.barcode_list);
        handleButtonCreate();
        scannedCodes = getIntent().getStringArrayListExtra("scanned");
        if(scannedCodes != null && scannedCodes.size() > 0) {
            ArrayAdapter<String> adapter = new BarcodeAdapter(this, scannedCodes);
            ListView listView = (ListView) findViewById(R.id.mobile_list);
            listView.setAdapter(adapter);
        }
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
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    private class BarcodeAdapter extends ArrayAdapter<String> {
        public BarcodeAdapter(Context context, ArrayList<String> scanned) {
            super(context, 0, scanned);
        }
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            String item = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.barcode_item, parent, false);
            }
            TextView codeText = (TextView) convertView.findViewById(R.id.label);
            Button removeButton = (Button) convertView.findViewById(R.id.remove_item);
            removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    scannedCodes.remove(position);
                    notifyDataSetChanged();
                }
            });
            codeText.setText(item);
            return convertView;
        }
    }
}
