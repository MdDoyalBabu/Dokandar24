package com.example.dokandar24.Common.Responses;

import android.app.ProgressDialog;

import com.example.dokandar24.Common.Model.SellerModel;

public interface RetrofitSellerResponses {
    void onSuccess(String message, ProgressDialog progressDialog, SellerModel seller);
    void onError(String message, ProgressDialog progressDialog);
}
