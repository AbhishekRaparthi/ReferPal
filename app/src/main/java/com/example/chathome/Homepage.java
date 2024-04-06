package com.example.chathome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chathome.utils.FirebaseFirestoreDB;
import com.example.chathome.modal.Users;

public class Homepage extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        FirebaseFirestoreDB database=new FirebaseFirestoreDB(this);
//        Users user=database.getUser("kL7NleXHNEFXzAbgCAb4");
//        populateTextView(user);


        //To Open user profile
        ImageView myprofile=findViewById(R.id.myProfile);
        myprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMyProfile();
            }
        });
    }

    void populateTextView(Users user){
        TextView text=findViewById(R.id.textView010);
        text.setText("Username \n Company");


    }

    void openChatWindow(){
        Intent intent=new Intent(this,ChatWindow.class);
        startActivity(intent);
    }

    void openMyProfile(){
        Intent intent=new Intent(this, EditProfileReferee.class);
        startActivity(intent);
    }
}