package com.example.chathome;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity {
    private EditText cemailEditText;
    private Button changepassButton;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        cemailEditText = findViewById(R.id.cemail);
        changepassButton = findViewById(R.id.changepass);
        fAuth = FirebaseAuth.getInstance();
        changepassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = cemailEditText.getText().toString().trim();
                if (!email.isEmpty()) {
                    fAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {
                                Intent intent = new Intent(ResetPassword.this, MainActivity.class);
                                startActivity(intent);
                                Toast.makeText(ResetPassword.this, "Password Reset Link sent to your email", Toast.LENGTH_SHORT).show();
                            } else {
                                cemailEditText.setError("Enter valid email address!");
                            }

                        }
                    });
                }else {
                    cemailEditText.setError("Email cannot be empty");
                }
            }
        });


    }
}
