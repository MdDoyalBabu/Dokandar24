package com.example.dokandar24.Seller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telecom.Call;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dokandar24.Common.ApiCall.UserApi;
import com.example.dokandar24.Common.Responses.CustomResponse;
import com.example.dokandar24.Common.Responses.RetrofitResponses2;
import com.example.dokandar24.R;

import java.util.HashMap;
import java.util.Map;

public class SellerLoginActivity extends AppCompatActivity {

    private TextView registerLink_Tv;
    private EditText phoneEt,passwordEt;
    private Button loginButton;
    private ProgressBar progressBar;
    private UserApi userApi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_login);
        init();

        registerLink_Tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SellerLoginActivity.this,SellerRegistrationActivity.class));
                finish();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone=phoneEt.getText().toString().trim();
                String password=passwordEt.getText().toString().trim();
                if(phone.isEmpty()){
                    showError(phoneEt,"Please enter Your Phone Number with country Code.");
                }else if(password.isEmpty()){
                    showError(passwordEt,"Please Enter Password");
                }else{
                   loginSeller(phone,password);
                }
            }
        });
    }
    private void loginSeller(String phone, String password) {
        Map<String,Object> hashMap=new HashMap<>();
        hashMap.put("phone",phone);
        hashMap.put("password",password);

     userApi.loginSeller(hashMap, progressBar, new RetrofitResponses2() {
         @Override
         public void onSuccess(String message, ProgressBar progressDialog) {
             sendUserToUserMainActivity();
             progressDialog.setVisibility(View.GONE);
             Toast.makeText(SellerLoginActivity.this, ""+message, Toast.LENGTH_SHORT).show();
         }

         @Override
         public void onError(String message, ProgressBar progressDialog) {
            progressBar.setVisibility(View.GONE);
             Toast.makeText(SellerLoginActivity.this, ""+message, Toast.LENGTH_SHORT).show();
         }
     });




    }


    private void init() {
        userApi=new UserApi(this);
        progressBar=findViewById(R.id.progressBar);
        registerLink_Tv=findViewById(R.id.registration_link);
        phoneEt=findViewById(R.id.phoneEt);
        passwordEt=findViewById(R.id.passwordEt);
        loginButton=findViewById(R.id.loginButtonEt);
    }
    private void showError(EditText editText, String message){
        editText.setError(message);
        editText.requestFocus();
    }

    private void sendUserToUserMainActivity() {
        startActivity(new Intent(SellerLoginActivity.this,SellerMainActivity.class));
        finish();
    }
}