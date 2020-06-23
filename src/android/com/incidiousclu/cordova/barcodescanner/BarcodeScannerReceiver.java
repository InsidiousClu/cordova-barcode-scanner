package com.incidiousclu.cordova.barcodescanner;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;


public class BarcodeScannerReceiver extends BroadcastReceiver {
    private CallbackContext context;
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
                    PluginResult result = new PluginResult(PluginResult.Status.OK, "SUCCESS EXIT FROM INTENT");
                    this.context.sendPluginResult(result);
                    break;
                case BARCODE_MULTIPLE:
                    this.handleBarcodesScan();
                    break;
                case BARCODE_SINGLE:
                    Log.d("SINGLE/BARCODE", BARCODE_MULTIPLE);
                    break;
            }
        }
    }

    private void handleBarcodesScan() {
        Log.d("MULTIPLE/BARCODES", BARCODE_MULTIPLE);
    }

}
