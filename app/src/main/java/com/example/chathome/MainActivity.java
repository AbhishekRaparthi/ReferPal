package com.example.chathome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {
    EditText email_login;
    EditText passwordEdittext;
    Button loginButton;
    FirebaseAuth fAuth;
    TextView fpassTextView;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email_login = findViewById(R.id.email_login);
        passwordEdittext = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        fpassTextView = findViewById(R.id.fpassword);
        fAuth = FirebaseAuth.getInstance();
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
                                String uid = fAuth.getCurrentUser().getUid();
                                preferences = getSharedPreferences("myPrefs", MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("uid", uid);
                                editor.apply();
                                Toast.makeText(MainActivity.this, "Logged In", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity.this, Homepage.class);
                                startActivity(intent);
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
}