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
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

import android.util.Patterns;

public class SignupRefereeActivity extends AppCompatActivity {

    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText emailEditText;
    private EditText skillsEditText;
    private EditText passwordEditText;
    private EditText retypePasswordEditText;
    private Button createAccountButton;
    FirebaseAuth fAuth;

    //    public boolean isValidEmail(CharSequence target) {
//        return (target != null && Patterns.EMAIL_ADDRESS.matcher(target).matches());
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_referee);

        // Initialize views
        firstNameEditText = findViewById(R.id.firstname);
        lastNameEditText = findViewById(R.id.lastname);
        emailEditText = findViewById(R.id.email_referee);
        skillsEditText = findViewById(R.id.skills);
        passwordEditText = findViewById(R.id.password);
        retypePasswordEditText = findViewById(R.id.retype);
        createAccountButton = findViewById(R.id.create);
        fAuth = FirebaseAuth.getInstance();

        // Set click listener for "Create Account" button
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validate input fields (e.g., check if passwords match)
                String firstname = firstNameEditText.getText().toString().trim();
                String lastname = lastNameEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String skills = skillsEditText.getText().toString().trim();
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
                    emailEditText.setError("Email is required");
                }
//                if(isValidEmail(email)){
//emailEditText.setError("Enter email in the right format");
//return;
//                }
                if (skills.isEmpty()) {
                    skillsEditText.setError("skills are required");
                }
                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(SignupRefereeActivity.this, MainActivity.class);
                            startActivity(intent);
                            Toast.makeText(SignupRefereeActivity.this, "Account Created! ", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(SignupRefereeActivity.this, "Failed to Create Account" + task.getException().getMessage(), Toast.LENGTH_SHORT);
                        }
                    }
                });


                // If validation passes, navigate to the MainActivity

            }
        });
    }
}