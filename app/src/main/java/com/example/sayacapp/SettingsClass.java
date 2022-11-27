package com.example.sayacapp;

import android.content.Context;
import android.content.SharedPreferences;

public class SettingsClass {
    int upValue,lowValue,currentValue;
    boolean upVib,upSound,lowVib,lowSound;

    static SettingsClass settingsClass=null;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private SettingsClass(Context context){
    sharedPreferences =context.getSharedPreferences("settings",Context.MODE_PRIVATE);
    editor=sharedPreferences.edit();
    loadValue();


    }
    public static SettingsClass getSettingsClass(Context context){
        if (settingsClass==null){
            settingsClass=new SettingsClass(context);
        }
        return settingsClass;
    }

    public void saveValue(){
        editor.putInt("upValue",upValue);
        editor.putInt("lowValue",lowValue);
        editor.putInt("currentValue",currentValue);
        editor.putBoolean("upVib",upVib);
        editor.putBoolean("upSound",upSound);
        editor.putBoolean("lowVib",lowVib);
        editor.putBoolean("lowSound",lowSound);
        editor.commit();
    }
    public void loadValue(){
        upValue=sharedPreferences.getInt("upValue",15);
        lowValue=sharedPreferences.getInt("lowValue",0);
        currentValue=sharedPreferences.getInt("currentValue",0);
        upVib=sharedPreferences.getBoolean("upVib",false);
        upSound=sharedPreferences.getBoolean("upSound",false);
        lowVib=sharedPreferences.getBoolean("lowVib",false);
        lowSound=sharedPreferences.getBoolean("lowSound",false);

    }



}
