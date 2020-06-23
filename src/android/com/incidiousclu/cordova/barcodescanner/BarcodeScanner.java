package org.apache.cordova.plugin;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;


import static org.apache.cordova.plugin.BarcodeScannerActivity.BARCODE_MULTIPLE;

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
        BroadcastReceiver bt = new BarcodeScannerReceiver();
        IntentFilter filter;
        filter = new IntentFilter(BARCODE_MULTIPLE);
        this.cordova.getActivity().startActivity(i);
        this.cordova.getActivity().registerReceiver(bt, filter);
    }
}