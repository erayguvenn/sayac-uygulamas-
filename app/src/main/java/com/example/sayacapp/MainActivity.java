package com.example.sayacapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView value;
    Button minus,plus,settings;
    SettingsClass settingsClass;

    Vibrator vibrator=null;
    MediaPlayer mediaPlayer=null;

    SensorManager sensorManager;
    Sensor sensorShake;
    SensorEventListener sensorEventListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context context =getApplicationContext();
        settingsClass=SettingsClass.getSettingsClass(context);

        value=(TextView) findViewById(R.id.value);
        plus=(Button) findViewById(R.id.plus);
        minus=(Button) findViewById(R.id.minus);
        settings=(Button) findViewById(R.id.settings);
        vibrator=(Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        mediaPlayer=MediaPlayer.create(context,R.raw.sound);

        sensorManager= (SensorManager) getSystemService (SENSOR_SERVICE);
        sensorShake= sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorEventListener=new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                float x=sensorEvent.values[0];
                float y=sensorEvent.values[1];
                float z=sensorEvent.values[2];

                float sum =Math.abs(x)+Math.abs(y)+Math.abs(z);

                if (sum>15){
                    settingsClass.currentValue=settingsClass.lowValue;
                    value.setText(String.valueOf(settingsClass.currentValue));
                }


            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };


        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               updateValue(1);
            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateValue(-1);

            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,SettingsAtcivity.class));
            }
        });


    }
        public void updateValue(int step){

            if(step>0){
                if(settingsClass.currentValue+step>settingsClass.upValue){
                    settingsClass.currentValue=settingsClass.upValue;
                    if(settingsClass.upVib){
                        alertVib();
                    }
                    if(settingsClass.upSound){
                        alertSound();
                    }


                }
                settingsClass.currentValue+=step;
            }
            if (step<0){
                if(settingsClass.currentValue+step<settingsClass.lowValue){
                    settingsClass.currentValue=settingsClass.lowValue;
                    if(settingsClass.lowVib){
                        alertVib();

                    }
                    if(settingsClass.lowSound){
                        alertSound();
                    }
                }
                settingsClass.currentValue+=step;

            }
            value.setText(String.valueOf(settingsClass.currentValue));

        }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int keyCode = event.getKeyCode();
        int action= event.getAction();

        switch(keyCode){
            case KeyEvent.KEYCODE_VOLUME_DOWN:{
                if(action==KeyEvent.ACTION_DOWN){
                    updateValue(-5);
                }
                return true;
            }
            case KeyEvent.KEYCODE_VOLUME_UP:{
                if(action==KeyEvent.ACTION_UP){
                    updateValue(5);
                }
                return true;

            }
        }

        return super.dispatchKeyEvent(event);
    }
    public void alertSound(){
        mediaPlayer.seekTo(0);
        mediaPlayer.start();
    }
    public void alertVib(){
        if(vibrator.hasVibrator()){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                    vibrator.vibrate(VibrationEffect.createOneShot(1000,VibrationEffect.DEFAULT_AMPLITUDE));
                }
                vibrator.vibrate(1000);

        }

    }
    @Override
    protected void onPostResume() {
        super.onPostResume();
        sensorManager.registerListener(sensorEventListener,sensorShake,SensorManager.SENSOR_DELAY_NORMAL);


    }

    @Override
    protected void onPause() {
        super.onPause();
        settingsClass.saveValue();
        sensorManager.unregisterListener(sensorEventListener,sensorShake);

    }

}