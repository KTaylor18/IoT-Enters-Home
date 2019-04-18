package com.ppm_xi.iotentershome;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

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

        //31.01.2019
        //https://stackoverflow.com/questions/14545139/android-back-button-in-the-title-bar
        ActionBar aBar = getSupportActionBar();
        aBar.setDisplayHomeAsUpEnabled(true);
    }

    //from report (ref Kyle)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //about, log out, preferences, support --> user man, faq
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.support){
            Intent intent = new Intent(getApplicationContext(), SupportActivity.class);
            startActivity(intent);
        } if(item.getItemId()==R.id.about){
            Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
            startActivity(intent);
        } if(item.getItemId()==R.id.logOut){
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(getApplicationContext(), "Sign out successful",
                    Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), MainScreenActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
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
