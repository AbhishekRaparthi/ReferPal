package com.example.chathome;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupRefererActivity extends AppCompatActivity {

    EditText firstNameEditText;
    EditText lastNameEditText;
    EditText companyNameEditText;
    EditText email_referer;
    EditText passwordEditText;
    EditText retypePasswordEditText;
    Button createAccountButton;
    FirebaseAuth fAuth;

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
        email_referer = findViewById(R.id.email_referer);

        fAuth = FirebaseAuth.getInstance();
        // Set click listener for "Create Account" button

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validate input fields (e.g., check if passwords match)
                String firstname = firstNameEditText.getText().toString().trim();
                String lastname = lastNameEditText.getText().toString().trim();
                String email = email_referer.getText().toString().trim();
                String password = passwordEditText.getText().toString();
                String repassword = retypePasswordEditText.getText().toString();
                if (password.equals(repassword)) {
                    retypePasswordEditText.setError(null);
                } else {
                    retypePasswordEditText.setError("Passwords do not match");
                }
                if (firstname.isEmpty()) {
                    firstNameEditText.setError("First Name is required");

                }
                if (password.length() < 6) {
                    passwordEditText.setError("Password must be 6 characters or more");
                }
                if (lastname.isEmpty()) {
                    lastNameEditText.setError("Last Name is required");

                }
                if (email.isEmpty()) {
                    email_referer.setError("Email is required");
                }
                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignupRefererActivity.this, "Account Created", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignupRefererActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(SignupRefererActivity.this, "Error Creating Account" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                // If validation passes, navigate to the MainActivity

            }
        });
    }
}