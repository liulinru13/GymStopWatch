package com.mmrx.gymstopwatch.util;


import android.os.Looper;

public class Utils {

    public static void enforceMainLooper() {
        if (Looper.getMainLooper() != Looper.myLooper()) {
            throw new IllegalAccessError("May only call from main thread.");
        }
    }
    
}
