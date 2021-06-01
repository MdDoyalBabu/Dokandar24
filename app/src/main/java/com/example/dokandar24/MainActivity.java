package com.example.dokandar24;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.dokandar24.Common.LocalDb.UserDb;
import com.example.dokandar24.Customer.CustomerMainActivity;
import com.example.dokandar24.Customer.CustomerRegistrationActivity;
import com.example.dokandar24.Seller.SellerMainActivity;
import com.example.dokandar24.Seller.SellerRegistrationActivity;

public class MainActivity extends AppCompatActivity {

    private Button sellerButton,customerButton;
    private UserDb userDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sellerButton=findViewById(R.id.sellerButton);
        customerButton=findViewById(R.id.customerButton);


        sellerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SellerRegistrationActivity.class));
            }
        });
        customerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CustomerRegistrationActivity.class));
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        userDb=new UserDb(this);
        String token=userDb.getAccessToken();
        String userType=userDb.getUserType();

        if(!token.equals("") && userType.equals("seller")){
            sendSellerToMainActivity();
        }
        if(!token.equals("") && userType.equals("customer")){
            sendUserMainActivity();
        }



    }

    public void sendSellerToMainActivity(){
        Intent intent=new Intent(MainActivity.this, SellerMainActivity.class);
        startActivity(intent);
        finish();
    } public void sendUserMainActivity(){
        Intent intent=new Intent(MainActivity.this, CustomerMainActivity.class);
        startActivity(intent);
        finish();
    }
}