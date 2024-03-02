package com.example.chathome;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;


public class MainActivity extends AppCompatActivity {
    EditText username;
    EditText password;
    Button loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHomepage();
//                if(username.getText().toString().equals("user")&& password.getText().toString().equals("1234")){
//                    Toast.makeText(MainActivity.this, "Login Successful !", Toast.LENGTH_SHORT).show();
//                }else {
//                    Toast.makeText(MainActivity.this, "Login Unsuccessful", Toast.LENGTH_SHORT).show();
//                }

            }
        });
        TextView referee = findViewById(R.id.referee);
        referee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Define the intent to navigate to the sign-up activity
                Intent intent = new Intent(MainActivity.this, SignupRefereeActivity.class);
                startActivity(intent);
            }
        });
        TextView referer = findViewById(R.id.referer);
        referer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Define the intent to navigate to the sign-up activity
                Intent intent = new Intent(MainActivity.this, SignupRefererActivity.class);
                startActivity(intent);
            }
        });


    }
    void openHomepage(){
        Intent intent=new Intent(this,Homepage.class);
        startActivity(intent);
    }
}