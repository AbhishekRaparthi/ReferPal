package com.example.chathome.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chathome.R;
import com.example.chathome.modal.Users;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.ArrayList;

public class SearchUserAdapter extends RecyclerView.Adapter<SearchUserAdapter.MyViewHolder>{
    Context context;
    ArrayList<Users> userList;

    public SearchUserAdapter(Context context, ArrayList<Users> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public SearchUserAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(context).inflate(R.layout.recycler_view_row,parent,false);
        return new MyViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull SearchUserAdapter.MyViewHolder holder, int position) {

        Users user=userList.get(position);
        Log.i("USers",user.getFirstname());
        holder.name.setText(user.getFirstname()+" "+user.getLastname());
        holder.company.setText(user.getCompany());
    }

    @Override
    public int getItemCount() {
        return userList.size();
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
