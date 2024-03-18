package com.example.chathome;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignupRefereeActivity extends AppCompatActivity {

    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText skillsEditText;
    private EditText passwordEditText;
    private EditText retypePasswordEditText;
    Button createAccountButton, uploadButton;

    ImageView IVPreviewImage;

    // constant to compare
    // the activity result code
    int SELECT_PICTURE = 200;
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
        uploadButton = findViewById(R.id.uploadButton);
        IVPreviewImage = findViewById(R.id.IVPreviewImage);
        uploadButton .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });
        // Set click listener for "Create Account" button
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validate input fields (e.g., check if passwords match)

                // If validation passes, navigate to the MainActivity
                Intent intent = new Intent(SignupRefereeActivity.this, MainActivity.class);
                startActivity(intent);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    IVPreviewImage.setImageURI(selectedImageUri);
                }
            }
        }
    }

}