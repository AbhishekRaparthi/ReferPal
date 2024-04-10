package com.example.chathome;

import androidx.activity.OnBackPressedDispatcher;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.chathome.adapter.SearchUserAdapter;
import com.example.chathome.model.Users;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    SearchUserAdapter adapter;

    TextView title;
    EditText searchInput;
    ImageButton searchButton;
    ImageButton backButton;
    RecyclerView recyclerView;
    ArrayList<Users> users;
    FirebaseFirestore db;
    ProgressDialog progressDialog;

    String field ="company";

    private final OnBackPressedDispatcher onBackPressedDispatcher = getOnBackPressedDispatcher();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        title=findViewById(R.id.search_activity_title);
        searchInput = findViewById(R.id.search_company_input);
        searchButton = findViewById(R.id.search_user_btn);
        backButton = findViewById(R.id.back_btn);


        searchInput.requestFocus();


        backButton.setOnClickListener(v -> {
            onBackPressedDispatcher.onBackPressed();
        });
        SharedPreferences preferences = getSharedPreferences("myPrefs", this.MODE_PRIVATE);
        String userType = preferences.getString("userType", null);

        if(userType.equals("professional")){
            title.setText("Find people based on skills");
            field ="skills";
            searchInput.setHint("Enter skill");
        }

        searchButton.setOnClickListener(v->{

            String enteredCompany=searchInput.getText().toString();
            if(enteredCompany.isEmpty() || enteredCompany.length()<3){
                searchInput.setError("Enter a valid search");
            }else {
                progressDialog=new ProgressDialog(this);
                progressDialog.setCancelable(false);
                progressDialog.show();
                searchInput.setText("");
                setUpSearchRecyclerView(field,enteredCompany);
            }
        });
    }

    public void setUpSearchRecyclerView(String feild,String company){
        db= FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.search_user_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        users=new ArrayList<Users>();
        adapter=new SearchUserAdapter(SearchActivity.this,users);
        recyclerView.setAdapter(adapter);
        db.collection("userDetails").whereEqualTo(feild,company)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error!=null){

                            Log.e("fetch", error.getMessage());
                            return;
                        }
                        for(DocumentChange dc:value.getDocumentChanges()){

                            if(dc.getType()==DocumentChange.Type.ADDED){

                                users.add(dc.getDocument().toObject(Users.class));
                            }

                        }
                        if(progressDialog.isShowing()){
                            progressDialog.dismiss();
                        }

                        adapter.notifyDataSetChanged();

                    }

                });

    }


}