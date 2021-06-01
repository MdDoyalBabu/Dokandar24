package com.example.dokandar24.Seller;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.example.dokandar24.Common.ApiCall.UserApi;
import com.example.dokandar24.Common.LocalDb.UserDb;
import com.example.dokandar24.Common.Model.SellerModel;
import com.example.dokandar24.Common.Responses.RetrofitSellerResponses;
import com.example.dokandar24.R;

public class SellerShopActivity extends AppCompatActivity {

    UserApi userApi;
    UserDb userDb;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_shop);
        init();
    }
    private void init() {
        userApi=new UserApi(this);
        userDb=new UserDb(this);
        progressDialog=new ProgressDialog(this);
    }
}