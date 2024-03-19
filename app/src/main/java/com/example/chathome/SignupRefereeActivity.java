package com.example.chathome;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupRefereeActivity extends AppCompatActivity {

    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText emailEditText;
    private EditText skillsEditText;
    private EditText passwordEditText;
    private EditText retypePasswordEditText;

    Button createAccountButton, uploadButton;

    ImageView IVPreviewImage;

    // constant to compare
    // the activity result code
    int SELECT_PICTURE = 200;

//    private Button createAccountButton;
    FirebaseAuth fAuth;


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
        uploadButton = findViewById(R.id.uploadButton);
        IVPreviewImage = findViewById(R.id.IVPreviewImage);
        uploadButton .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });

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
                            Toast.makeText(SignupRefereeActivity.this, "Failed to Create Account" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


                // If validation passes, navigate to the MainActivity

            }
        });
    }

    // this function is triggered when
    // the Select Image Button is clicked
    void imageChooser() {



        // create an instance of the
        // intent of the type image
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    // this function is triggered when user
    // selects the image from the imageChooser
    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                if (selectedImageUri != null) {
                    IVPreviewImage.setImageURI(selectedImageUri);
                    IVPreviewImage.setVisibility(View.VISIBLE);
                }
            }
        } else {
            // Handle case where no image was selected or permission was denied
            IVPreviewImage.setVisibility(View.GONE);
        }
    }

}