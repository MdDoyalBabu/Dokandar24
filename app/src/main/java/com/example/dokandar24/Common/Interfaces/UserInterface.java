package com.example.dokandar24.Common.Interfaces;

import android.app.ProgressDialog;
import android.widget.ProgressBar;

import com.example.dokandar24.Common.Model.SellerModel;
import com.example.dokandar24.Common.Responses.RetrofitCashInListResponse;
import com.example.dokandar24.Common.Responses.RetrofitResponses;
import com.example.dokandar24.Common.Responses.RetrofitResponses2;
import com.example.dokandar24.Common.Responses.RetrofitSellerResponses;
import com.example.dokandar24.Common.Responses.RetrofitSendMoneyListResponse;
import com.example.dokandar24.Common.Responses.RetrofitShopResponse;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Part;

public interface UserInterface {
    void registerSeller(SellerModel seller, ProgressBar progressDialog, RetrofitResponses2 retrofitResponses);
    void loginSeller(Map<String,Object> sellerMap, ProgressBar progressDialog, RetrofitResponses2 retrofitResponses);
    void updateProfileImage(MultipartBody.Part image,RequestBody name,String token, ProgressDialog progressDialog, RetrofitResponses retrofitResponses);
    void sendCashInRequest(Map<String,Object> cash,String token, ProgressDialog progressDialog, RetrofitResponses retrofitResponses);
    void sendMoney(Map<String,Object> cash,String token, ProgressDialog progressDialog, RetrofitResponses retrofitResponses);
    void createSellerShop(Map<String,Object> shopMap,String token, ProgressDialog progressDialog, RetrofitResponses retrofitResponses);
    void getShop(String token, ProgressDialog progressDialog, RetrofitShopResponse retrofitResponses);
    void getCurrentUser(String token, ProgressDialog progressDialog, RetrofitSellerResponses retrofitResponses);
    void getCashIn(String token, String cashInType, ProgressDialog progressDialog, RetrofitCashInListResponse retrofitResponse);
    void getSendMoneyHistory(String token,ProgressDialog progressDialog, RetrofitSendMoneyListResponse retrofitResponse);
}
