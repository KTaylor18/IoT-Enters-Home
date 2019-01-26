package com.ppm_xi.iotentershome;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

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

        //22.01.2019
        //stackoverflow.com/questions/42998415/how-to-access-one-key-of-json-tree-from-firebase-realtime-database
        //stackoverflow.com/questions/15082432/how-to-create-button-dynamically-in-android
        //25.01.2019
        //stackoverflow.com/questions/40366717/firebase-for-android-how-can-i-loop-through-a-child-for-each-child-x-do-y
        //26.01.2019
        //stackoverflow.com/questions/7919173/android-set-textview-textstyle-programmatically
        //stackoverflow.com/questions/4602902/how-to-set-the-text-color-of-textview-in-code
        mAuth = FirebaseAuth.getInstance();
        fDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference ref = fDatabase.getReference();
        final String uid = mAuth.getUid();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dSnapShot) {

                LinearLayout box = (LinearLayout) findViewById(R.id.buttonBox);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.
                        WRAP_CONTENT);

                for (final DataSnapshot aSnapShot : dSnapShot.getChildren()){
                    for (final DataSnapshot bSnapShot : aSnapShot.getChildren()) {
                        for (final DataSnapshot cSnapShot : bSnapShot.getChildren()) {
                            for (final DataSnapshot eSnapShot : cSnapShot.getChildren()) {

                                if ((eSnapShot.getKey()).equals("Lighting")){
                                    for (final DataSnapshot fSnapShot : eSnapShot.getChildren()) {
                                        String roomName = fSnapShot.getKey();
                                        addLabel(box, layoutParams, 36, roomName, true);

                                        for (final DataSnapshot gSnapShot : eSnapShot.getChildren()) {
                                            for (final DataSnapshot hSnapShot : gSnapShot.getChildren()){
                                                String objectName = hSnapShot.getKey();
                                                addLabel(box, layoutParams, 30, objectName, false);

                                                String textToUse = hSnapShot.getValue().toString();
                                                Button newButton = new Button(LightingActivity.this);
                                                newButton.setText(textToUse);

                                                box.addView(newButton, layoutParams);

                                                newButton.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        if ((hSnapShot.getValue()).equals("On")){
                                                            ref.child(aSnapShot.getKey()).child(bSnapShot.getKey()).
                                                                    child(cSnapShot.getKey()).child(eSnapShot.getKey()).
                                                                    child(fSnapShot.getKey()).
                                                                    child(hSnapShot.getKey()).setValue("Off");
                                                        } else {
                                                            ref.child(aSnapShot.getKey()).child(bSnapShot.getKey()).
                                                                    child(cSnapShot.getKey()).child(eSnapShot.getKey()).
                                                                    child(fSnapShot.getKey()).
                                                                    child(hSnapShot.getKey()).setValue("Off");
                                                        }
                                                        Toast.makeText(LightingActivity.this, "Updating...", Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(getApplicationContext(), LightingActivity.class);
                                                        startActivity(intent);
                                                    }
                                                });
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void addLabel(LinearLayout box, LinearLayout.LayoutParams layoutParams, int textSize, String name, boolean bold){
        TextView newTextView = new TextView(LightingActivity.this);
        newTextView.setText(name);
        newTextView.setTypeface(Typeface.MONOSPACE);
        if (bold == true){
            newTextView.setTypeface(Typeface.DEFAULT_BOLD);
        }
        newTextView.setTextSize(textSize);
        newTextView.setTextColor(Color.parseColor("#000000"));

        box.addView(newTextView, layoutParams);
    }
}
