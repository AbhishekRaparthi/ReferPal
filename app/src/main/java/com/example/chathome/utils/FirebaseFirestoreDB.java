package com.example.chathome.utils;


import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.util.Log;
import android.content.Context;
import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.example.chathome.modal.Users;
import com.google.firebase.firestore.auth.User;

public class FirebaseFirestoreDB {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String USERCOLLECTION="userDetails";
    public FirebaseFirestoreDB() {
    }
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    private Context mContext; // Add Context field

    public FirebaseFirestoreDB(Context context) { // Constructor with Context parameter
        mContext = context;
    }

    private FirebaseFirestore getDB(){
        return db;
    }

    public void setType() {
//        Users user = new Users();
        FirebaseUser currentUser = fAuth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            DocumentReference docRef = db.collection("userDetails").document(userId);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            String type = document.getString("type");
                            if (type != null) {
                                // Use the type
                                Log.d(TAG, "Type: " + type);
                                Log.d(TAG, "UID: " + userId);

                                // Store the type in SharedPreferences
                                if (mContext != null) {

                                    SharedPreferences preferences = mContext.getSharedPreferences("myPrefs", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putString("userType", type);
                                    editor.putString("UID", userId);
                                    editor.apply();
                                }else{
                                    Log.d(TAG, "mContext is null, unable to access SharedPreferences");

                                }

                                // Now you can access the type from SharedPreferences
                            } else {
                                Log.d(TAG, "Type attribute is null");
                            }


                        } else {
                            Log.d("Error", "No such document");
                        }
                    } else {
                        Log.d("Error", "get failed with ", task.getException());
                    }
                }
            });
        }
    }
    public void setUser(Users user,String uid){
        DocumentReference documentReference = db.collection(USERCOLLECTION).document(uid);

        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d(TAG, "onSuccess: User data stored in DB"+uid);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: "+e.toString());

            }
        });
    }
}
