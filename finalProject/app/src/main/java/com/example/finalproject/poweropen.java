package com.example.finalproject;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.SystemClock;

public class poweropen  extends Service {
    private PowerManager powerManager;
    private int count;
    private boolean isopen;
    private Intent intent;
    public static final String BROADCAST_ACTION = "com.example.android.finalproject.MainActivity";
    private Handler handler = new Handler();
    private long initial_time;
    long timeInMilliseconds = 0L;

    @Override
    public void onCreate() {
        super.onCreate();
        initial_time = SystemClock.uptimeMillis();
        intent = new Intent(BROADCAST_ACTION);
        handler.removeCallbacks(sendUpdatesToUI);
        handler.postDelayed(sendUpdatesToUI, 1000);
        powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        count = 0;
    }

    private Runnable sendUpdatesToUI = new Runnable() {
        public void run() {
            DisplayLoggingInfo();
            handler.postDelayed(this, 1000);
        }
    };

    private void DisplayLoggingInfo() {
        isopen = powerManager.isInteractive();
        if(isopen){
            count++;
        }
        //timeInMilliseconds = SystemClock.uptimeMillis() - initial_time;

        //int timer = (int) timeInMilliseconds / 1000 - count;
        intent.putExtra("time", count);
        sendBroadcast(intent);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(sendUpdatesToUI);

    }



    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}