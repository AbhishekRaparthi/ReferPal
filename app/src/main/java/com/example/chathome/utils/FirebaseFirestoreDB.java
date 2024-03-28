package com.example.chathome.utils;


import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.example.chathome.modal.Users;
import com.google.firebase.firestore.auth.User;

public class FirebaseFirestoreDB {
    private FirebaseFirestore db=null;
    private static final String USERCOLLECTION="userDetails";
    public FirebaseFirestoreDB() {
        db = FirebaseFirestore.getInstance();
    }

    private FirebaseFirestore getDB(){
        return db;
    }

    public Users getUser(String id){
        Users user=new Users();
        if(db!=null){
            DocumentReference docRef= db.collection(USERCOLLECTION).document("kL7NleXHNEFXzAbgCAb4");
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Log.d("INFO", "DocumentSnapshot data: " + document.getData());
                            String fname=document.getString("firstName");
                            String lname=document.getString("secondName");
                            String company=document.getString("Company");
                            user.setFirstname(fname);
                            user.setLastname(lname);
                            user.setCompany(company);
                            Log.d("INFO", user.toString());


                        } else {
                            Log.d("Error", "No such document");
                        }
                    } else {
                        Log.d("Error", "get failed with ", task.getException());
                    }
                }
            });
        }
        return user;
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
