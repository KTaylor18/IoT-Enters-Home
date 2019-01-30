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

import java.util.LinkedList;

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
        //30.01.2019
        //stackoverflow.com/questions/7631808/java-splitting-a-string-into-2-strings-based-on-a-delimiter
        //stackoverflow.com/questions/7829034/java-how-to-check-if-a-string-is-a-part-of-any-linkedlist-element
        //stackoverflow.com/questions/29883982/do-not-add-string-if-string-exists-arraylist
        mAuth = FirebaseAuth.getInstance();
        fDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference ref = fDatabase.getReference();
        final LinkedList<String> linkedList = new LinkedList<>();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dSnapShot) {

                LinearLayout box = (LinearLayout) findViewById(R.id.buttonBox);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.
                        WRAP_CONTENT);

                for (final DataSnapshot aSnapShot : dSnapShot.getChildren()) {
                    for (final DataSnapshot bSnapShot : aSnapShot.getChildren()) {
                        for (final DataSnapshot cSnapShot : bSnapShot.getChildren()) {
                            for (final DataSnapshot eSnapShot : cSnapShot.getChildren()) {
                                if ((eSnapShot.getKey()).equals("Lighting")) {
                                    for (final DataSnapshot fSnapShot : eSnapShot.getChildren()) {
                                        String roomName = fSnapShot.getKey();
                                        String[] strings = roomName.split("-", 2);
                                        String rName = strings[0];
                                        if (!linkedList.contains(rName)){
                                            linkedList.add(rName);
                                            addLabel(box, layoutParams, 36, rName, true);
                                        }
                                        for (String r : linkedList) {
                                            Toast.makeText(LightingActivity.this, r, Toast.LENGTH_SHORT).show();
                                            if (r.contains(strings[0])) {
                                                String oName = strings[1];
                                                addLabel(box, layoutParams, 30, oName, false);

                                                String textToUse = fSnapShot.getValue().toString();
                                                Button newButton = new Button(LightingActivity.this);
                                                newButton.setText(textToUse);

                                                box.addView(newButton, layoutParams);

                                                newButton.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        if ((fSnapShot.getValue()).toString().equals("On")) {
                                                            ref.child(aSnapShot.getKey()).child(bSnapShot.getKey()).
                                                                    child(cSnapShot.getKey()).child(eSnapShot.getKey()).
                                                                    child(fSnapShot.getKey()).setValue("Off");
                                                        } else {
                                                            ref.child(aSnapShot.getKey()).child(bSnapShot.getKey()).
                                                                    child(cSnapShot.getKey()).child(eSnapShot.getKey()).
                                                                    child(fSnapShot.getKey()).setValue("On");
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

    public void addLabel(LinearLayout box, LinearLayout.LayoutParams layoutParams, int textSize, String name, boolean bold) {
        TextView newTextView = new TextView(LightingActivity.this);
        newTextView.setText(name);
        newTextView.setTypeface(Typeface.MONOSPACE);
        if (bold == true) {
            newTextView.setTypeface(Typeface.DEFAULT_BOLD);
        }
        newTextView.setTextSize(textSize);
        newTextView.setTextColor(Color.parseColor("#000000"));

        box.addView(newTextView, layoutParams);
    }
}
