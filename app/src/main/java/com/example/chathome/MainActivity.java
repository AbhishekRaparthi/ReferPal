package com.example.chathome;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;


import com.example.chathome.utils.FirebaseFirestoreDB;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;


public class MainActivity extends AppCompatActivity {
    EditText email_login;
    EditText passwordEdittext;
    Button loginButton;
    FirebaseAuth fAuth;
    TextView fpassTextView;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email_login = findViewById(R.id.email_login);
        passwordEdittext = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        fpassTextView = findViewById(R.id.fpassword);
        fAuth = FirebaseAuth.getInstance();
        SharedPreferences preferences = getSharedPreferences("myPrefs", this.MODE_PRIVATE);
        String userType = preferences.getString("userType", null);
        String userID = preferences.getString("UID", null);
        if (userID != null && userType != null) {
            Log.d(TAG, "User ID " + userID);
            Toast.makeText(MainActivity.this, "Logged In", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, Homepage.class);
            startActivity(intent);
            finish();
        } else {
            Log.d(TAG, "User Type is null");
        }
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = email_login.getText().toString().trim();
                String password = passwordEdittext.getText().toString().trim();
                if (!email.isEmpty() && !password.isEmpty()) {
                    fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseFirestoreDB fireDB = new FirebaseFirestoreDB(MainActivity.this);
                                fireDB.setType();
//                                getType();
                                Toast.makeText(MainActivity.this, "Logged In", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity.this, Homepage.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(MainActivity.this, "Invalid Credentials, Try again!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(MainActivity.this, "Enter valid Email and Password", Toast.LENGTH_SHORT).show();
                }
            }
        });
        TextView referee = findViewById(R.id.referee);
        referee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Define the intent to navigate to the sign-up activity
                Intent intent = new Intent(MainActivity.this, SignupRefereeActivity.class);
                startActivity(intent);
            }
        });
        TextView referer = findViewById(R.id.referer);
        referer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Define the intent to navigate to the sign-up activity
                Intent intent = new Intent(MainActivity.this, SignupRefererActivity.class);
                startActivity(intent);
            }
        });

        fpassTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = passwordEdittext.getText().toString().trim();
                Intent intent = new Intent(MainActivity.this, ResetPassword.class);
                startActivity(intent);

            }
        });

    }

    private void open() {
        Intent intent = new Intent(this, Homepage.class);
        startActivity(intent);
    }

    private void getuid() {

    }



}