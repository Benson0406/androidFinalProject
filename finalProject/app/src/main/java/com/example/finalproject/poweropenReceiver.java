package com.example.finalproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class poweropenReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent){
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            /* 收到廣播後開啟目的Service */
            Intent startServiceIntent = new Intent(context, poweropen.class);
            context.startService(startServiceIntent);
        }
    }
}
