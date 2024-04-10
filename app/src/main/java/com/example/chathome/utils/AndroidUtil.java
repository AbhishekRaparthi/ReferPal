package com.example.chathome.utils;

import android.content.Intent;

import com.example.chathome.model.Users;

public class AndroidUtil {
    public static void passUserModelAsIntent(Intent intent, Users model){
        intent.putExtra("username",model.getFirstname());
        intent.putExtra("Company",model.getCompany());
        intent.putExtra("skill",model.getSkills());
        intent.putExtra("id",model.getId());

    }

    public static Users getUserModelFromIntent(Intent intent){
        Users userModel = new Users();
        userModel.setFirstname(intent.getStringExtra("username"));
        userModel.setCompany(intent.getStringExtra("company"));
        userModel.setSkills(intent.getStringExtra("skill"));
        userModel.setId(intent.getStringExtra("id"));
        return userModel;
    }
}
