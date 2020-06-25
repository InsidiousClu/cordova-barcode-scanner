package com.incidiousclu.cordova.barcodescanner;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class BarcodeScannerReceiver extends BroadcastReceiver {
    private CallbackContext context;
    private List<String> scannedBarcodes = new ArrayList<String>();

    public static final String FLUSH_AWAY = "com.incidiousclu.action.FLUSH_AWAY";
    public static final String BARCODE_MULTIPLE = "com.incidiousclu.action.BARCODE_MULTIPLE";
    public static final String BARCODE_SINGLE = "com.incidiousclu.action.BARCODE_SINGLE";

    BarcodeScannerReceiver(CallbackContext context) {
        super();
        this.context = context;
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null) {
            final String action = intent.getAction();
            switch (action) {
                case FLUSH_AWAY:
                    this.handleFlushAway(intent);
                    break;
                case BARCODE_MULTIPLE:
                    Log.d("MULTIPLE/BARCODES", BARCODE_MULTIPLE);
                    break;
                case BARCODE_SINGLE:
                    Log.d("SINGLE/BARCODE", BARCODE_MULTIPLE);
                    break;
            }
        }
    }

    private void handleFlushAway(Intent intent) {
        final Bundle extras = intent.getExtras();
        if(extras != null) {
            final List<String> res = extras.getStringArrayList("scannedBarcodes");
            if(res != null) {
                try {
                    Log.d("STATE", res.toString());
                    final JSONArray scannedCodes = new JSONArray(res);
                    JSONObject object = new JSONObject();
                    object.put("codes", scannedCodes);
                    PluginResult result = new PluginResult(PluginResult.Status.OK, "");
                    this.context.sendPluginResult(result);
                } catch (JSONException e) {
                    this.context.error(e.getMessage());
                }

            } else {
                this.context.error("SCANNED_BARCODES are empty");
            }
        }
    }
}
