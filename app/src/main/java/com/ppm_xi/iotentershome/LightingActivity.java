package com.ppm_xi.iotentershome;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LightingActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase fDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lighting);

        //22.01.2018
        //stackoverflow.com/questions/42998415/how-to-access-one-key-of-json-tree-from-firebase-realtime-database
        //stackoverflow.com/questions/15082432/how-to-create-button-dynamically-in-android
        mAuth = FirebaseAuth.getInstance();
        fDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ref = fDatabase.getReference("iot-enters-home-2ab63");
        final String uid = mAuth.getUid();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dSnapShot) {
                for (DataSnapshot aSnapShot : dSnapShot.getChildren()){
                    String textToUse = (String) aSnapShot.child("Users").child(uid).child("System").child("Lighting").getValue();
                    Button newButton = new Button(LightingActivity.this);
                    newButton.setText(textToUse);

                    LinearLayout box = (LinearLayout)findViewById(R.id.buttonBox);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.
                            WRAP_CONTENT);
                    box.addView(newButton, layoutParams);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
