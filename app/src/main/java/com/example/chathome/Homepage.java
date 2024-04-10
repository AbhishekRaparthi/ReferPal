package com.example.chathome;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.chathome.adapter.RecentChatsAdapter;
import com.example.chathome.model.RecentChatModel;
import com.example.chathome.utils.FirebaseFirestoreDB;
import com.example.chathome.model.Users;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Homepage extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<RecentChatModel> chats;
    RecentChatsAdapter adapter;
    ProgressDialog progressDialog;
    FirebaseFirestore db;

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
                } else if (item.getItemId() == R.id.action_logout) {
                    //handles logout logic
                    logOut();
                    return true;
                } else {
                    return false;
                }
            }
        });

        showChats();
        Button searchButton = findViewById(R.id.homepage_search_button);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Homepage.this, SearchActivity.class);
                startActivity(intent);
            }
        });
    }

    private void openMyProfile() {
        Intent intent = new Intent(this, EditProfileReferee.class);
        startActivity(intent);
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

    void showChats() {
        progressDialog = new ProgressDialog(this);
        progressDialog.show();
        db = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.homepage_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        chats = new ArrayList<>();
        adapter = new RecentChatsAdapter(Homepage.this, chats);
        recyclerView.setAdapter(adapter);

        SharedPreferences preferences = getSharedPreferences("myPrefs", this.MODE_PRIVATE);
        String userID = preferences.getString("UID", null);

        if (userID != null) {
            db.collection("chatrooms").whereArrayContains("userIds", userID)
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            if (error != null) {
                                Log.e("fetch", error.getMessage());
                                return;
                            }
                            for (DocumentChange dc : value.getDocumentChanges()) {
                                if (dc.getType() == DocumentChange.Type.ADDED) {
                                    chats.add(dc.getDocument().toObject(RecentChatModel.class));
                                }
                            }
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            adapter.notifyDataSetChanged();
                        }
                    });
        } else {
            Log.d("Homepage", "UserID is null");
        }
    }

    private void openChatWindow() {
        Intent intent = new Intent(this, ChatWindow.class);
        startActivity(intent);
    }
}