package com.example.chathome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ChatWindow extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);


        TextView edittext=findViewById(R.id.msg);
        Button send=findViewById(R.id.send);
        LinearLayout container=findViewById(R.id.messages);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message=edittext.getText().toString();
                edittext.setText(" ");
                TextView text=new TextView(ChatWindow.this);
                text.setText(message);
                text.setTextSize(20);
                text.setBackgroundColor(Color.parseColor("#D3D3D3"));
                text.setTextColor(Color.parseColor("#000000"));
                text.setPadding(30,10,30,10);
                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                text.setLayoutParams(param);
                container.addView(text);
            }
        });

        TextView viewProfile=findViewById(R.id.ViewProfile);
        viewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProfile();
            }
        });

    }

    void showProfile(){
        Intent intent=new Intent(this,PublicProfile.class);
        startActivity(intent);
    }
}