package com.example.dokandar24.Seller;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.dokandar24.Common.ApiCall.UserApi;
import com.example.dokandar24.Common.LocalDb.UserDb;
import com.example.dokandar24.Common.Model.SellerModel;
import com.example.dokandar24.Common.Model.ShopModel;
import com.example.dokandar24.Common.Responses.RetrofitSellerResponses;
import com.example.dokandar24.Common.Responses.RetrofitShopResponse;
import com.example.dokandar24.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;

public class SellerShopActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView nameTv,shopNameTv,phoneTv,joiningDateTv,shopBalanceTv,sellBalanceTv,cashBalanceTv;
    private Button shopCodeBtn,sendMoneyButton,addProductButton,allProductButton;
    private CircleImageView profileImageView;

    UserApi userApi;
    UserDb userDb;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_shop);
        init();


        sendMoneyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SellerShopActivity.this,SendMoneyActivity.class));
            }
        });

        addProductButton.setOnClickListener(this);
        allProductButton.setOnClickListener(this);


    }
    private void init() {
        userApi=new UserApi(this);
        userDb=new UserDb(this);
        progressDialog=new ProgressDialog(this);

        addProductButton=findViewById(R.id.addProduct);
        nameTv=findViewById(R.id.nameTv);
        phoneTv=findViewById(R.id.phoneTv);
        joiningDateTv=findViewById(R.id.joindateTv);
        shopBalanceTv=findViewById(R.id.shopBalanceTv);
        sellBalanceTv=findViewById(R.id.sellBalanceTv);
        shopNameTv=findViewById(R.id.shopNameTv);
        cashBalanceTv=findViewById(R.id.cashBalanceTv);
        shopCodeBtn=findViewById(R.id.shopCodeBtn);
        profileImageView=findViewById(R.id.profileImageView);
        sendMoneyButton=findViewById(R.id.sendMoneyButton);
        allProductButton=findViewById(R.id.allProduct);
    }

    @Override
    protected void onStart() {
        super.onStart();

        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);

        String token=userDb.getAccessToken();
        userApi.getCurrentUser(token, progressDialog, new RetrofitSellerResponses() {
            @Override
            public void onSuccess(String message, ProgressDialog progressDialog, SellerModel seller) {
                progressDialog.dismiss();
                nameTv.setText("Name: "+seller.getName());
                phoneTv.setText("Phone: "+seller.getPhone());
                joiningDateTv.setText("Joining Date: "+seller.getJoiningDate());
                cashBalanceTv.setText(""+seller.getCashBalance()+" tk");
                shopCodeBtn.setText("Shope Code: "+seller.getMyRefer());
                if(!seller.getProfileImage().equals("none"))
                    Picasso.get().load(seller.getProfileImage()).placeholder(R.drawable.profile).into(profileImageView);

                userApi.getShop(token, progressDialog, new RetrofitShopResponse() {
                    @Override
                    public void onSuccess(String message, ProgressDialog progressDialog, ShopModel shop) {
                        progressDialog.dismiss();
                        shopBalanceTv.setText(""+shop.getShopBalance()+" tk");
                        sellBalanceTv.setText(""+shop.getSellBalance()+" tk");
                        shopNameTv.setText("Shop Name: "+shop.getShopName());
                    }

                    @Override
                    public void onError(String message, ProgressDialog progressDialog) {
                        progressDialog.dismiss();
                    }
                });



            }

            @Override
            public void onError(String message, ProgressDialog progressDialog) {
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.addProduct){
            startActivity(new Intent(SellerShopActivity.this,ProductUploadActivity.class));
        }else  if(v.getId()==R.id.allProduct){
            startActivity(new Intent(SellerShopActivity.this,ShopAllProductActivity.class));
        }
    }
}