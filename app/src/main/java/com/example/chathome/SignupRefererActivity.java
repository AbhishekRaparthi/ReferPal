package com.example.chathome;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class SignupRefererActivity extends AppCompatActivity {

    EditText firstNameEditText;
    EditText lastNameEditText;
    EditText companyNameEditText;
    EditText passwordEditText;
    EditText retypePasswordEditText;
    Button createAccountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_referer);

        // Initialize views
        firstNameEditText = findViewById(R.id.firstname);
        lastNameEditText = findViewById(R.id.lastname);
        companyNameEditText = findViewById(R.id.companyname);
        passwordEditText = findViewById(R.id.password);
        retypePasswordEditText = findViewById(R.id.retype);
        createAccountButton = findViewById(R.id.create);

        // Set click listener for "Create Account" button
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validate input fields (e.g., check if passwords match)

                // If validation passes, navigate to the MainActivity
                Intent intent = new Intent(SignupRefererActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}