package com.example.sayacapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

public class SettingsAtcivity extends AppCompatActivity {

    Button upMinus,upPlus,lowMinus,lowPlus;
    TextView upValue,lowValue;
    CheckBox upSound,lowSound,upVib,lowVib;

    SettingsClass settingsClass;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_atcivity);

        upMinus = (Button) findViewById(R.id.upMinus);
        upPlus = (Button) findViewById(R.id.upPlus);
        lowMinus = (Button) findViewById(R.id.lowMinus);
        lowPlus= (Button) findViewById(R.id.lowPlus);
        upValue= (TextView) findViewById(R.id.upValue);
        lowValue= (TextView) findViewById(R.id.lowValue);
        upSound=(CheckBox) findViewById(R.id.upSound);
        lowSound=(CheckBox) findViewById(R.id.lowSound);
        upVib=(CheckBox) findViewById(R.id.upVib);
        lowVib=(CheckBox) findViewById(R.id.lowVib);

        settingsClass=SettingsClass.getSettingsClass(getApplicationContext());



        upMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settingsClass.upValue--;
                upValue.setText(String.valueOf(settingsClass.upValue));
            }
        });
        upPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settingsClass.upValue++;
                upValue.setText(String.valueOf(settingsClass.upValue));
            }
        });
        lowMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settingsClass.lowValue--;
                lowValue.setText(String.valueOf(settingsClass.lowValue));
            }
        });
        lowPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settingsClass.lowValue++;
                lowValue.setText(String.valueOf(settingsClass.lowValue));
            }
        });

        upVib.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                settingsClass.upVib=b;
            }
        });

        upSound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                settingsClass.upSound=b;
            }
        });

        lowVib.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                settingsClass.lowVib=b;
            }
        });
        lowSound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                settingsClass.lowSound=b;
            }
        });

        upValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(upValue.getText().toString().length()!=0){
                    settingsClass.upValue=Integer.parseInt(upValue.getText().toString());
                }
            }
        });

        lowValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(lowValue.getText().toString().length()!=0){
                    settingsClass.lowValue=Integer.parseInt(lowValue.getText().toString());
                }
            }
        });


    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        upValue.setText(String.valueOf(settingsClass.upValue));
        lowValue.setText(String.valueOf(settingsClass.lowValue));
        upVib.setChecked(settingsClass.upVib);
        upSound.setChecked(settingsClass.upSound);
        lowVib.setChecked(settingsClass.lowVib);
        lowSound.setChecked(settingsClass.lowSound);



    }

    @Override
    protected void onPause() {
        super.onPause();
        settingsClass.saveValue();
    }
}