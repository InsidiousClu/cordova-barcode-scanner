package com.incidiousclu.cordova.barcodescanner;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class ScannedCodesActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.barcode_list);
        List<String> codes = getIntent().getStringArrayListExtra("scanned");
        if(codes != null && codes.size() > 0) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.barcode_item, codes);
            ListView listView = (ListView) findViewById(R.id.mobile_list);
            listView.setAdapter(adapter);
        }

    }
}
