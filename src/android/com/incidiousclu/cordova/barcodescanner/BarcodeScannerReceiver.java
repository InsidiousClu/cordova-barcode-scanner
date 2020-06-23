package org.apache.cordova.plugin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;


public class BarcodeScannerReceiver extends BroadcastReceiver {
    private CallbackContext context;
    BarcodeScannerReceiver(CallbackContext context) {
        super();
        this.context = context;
    }
    private static final String FLUSH_AWAY = "com.incidiousclu.action.FLUSH_AWAY";
    private static final String TAG = "BarcodeReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction() != null) {
            final String action = intent.getAction();
            Log.d("ACTION", action);
            if (action.equals(FLUSH_AWAY)) {
                PluginResult result = new PluginResult(PluginResult.Status.OK, "SUCCESS EXIT FROM INTENT");
                this.context.sendPluginResult(result);
            } else {
                Toast.makeText(context, "Action: " + intent.getAction(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
