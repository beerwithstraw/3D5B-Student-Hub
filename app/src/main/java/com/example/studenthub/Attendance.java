package com.example.studenthub;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Attendance extends AppCompatActivity {

    int numAttended = 0;
    int numMissed = 0;
    TextView attendanceTextView;
    TextView moduleName;

    TextView mAttendancePercentageTextView;
    Button mButtonMissed;
    Button mButtonAttended;


    DatabaseReference RootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mPercentageRef = RootRef.child("percentage");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        mAttendancePercentageTextView=(TextView)findViewById(R.id.attendancePercentageTextView);
        mButtonMissed=(Button)findViewById(R.id.buttonMissed);
        mButtonAttended=(Button)findViewById(R.id.buttonAttended);


        moduleName = findViewById(R.id.ModuleNameText);
        Bundle bn = getIntent().getExtras();
        String modulename = bn.getString("modulename");
        moduleName.setText(String.valueOf(modulename));

        System.out.println("Activity started");
        attendanceTextView = findViewById(R.id.attendancePercentageTextView);

    }
/*
    public void onAttendedClick(View view) {
        System.out.println("On attended was clicked");
        numAttended ++;
        updateAttendancePercentage();
        System.out.println("attended clicked");

    }


    public void onMissedClick(View view) {
        System.out.println("On missed was clicked");
        numMissed ++;
        updateAttendancePercentage();
        System.out.println("missed clicked");

    }

    private void updateAttendancePercentage() {

        double attendedPercentage = 100 * numAttended / (numAttended + numMissed);
        String attendedPercentageAsString = String.valueOf(attendedPercentage);


        attendanceTextView.setText("Attendance: " + attendedPercentageAsString + "%");


    }
*/
    @Override
    protected void onStart(){
        super.onStart();

        mPercentageRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class);
                mAttendancePercentageTextView.setText(text);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mButtonAttended.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numAttended++;
                double attendedPercentage = 100 * numAttended / (numAttended + numMissed);
                String attendedPercentageAsString = String.valueOf(attendedPercentage);

                mPercentageRef.setValue("Attendance: " + attendedPercentageAsString + "%");
            }
        });

        mButtonMissed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numMissed++;
                double attendedPercentage = 100 * numAttended / (numAttended + numMissed);
                String attendedPercentageAsString = String.valueOf(attendedPercentage);
                mPercentageRef.setValue("Attendance: " + attendedPercentageAsString + "%");

            }
        });

    }

}
