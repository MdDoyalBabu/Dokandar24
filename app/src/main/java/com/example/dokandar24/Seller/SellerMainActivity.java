package com.example.dokandar24.Seller;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dokandar24.Common.ApiCall.UserApi;
import com.example.dokandar24.Common.LocalDb.UserDb;
import com.example.dokandar24.Common.Model.SellerModel;
import com.example.dokandar24.Common.Responses.RetrofitResponses;
import com.example.dokandar24.Common.Responses.RetrofitSellerResponses;
import com.example.dokandar24.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class SellerMainActivity extends AppCompatActivity {

    private TextView phoneTv,nameTv,joiningDateTv;
    private TextView cashBalanceTv;
    private Button createShopButton;


    UserApi userApi;
    UserDb userDb;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_main);
        init();

        createShopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCreateShopDialogue();
            }
        });


    }
    private void init() {

        nameTv=findViewById(R.id.nameTv);
        phoneTv=findViewById(R.id.phoneTv);
        joiningDateTv=findViewById(R.id.joindateTv);
        cashBalanceTv=findViewById(R.id.cashBalanceTv);
        createShopButton=findViewById(R.id.createShopButton);



        userApi=new UserApi(this);
        userDb=new UserDb(this);
        progressDialog=new ProgressDialog(this);
    }
    @Override
    protected void onStart() {
        super.onStart();

        progressDialog.setMessage("Loading...");
        String token=userDb.getAccessToken();
        userApi.getCurrentUser(token, progressDialog, new RetrofitSellerResponses() {
            @Override
            public void onSuccess(String message, ProgressDialog progressDialog, SellerModel seller) {
                progressDialog.dismiss();
                if(seller.getAccountStatus().equals("inactive")){
                    createShopButton.setVisibility(View.VISIBLE);
                }else{
                    createShopButton.setVisibility(View.GONE);
                }
                nameTv.setText("Name: "+seller.getName());
                phoneTv.setText("Phone: "+seller.getPhone());
                joiningDateTv.setText("Joining Date: "+seller.getJoiningDate());
                cashBalanceTv.setText(""+seller.getCashBalance()+" tk");




            }

            @Override
            public void onError(String message, ProgressDialog progressDialog) {
                progressDialog.dismiss();
                userDb.setUserData("","");
                startActivity(new Intent(SellerMainActivity.this,SellerLoginActivity.class));
                finish();
            }
        });
    }
    private void showCreateShopDialogue(){
        progressDialog.setMessage("Creating New Shop..");
            AlertDialog.Builder builder=new AlertDialog.Builder(SellerMainActivity.this);
            View view=getLayoutInflater().inflate(R.layout.create_shop_popup,null);
            builder.setView(view);

            EditText shopNameEt=view.findViewById(R.id.shopNameEt);
            Button createShopButton=view.findViewById(R.id.create_Shop_Button);
            final AlertDialog dialog=builder.create();
            dialog.show();

            createShopButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String shopName=shopNameEt.getText().toString();
                    if(shopName.isEmpty()){
                        shopNameEt.setError("Enter Shop Name");
                        shopNameEt.requestFocus();
                    }else{
                        createAnOnlineShop(shopName,dialog);
                      }
                }
            });

    }

    private void createAnOnlineShop(String shopName,AlertDialog dialog) {

        String token=userDb.getAccessToken();
        Calendar calForDate=Calendar.getInstance();
        SimpleDateFormat currentDate=new SimpleDateFormat("dd-MMMM-yyy");
        String saveCurrentDate=currentDate.format(calForDate.getTime());

        Calendar callForTime=Calendar.getInstance();
        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm");
        String saveCurrentTime=currentTime.format(callForTime.getTime());
        String joiningDate=saveCurrentDate+" at "+saveCurrentTime;

        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("shopName",shopName);
        hashMap.put("currentTime",joiningDate);

        userApi.createSellerShop(hashMap, token, progressDialog, new RetrofitResponses() {
            @Override
            public void onSuccess(String message, ProgressDialog progressDialog) {
                dialog.dismiss();
                progressDialog.dismiss();
                Toast.makeText(SellerMainActivity.this, ""+message, Toast.LENGTH_SHORT).show();
                onStart();
            }

            @Override
            public void onError(String message, ProgressDialog progressDialog) {
                dialog.dismiss();
                progressDialog.dismiss();
                Toast.makeText(SellerMainActivity.this, ""+message, Toast.LENGTH_LONG).show();
            }
        });





    }


}