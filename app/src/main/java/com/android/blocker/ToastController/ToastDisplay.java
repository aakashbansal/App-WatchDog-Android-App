package com.android.blocker.ToastController;

import android.content.Context;
import android.widget.Toast;

public class ToastDisplay {

    public static void makeLongDurationToast(Context context, String toastMsg) {
        Toast.makeText(context, toastMsg, Toast.LENGTH_LONG).show();
    }

    public static void makeShortDurationToast(Context context, String toastMsg) {
        Toast.makeText(context, toastMsg, Toast.LENGTH_SHORT).show();
    }
}
