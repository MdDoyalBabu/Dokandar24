package com.example.dokandar24.Common.LocalDb;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class UserDb {
    private Activity activity;

    public UserDb(Activity activity) {
        this.activity = activity;
    }


    public void setUserData(String token,String userType) {
        SharedPreferences sharedPreferences=activity.getSharedPreferences("userDb", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("token",token);
        editor.putString("userType",userType);
        editor.commit();
    }

    public String getAccessToken(){
        SharedPreferences sharedPreferences=activity.getSharedPreferences("userDb", Context.MODE_PRIVATE);
        String token=sharedPreferences.getString("token","");
        return  token;
    }
    public String getUserType() {
        SharedPreferences sharedPreferences = activity.getSharedPreferences("userDb", Context.MODE_PRIVATE);
        String userType = sharedPreferences.getString("userType", "");
        return userType;
    }
}
