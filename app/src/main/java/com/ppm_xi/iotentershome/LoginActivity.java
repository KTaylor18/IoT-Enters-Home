package com.ppm_xi.iotentershome;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    //reference the work i did in the summer

    private FirebaseAuth mAuth;
    private EditText email;
    private EditText password;
    private Button logButton;
    private Button regButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        email = (EditText)findViewById(R.id.loginEmail);
        password = (EditText)findViewById(R.id.loginPassword);
        logButton = (Button)findViewById(R.id.loginButton);
        regButton = (Button)findViewById(R.id.switchRegister);

        loginAction();
        registerAction();
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

    public void logInUser(String logEmail, String logPassword){
        mAuth.signInWithEmailAndPassword(logEmail, logPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Log.d("sign in successful");
                            Toast.makeText(getApplicationContext(), "Login successful",
                                    Toast.LENGTH_SHORT).show();
                            finish();
                            Intent mainScreen = new Intent(getApplicationContext(), MainScreenActivity.class);
                            startActivity(mainScreen);
                        } else {
                            Toast.makeText(LoginActivity.this, "Login failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                })
    }

    public void loginAction(){
        logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                private String aEmail = email.getText().toString().trim();
                private String aPassword = password.getText().toString().trim();
                if((TextUtils.isEmpty(aEmail)) || (TextUtils.isEmpty(aPassword)){
                    Toast.makeText(getApplicationContext(), "A required field is empty",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                logInUser(aEmail, aPassword);
            }
        });
    }

    public void registerAction(){
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent switchToReg = new Intent(getApplicationContext(), MainScreenActivity.class);
                startActivity(switchToReg);
            }
        });
    }


}
