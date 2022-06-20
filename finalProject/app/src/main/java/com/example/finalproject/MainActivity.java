package com.example.finalproject;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

public class MainActivity extends Activity {

    private SharedPreferences mPreferences;
    private String sharedPrefFile =
            "com.example.android.finalproject";
    private ImageButton pauseButton;
    private int number;
    private int time;
    private int i;
    private TextView timerValue;
    private ImageView imageview;
    private boolean isPause = false;
    Intent intent;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageview = (ImageView) findViewById(R.id.imageView2);
        timerValue = (TextView) findViewById(R.id.clock);
        intent = new Intent(MainActivity.this, poweropen.class);
        startService(intent);
        registerReceiver(broadcastReceiver, new IntentFilter(poweropen.BROADCAST_ACTION));
        Button button = findViewById(R.id.button);
        pauseButton = (ImageButton) findViewById(R.id.pauseButton);
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        number = mPreferences.getInt("countnumber",0);
       // number = mPreferences.getInt("countnumber",0) + (int) SystemClock.uptimeMillis()/1000 - mPreferences.getInt("sym",0);
    }
    public void onClick(View view) {
        if(i==0){
            imageview.setBackground(ContextCompat.getDrawable(this,R.drawable.sky));
            i+=1;
        }
        else if(i==1){
            imageview.setBackground(ContextCompat.getDrawable(this,R.drawable.cute));
            i+=1;
        }
        else if(i==2){
            imageview.setBackground(ContextCompat.getDrawable(this,R.drawable.beach));
            i+=1;
        }
        else if(i==3){
            imageview.setBackground(ContextCompat.getDrawable(this,R.drawable.canahela));
            i+=1;
        }
        else if(i==4){
            imageview.setBackground(ContextCompat.getDrawable(this,R.drawable.duck));
            i=0;
        }

    }
    public void Pause(View view) {
        if(isPause){
            number = mPreferences.getInt("countnumber",0);
            startService(intent);
            //registerReceiver(broadcastReceiver, new IntentFilter(poweropen.BROADCAST_ACTION));
            isPause = false;
            pauseButton.setImageDrawable(getDrawable(R.drawable.ic_pause));
            pauseButton.setBackground(getDrawable(R.drawable.pausebutton));
        }
        else{
            //unregisterReceiver(broadcastReceiver);
            stopService(intent);
            SharedPreferences.Editor preferencesEditor = mPreferences.edit();
            preferencesEditor.putInt("countnumber",time);
            preferencesEditor.apply();
            isPause = true;
            pauseButton.setImageDrawable(getDrawable(R.drawable.ic_start));
            pauseButton.setBackground(getDrawable(R.drawable.startbutton));
        }
    }
    public void Reset(View view){
        time = 0;
        number = 0;
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.clear();
        preferencesEditor.apply();
        isPause = true;
        pauseButton.setImageDrawable(getDrawable(R.drawable.ic_start));
        timerValue.setText("00 : 00 : 00");
        stopService(intent);
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            updateUI(intent);
        }
    };

    private void updateUI(Intent intent) {
        time = intent.getIntExtra("time", 0) + number;
        int rounded = (int) Math.round(time);
        int seconds = ((rounded % 86400) % 3600) % 60;
        int minutes = ((rounded % 86400) % 3600) / 60;
        int hours = ((rounded % 86400) / 3600);
        timerValue.setText(String.format("%02d",hours) + " : " + String.format("%02d",minutes) + " : " + String.format("%02d",seconds));
    }
    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onPause(){
        super.onPause();
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putInt("countnumber",time);
        preferencesEditor.apply();
    }
}