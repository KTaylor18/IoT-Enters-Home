package com.ppm_xi.iotentershome;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase fDatabase;
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
        fDatabase = FirebaseDatabase.getInstance();
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

    public void createUser(final String firstName, final String lastName, final String userEmail,
                           String userPassw){
        mAuth.createUserWithEmailAndPassword(userEmail, userPassw)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Registration successful",
                                    Toast.LENGTH_SHORT).show();
                            finish();
                            DatabaseReference ref = fDatabase.getReference();
                            String uid = mAuth.getUid();
                            ref.child("Users").child(uid).child("First Name").setValue(firstName);
                            ref.child("Users").child(uid).child("Last Name").setValue(lastName);
                            ref.child("Users").child(uid).child("Email").setValue(userEmail);
                            ref.child("Users").child(uid).child("System");

                            Intent mainScreen = new Intent(getApplicationContext(),
                                    MainScreenActivity.class);
                            startActivity(mainScreen);
                        } else {
                            Toast.makeText(getApplicationContext(), "Registration failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void regAction(){
        regBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String aFName = fName.getText().toString().trim();
                String aLName = lName.getText().toString().trim();
                String aEmail1 = email.getText().toString().trim();
                String aEmail2 = confirmEmail.getText().toString().trim();
                String aPass1 = passw.getText().toString().trim();
                String aPass2 = confirmPassw.getText().toString().trim();

                if((TextUtils.isEmpty(aFName)) || (TextUtils.isEmpty(aLName)) ||
                        (TextUtils.isEmpty(aEmail1)) || (TextUtils.isEmpty(aEmail2)) ||
                        (TextUtils.isEmpty(aPass1)) || (TextUtils.isEmpty(aPass2))){
                    Toast.makeText(getApplicationContext(), "A required field is empty",
                            Toast.LENGTH_SHORT).show();
                    return;
                } if (!aEmail1.equals(aEmail2)){
                    Toast.makeText(getApplicationContext(), "The email fields must match",
                            Toast.LENGTH_SHORT).show();
                    return;
                } if (!aPass1.equals(aPass2)){
                    Toast.makeText(getApplicationContext(), "The password fields must match",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                createUser(aFName, aLName, aEmail1, aPass1);
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
