package com.ppm_xi.iotentershome;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import java.util.Objects;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lighting);

        //31.01.2019
        //https://stackoverflow.com/questions/14545139/android-back-button-in-the-title-bar
        ActionBar aBar = getSupportActionBar();
        aBar.setDisplayHomeAsUpEnabled(true);
    }
}
