package com.example.chathome;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditProfileReferee extends AppCompatActivity {

    private EditText first_name;
    private EditText last_name;
    private EditText skills;

    private static final int PICK_IMAGE_REQUEST = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        populateData();
    }
    public void populateData(){
        FirebaseAuth auth=FirebaseAuth.getInstance();

        String uid=auth.getCurrentUser().getUid();
        DocumentReference docRef= FirebaseFirestore.getInstance().collection("userDetails").document(uid);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                EditText firstNameEditText = findViewById(R.id.first_name);
                EditText lastNameEditText = findViewById(R.id.last_name);
                EditText skillsEditText = findViewById(R.id.skills_referee);

                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        String fname=document.getString("firstName");
                        String lname=document.getString("secondName");
                        String skills=document.getString("Skills");

                        firstNameEditText.setText(fname);
                        lastNameEditText.setText(lname);
                        skillsEditText.setText(skills);



                    } else {
                        Log.d("Error", "No such document");
                    }
                } else {
                    Log.d("Error", "get failed with ", task.getException());
                }
            }
        });
    }

    public void uploadImage(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            // Set the selected image to ImageButton
            ImageButton imageButton = findViewById(R.id.uploadImage);
            imageButton.setImageURI(imageUri);

        }


    }
}