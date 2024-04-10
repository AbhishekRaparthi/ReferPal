package com.example.chathome;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.chathome.adapter.RecentChatsAdapter;
import com.example.chathome.model.RecentChatModel;
import com.example.chathome.utils.FirebaseFirestoreDB;
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
        FirebaseFirestoreDB database=new FirebaseFirestoreDB(this);
        showChats();
        Button searchButton=findViewById(R.id.homepage_search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Homepage.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        //To Open user profile
        ImageView myprofile=findViewById(R.id.myProfile);
        myprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMyProfile();
            }
        });

    }
    void showChats(){
        progressDialog=new ProgressDialog(this);
        progressDialog.show();
        db= FirebaseFirestore.getInstance();
        recyclerView=findViewById(R.id.homepage_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        chats=new ArrayList<>();
        adapter=new RecentChatsAdapter(Homepage.this,chats);
        recyclerView.setAdapter(adapter);

        SharedPreferences preferences = getSharedPreferences("myPrefs", this.MODE_PRIVATE);
        String userID = preferences.getString("UID", null);


        db.collection("chatrooms").whereArrayContains("userIds",userID)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error!=null){
                            Log.e("fetch", error.getMessage());
                            return;
                        }for(DocumentChange dc:value.getDocumentChanges()){

                            if(dc.getType()==DocumentChange.Type.ADDED){

                                chats.add(dc.getDocument().toObject(RecentChatModel.class));
                            }

                        }if(progressDialog.isShowing()){
                            progressDialog.dismiss();
                        }

                        adapter.notifyDataSetChanged();
                    }
                });
    }

    void openMyProfile(){
        Intent intent=new Intent(this, EditProfileReferee.class);
        startActivity(intent);
    }
}