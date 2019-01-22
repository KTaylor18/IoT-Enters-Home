package com.ppm_xi.iotentershome;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainScreenActivity extends AppCompatActivity {

    private Button lightingBut;
    private Button heatingBut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        lightingBut = (Button)findViewById(R.id.lightingButton);
        heatingBut = (Button)findViewById(R.id.heatingButton);

        lightingActivity();
        heatingActivity();
    }

    public void lightingActivity(){
        lightingBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent lighting = new Intent(getApplicationContext(), LightingActivity.class);
                startActivity(lighting);
            }
        });
    }

    public void heatingActivity(){
        heatingBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent heating = new Intent(getApplicationContext(), HeatingActivity.class);
                startActivity(heating);
            }
        });
    }
}
