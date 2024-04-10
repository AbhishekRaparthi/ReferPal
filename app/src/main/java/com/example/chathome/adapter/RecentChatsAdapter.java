package com.example.chathome.adapter;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chathome.ChatWindow;
import com.example.chathome.R;
import com.example.chathome.model.RecentChatModel;
import com.example.chathome.model.Users;
import com.example.chathome.utils.AndroidUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Objects;

public class RecentChatsAdapter extends RecyclerView.Adapter<RecentChatsAdapter.MyViewHolder>{
    Context context;
    ArrayList<RecentChatModel> chats;
    FirebaseAuth auth=FirebaseAuth.getInstance();

    public RecentChatsAdapter(Context context,ArrayList<RecentChatModel> chats){
        this.context=context;
        this.chats=chats;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.recycler_view_row,parent,false);
        return new RecentChatsAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentChatsAdapter.MyViewHolder holder, int position) {
        RecentChatModel chat=chats.get(position);
        holder.company.setText(chat.getLastMessage());
        String[] ids=chat.getChatroomId().split("_");

        String currentID= Objects.requireNonNull(auth.getCurrentUser()).getUid();
        String id=currentID.equals(ids[0])?ids[1]:ids[0];
        Log.d("Firebase","Test "+ id);

        //we need a user object using currentID;

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("userDetails").document(id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                // User details exist, extract them
                                String userName = document.getString("firstname");
                                // Do something with the user details, for example set them to TextViews
                                holder.name.setText(userName);
                                Users user=new Users();
                                user.setFirstname(document.getString("firstname"));
                                user.setCompany(document.getString("company"));
                                user.setSkills(document.getString("skills"));
                                user.setId(document.getString("id"));

                                //here on click for chat activity
                                holder.itemView.setOnClickListener(v->{
                                    Intent intent=new Intent(context, ChatWindow.class);
                                    AndroidUtil.passUserModelAsIntent(intent,user);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(intent);
                                });

                                Log.d("firebase","user"+user.toString());
                            } else {
                                // Handle the case where the user data doesn't exist
                                Log.d("Firebase", "No user details found for ID: " + id);
                            }
                        } else {
                            // Handle potential errors
                            Log.e("Firebase", "Error getting user details", task.getException());
                        }
                    }
                });

    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    public static class MyViewHolder extends  RecyclerView.ViewHolder{
        TextView name,company;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.user_name_text);
            company=itemView.findViewById(R.id.company_name_text);
        }
    }

}
