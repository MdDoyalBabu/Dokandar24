package com.example.dokandar24.Common.Interfaces;

import android.app.ProgressDialog;
import android.widget.ProgressBar;

import com.example.dokandar24.Common.Model.SellerModel;
import com.example.dokandar24.Common.Responses.RetrofitResponses;
import com.example.dokandar24.Common.Responses.RetrofitResponses2;
import com.example.dokandar24.Common.Responses.RetrofitSellerResponses;

import java.util.HashMap;
import java.util.Map;

public interface UserInterface {

    void registerSeller(SellerModel seller, ProgressBar progressDialog, RetrofitResponses2 retrofitResponses);
    void loginSeller(Map<String,Object> sellerMap, ProgressBar progressDialog, RetrofitResponses2 retrofitResponses);
    void getCurrentUser(String token, ProgressDialog progressDialog, RetrofitSellerResponses retrofitResponses);
}
