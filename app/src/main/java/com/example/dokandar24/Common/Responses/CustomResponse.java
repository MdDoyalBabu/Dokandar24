package com.example.dokandar24.Common.Responses;

import com.google.gson.annotations.SerializedName;

public class CustomResponse {
    String message;
    @SerializedName("access_token")
      String  token;

    public CustomResponse(){

    }
    public CustomResponse(String message,String token) {
        this.message = message;
        this.token=token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
