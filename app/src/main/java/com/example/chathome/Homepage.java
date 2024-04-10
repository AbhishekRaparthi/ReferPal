package com.example.chathome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chathome.utils.FirebaseFirestoreDB;
import com.example.chathome.modal.Users;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

public class Homepage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        FirebaseFirestoreDB database = new FirebaseFirestoreDB(this);
        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);



        bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Handle logout action
                if (item.getItemId() == R.id.action_edit_profile) {
                    openMyProfile();
                    return true;
                } else if(item.getItemId() == R.id.action_logout){
                    //handles logout logic
                logOut();
                return true;
                } else {
                    return false;
                }

            }
        });

        Button searchButton = findViewById(R.id.homepage_search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Homepage.this, SearchActivity.class);
                startActivity(intent);
            }
        });
    }
    private void logOut() {
        // Sign out the user from Firebase Authentication
        FirebaseAuth.getInstance().signOut();

        // Clear any stored user data or preferences
        // For example, clear the shared preferences:
        SharedPreferences preferences = getSharedPreferences("myPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();

        // Navigate back to the login screen
        Intent intent = new Intent(Homepage.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nav_bottom_menu, menu);
        return true;
    }

    void openChatWindow() {
        Intent intent = new Intent(this, ChatWindow.class);
        startActivity(intent);
    }

    void openMyProfile() {
        Intent intent = new Intent(this, EditProfileReferee.class);
        startActivity(intent);
    }
}