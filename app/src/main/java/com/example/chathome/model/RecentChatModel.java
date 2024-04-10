package com.example.chathome.model;

import com.google.firebase.Timestamp;

import java.util.ArrayList;

public class RecentChatModel {
    String chatroomId, lastMessage, lastMessageSenderId;
    Timestamp timestamp;

    ArrayList<String> users;

    public RecentChatModel() {
    }

    public RecentChatModel(String chatroomId, String lastMessage, String lastMessageSenderId, Timestamp timestamp, ArrayList<String> users) {
        this.chatroomId = chatroomId;
        this.lastMessage = lastMessage;
        this.lastMessageSenderId = lastMessageSenderId;
        this.timestamp = timestamp;
        this.users = users;
    }

    public String getChatroomId() {
        return chatroomId;
    }

    public void setChatroomId(String chatroomId) {
        this.chatroomId = chatroomId;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getLastMessageSenderId() {
        return lastMessageSenderId;
    }

    public void setLastMessageSenderId(String lastMessageSenderId) {
        this.lastMessageSenderId = lastMessageSenderId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public ArrayList<String> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<String> users) {
        this.users = users;
    }
}

