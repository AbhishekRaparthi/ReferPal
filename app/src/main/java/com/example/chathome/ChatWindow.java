package com.example.chathome;

import androidx.activity.OnBackPressedDispatcher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chathome.adapter.ChatRecyclerAdapter;
import com.example.chathome.model.ChatMessageModel;
import com.example.chathome.model.ChatRoom;
import com.example.chathome.model.Users;
import com.example.chathome.utils.AndroidUtil;
import com.example.chathome.utils.FirebaseFirestoreDB;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Query;

import java.util.Arrays;

public class ChatWindow extends AppCompatActivity {

    String chatroomID;
    ImageButton back;
    ImageButton send;
    private final OnBackPressedDispatcher onBackPressedDispatcher = getOnBackPressedDispatcher();
    TextView name;
    EditText messageInput;
    Users user;
    RecyclerView recyclerView;

    String currentUserID;
    FirebaseFirestoreDB firebaseFirestoreDB;
    ChatRoom chatroomModel;

    ChatRecyclerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        SharedPreferences preferences = getSharedPreferences("myPrefs", this.MODE_PRIVATE);
        firebaseFirestoreDB=new FirebaseFirestoreDB();
        chatroomModel=new ChatRoom();

        currentUserID = preferences.getString("UID", null);

        user= AndroidUtil.getUserModelFromIntent(getIntent());
        chatroomID= firebaseFirestoreDB.getChatroomId(currentUserID,user.getId());

        name=findViewById(R.id.other_username);
        back=findViewById(R.id.back_btn);
        send=findViewById(R.id.message_send_btn);
        messageInput=findViewById(R.id.chat_message_input);
        recyclerView=findViewById(R.id.chat_recycler_view);

        String feild="";
        try{
            if(user.getCompany()!=null){
                feild= user.getCompany();
            }else if(user.getSkills()!=null){
                feild=user.getSkills();
            }
        }catch(Exception e){};

        send.setOnClickListener(v->{
            String msg=messageInput.getText().toString();
            if(!msg.equals("")){
                sendMessage(msg);
            }else{
                messageInput.setError("Enter a message to send");
            }
        });


        name.setText(user.getFirstname()+"\n"+feild);
        back.setOnClickListener(v->{
            onBackPressedDispatcher.onBackPressed();
        });

        getOrCreateChatroomModal();
        setupChatRecyclerView();
    }
    void setupChatRecyclerView(){

        Query query = firebaseFirestoreDB.getChatroomMessageReference(chatroomID)
                .orderBy("timestamp", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<ChatMessageModel> options = new FirestoreRecyclerOptions.Builder<ChatMessageModel>()
                .setQuery(query,ChatMessageModel.class).build();

        adapter = new ChatRecyclerAdapter(options,getApplicationContext());
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setReverseLayout(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.startListening();
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                recyclerView.smoothScrollToPosition(0);
            }
        });

    }

    public void sendMessage(String msg){
        chatroomModel.setLastMessageTimestamp(Timestamp.now());
        chatroomModel.setLastMessageSenderId(currentUserID);
        chatroomModel.setLastMessage(msg);

        firebaseFirestoreDB.getChatRoomReference(chatroomID).set(chatroomModel);

        ChatMessageModel chatMessageModel=new ChatMessageModel(msg,currentUserID,Timestamp.now());

        firebaseFirestoreDB.getChatroomMessageReference(chatroomID).add(chatMessageModel)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if(task.isSuccessful()){
                            messageInput.setText("");
                        }
                    }
                });

    }
    public void getOrCreateChatroomModal(){
        firebaseFirestoreDB.getChatRoomReference(chatroomID).get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                chatroomModel = task.getResult().toObject(com.example.chathome.model.ChatRoom.class);
                if(chatroomModel==null){
                    //first time chat
                    chatroomModel = new ChatRoom(
                            chatroomID,
                            Arrays.asList(currentUserID,user.getId()),
                            Timestamp.now(),
                            ""
                    );
                    firebaseFirestoreDB.getChatRoomReference(chatroomID).set(chatroomModel);
                }
            }
        });
    }


}