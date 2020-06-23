package org.apache.cordova.plugin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;


public class BarcodeScannerReceiver extends BroadcastReceiver {
    BarcodeScannerReceiver() {
        super();
    }
    private static final String FLUSH_AWAY = "com.incidiousclu.action.FLUSH_AWAY";
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction() != null) {
            final String action = intent.getAction();
            if (action.equals(FLUSH_AWAY)) {
                this.abortBroadcast();
            } else {
                Toast.makeText(context, "Action: " + intent.getAction(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
