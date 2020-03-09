package com.example.studenthub;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    private Button b1,b2;
    private EditText ed1,ed2;
    private FirebaseAuth mAuth;
    private Button mButton;
    private Button mReminders;

    TextView tx1;
    int counter = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mReminders = (Button) findViewById(R.id.Reminder);
        mReminders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, AlarmPage.class);
                startActivity(intent);
            }
        });


        mButton = (Button) findViewById(R.id.TeacherPage);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent buttonClick = new Intent(Login.this,TeacherLogin.class);
                startActivity(buttonClick);
            }
        });

        mAuth = FirebaseAuth.getInstance();

        b1 = (Button)findViewById(R.id.buttonAttended);
        ed1 = (EditText)findViewById(R.id.ModuleNameText);
        ed2 = (EditText)findViewById(R.id.editText2);

        b2 = (Button)findViewById(R.id.buttonMissed);
        tx1 = (TextView)findViewById(R.id.textView3);
        tx1.setVisibility(View.GONE);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSignIn();
                //Toast.makeText(Login.this, "Button pressed", Toast.LENGTH_SHORT).show();
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void startSignIn(){

        String email = ed1.getText().toString();
        String password = ed2.getText().toString();
        //Toast.makeText(Login.this, "In start sign in with email/password: " + email + password, Toast.LENGTH_SHORT).show();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
            Toast.makeText(getApplicationContext(),
                    "Fields are empty.",Toast.LENGTH_SHORT).show();
            return;
        }

        //mAuth function does not always work for me so included this shortcut to let me login - Billy
        if(TextUtils.equals(email, "admin") & TextUtils.equals(password, "shortcut")){
            Toast.makeText(getApplicationContext(), "Billy's Shortcut",Toast.LENGTH_SHORT).show();
            Intent jumpToHome = new Intent(Login.this, ModuleList.class);
            startActivity(jumpToHome);
        }


        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>(){
            @Override
            public void onComplete(@NonNull Task<AuthResult> task){

                if (task.isSuccessful()){

                    Toast.makeText(getApplicationContext(),
                            "Redirecting...",Toast.LENGTH_SHORT).show();
                    Intent jumpToHome = new Intent(Login.this, ModuleList.class);
                    startActivity(jumpToHome);
                }else{
                    Toast.makeText(getApplicationContext(),
                            "Wrong Credentials",Toast.LENGTH_SHORT).show();

                    tx1.setVisibility(View.VISIBLE);
                    tx1.setTextColor(Color.RED);
                    counter--;
                    tx1.setText(Integer.toString(counter));

                    if (counter == 0) {
                        b1.setEnabled(false);
                    }
                }
            }
        });

    }

}
