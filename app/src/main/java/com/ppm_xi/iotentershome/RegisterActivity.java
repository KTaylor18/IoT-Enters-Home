package com.ppm_xi.iotentershome;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText fName;
    private EditText lName;
    private EditText email;
    private EditText confirmEmail;
    private EditText passw;
    private EditText confirmPassw;
    private Button regBut;
    private Button logBut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        fName = (EditText)findViewById(R.id.regFN);
        lName = (EditText)findViewById(R.id.regLN);
        email = (EditText)findViewById(R.id.regE1);
        confirmEmail = (EditText)findViewById(R.id.regE2);
        passw = (EditText)findViewById(R.id.regPass1);
        confirmPassw = (EditText)findViewById(R.id.regPass2);
        regBut = (Button)findViewById(R.id.regRegButton);
        logBut = (Button)findViewById(R.id.switchToLog);

        regAction();
        logAction();
    }

    public void onStart(){
        super.onStart();
        FirebaseUser cUser = mAuth.getCurrentUser();
        if(cUser != null){
            Intent startMain = new Intent(getApplicationContext(), MainScreenActivity.class);
            startActivity(startMain);
            //18.01
            //stackoverflow.com/questions/4186021/how-to-start-new-activity-on-button-click
            //developer.android.com/training/basics/firstapp/starting-activity
        }
    }

    public void createUser(String firstName, String lastName, String userEmail, String userPassw){

    }

    public void regAction(){
        regBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //insert code
            }
        });
    }

    public void logAction(){
        logBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switchToLogin = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(switchToLogin);
            }
        });
    }
}
