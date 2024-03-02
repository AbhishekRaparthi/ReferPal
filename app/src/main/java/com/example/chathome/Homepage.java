package com.example.chathome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Homepage extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        LinearLayout allchats=findViewById(R.id.scrollLayout);
        int count=allchats.getChildCount();
        for(int i=0;i<count;i++){
            View child=allchats.getChildAt(i);
            child.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    // click handling code
                    TextView tv=(TextView) view;
                    openChatWindow();
                }
            });;

        }

        ImageView myprofile=findViewById(R.id.myProfile);
        myprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMyProfile();
            }
        });
    }

    void openChatWindow(){
        Intent intent=new Intent(this,ChatWindow.class);
        startActivity(intent);
    }

    void openMyProfile(){
        Intent intent=new Intent(this,EditProfile.class);
        startActivity(intent);
    }
}