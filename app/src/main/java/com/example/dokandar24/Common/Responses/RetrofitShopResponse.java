package com.example.dokandar24.Common.Responses;

import android.app.ProgressDialog;

import com.example.dokandar24.Common.Model.SellerModel;
import com.example.dokandar24.Common.Model.ShopModel;

public interface RetrofitShopResponse {
    void onSuccess(String message, ProgressDialog progressDialog, ShopModel shop);
    void onError(String message, ProgressDialog progressDialog);
}
