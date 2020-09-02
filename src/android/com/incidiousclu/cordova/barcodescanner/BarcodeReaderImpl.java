package com.incidiousclu.cordova.barcodescanner;

import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.view.View;

import info.androidhive.barcode.BarcodeReader;

public class BarcodeReaderImpl extends BarcodeReader {

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return true;
    }
}
