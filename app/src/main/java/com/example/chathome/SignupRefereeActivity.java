package com.example.chathome;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignupRefereeActivity extends AppCompatActivity {

    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText skillsEditText;
    private EditText passwordEditText;
    private EditText retypePasswordEditText;
    private Button createAccountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_referee);

        // Initialize views
        firstNameEditText = findViewById(R.id.firstname);
        lastNameEditText = findViewById(R.id.lastname);
        skillsEditText = findViewById(R.id.skills);
        passwordEditText = findViewById(R.id.password);
        retypePasswordEditText = findViewById(R.id.retype);
        createAccountButton = findViewById(R.id.create);

        // Set click listener for "Create Account" button
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validate input fields (e.g., check if passwords match)

                // If validation passes, navigate to the MainActivity
                Intent intent = new Intent(SignupRefereeActivity.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(SignupRefereeActivity.this, "Account Created! ", Toast.LENGTH_SHORT).show();
            }
        });
    }
}