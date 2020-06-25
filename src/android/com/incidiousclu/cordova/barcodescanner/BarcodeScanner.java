package com.incidiousclu.cordova.barcodescanner;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;


import static com.incidiousclu.cordova.barcodescanner.BarcodeScannerReceiver.FLUSH_AWAY;
import static com.incidiousclu.cordova.barcodescanner.BarcodeScannerReceiver.BARCODE_MULTIPLE;
import static com.incidiousclu.cordova.barcodescanner.BarcodeScannerReceiver.BARCODE_SINGLE;

public class BarcodeScanner extends CordovaPlugin {
    CallbackContext callbackContext;
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if(action.equals("scan")) {
            this.callbackContext = callbackContext;
            scan();
            return true;
        }
        return false;
    }

    private void scan() {
        Intent i = new Intent(this.cordova.getContext(), BarcodeScannerActivity.class);
        BroadcastReceiver bt = new BarcodeScannerReceiver(this.callbackContext);
        IntentFilter filter = new IntentFilter();

        filter.addAction(BARCODE_MULTIPLE);
        filter.addAction(BARCODE_SINGLE);
        filter.addAction(FLUSH_AWAY);

        this.cordova.getActivity().startActivity(i);
        this.cordova.getActivity().registerReceiver(bt, filter);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        Log.d("REQUEST_CODE", Integer.toString(requestCode));
        Log.d("RESULT_CODE", Integer.toString(resultCode));
    }

    public void onRestoreStateForActivityResult(Bundle state, CallbackContext callbackContext) {
        this.callbackContext = callbackContext;
    }
}