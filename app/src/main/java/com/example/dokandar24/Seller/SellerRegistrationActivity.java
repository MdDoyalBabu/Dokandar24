package com.example.dokandar24.Seller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dokandar24.Common.ApiCall.UserApi;
import com.example.dokandar24.Common.Model.SellerModel;
import com.example.dokandar24.Common.Responses.RetrofitResponses2;
import com.example.dokandar24.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class SellerRegistrationActivity extends AppCompatActivity {

    private EditText nameEt,addressEt,referEt;
    private Spinner countrySpinner,citySpinner;
    private EditText phoneEt,passwordEt;
    private Button registerButton;
    private ProgressBar progressBar;
    private TextView loginLink;

    private ArrayAdapter countrySpinnerAdapter;
    private   ArrayAdapter citySpinnerAdapter;


    String[] countrys={"Country","Bangladesh","India","Pakistan","Afganistan"};
    String[] citys={"City","Rangpur","Dhaka","Barishal","Sylhet","Mymansing"};


    String selectedCountry="Country";
    String selectedCity="City";
    String name,phone,password,address,refer;
    private UserApi userApi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_registration);


        init();

        countrySpinnerAdapter=new ArrayAdapter(SellerRegistrationActivity.this,R.layout.item_layout,R.id.spinnerHeaderTExt,countrys);
        countrySpinner.setAdapter(countrySpinnerAdapter);

        citySpinnerAdapter=new ArrayAdapter(SellerRegistrationActivity.this,R.layout.item_layout,R.id.spinnerHeaderTExt,citys);
        citySpinner.setAdapter(citySpinnerAdapter);


        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SellerRegistrationActivity.this, SellerLoginActivity.class));
                finish();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              name =nameEt.getText().toString();
                phone=phoneEt.getText().toString().trim();
                password=passwordEt.getText().toString().trim();
                address=addressEt.getText().toString();
                selectedCountry=countrySpinner.getSelectedItem().toString();
                selectedCity=citySpinner.getSelectedItem().toString();
                refer=referEt.getText().toString().trim();



                if(name.isEmpty()){
                    showError(nameEt,"Write Your Name");
                }
                else if(selectedCountry.equals("Country")){
                    Toast.makeText(SellerRegistrationActivity.this, "Select Your Country", Toast.LENGTH_SHORT).show();
                }
                else if(selectedCity.equals("City")){
                    Toast.makeText(SellerRegistrationActivity.this, "Select Your City", Toast.LENGTH_SHORT).show();
                }
                else if(refer.isEmpty()){
                    showError(referEt,"Enter Your Refer Id.");
                }  else if(address.isEmpty()){
                    showError(addressEt,"Please Write Your Address");
                }
                else if(phone.isEmpty()){
                    showError(phoneEt,"Please enter Your Phone Number with country Code.");
                }else if(password.isEmpty()){
                    showError(passwordEt,"Please Enter Password");
                }else{
                    registerSeller();
                }
            }
        });
    }
    private void init(){
        loginLink=findViewById(R.id.login_link);
        nameEt=findViewById(R.id.nameEt);
        referEt=findViewById(R.id.referEt);
        addressEt=findViewById(R.id.addressEt);
        phoneEt=findViewById(R.id.phoneEt);
        addressEt=findViewById(R.id.addressEt);
        countrySpinner=findViewById(R.id.country_Spinner);
        citySpinner=findViewById(R.id.city_Spinner);
        passwordEt=findViewById(R.id.passwordEt);
        registerButton=findViewById(R.id.register_Button);
        progressBar=findViewById(R.id.progressBar);
        userApi=new UserApi(this);
    }
    private void showError(EditText editText, String message){
        editText.setError(message);
        editText.requestFocus();
    }
    private void registerSeller(){
        Calendar calForDate=Calendar.getInstance();
        SimpleDateFormat currentDate=new SimpleDateFormat("dd-MMMM-yyy");
        String saveCurrentDate=currentDate.format(calForDate.getTime());

        Calendar callForTime=Calendar.getInstance();
        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm");
        String saveCurrentTime=currentTime.format(callForTime.getTime());
        String joiningDate=saveCurrentDate+" At "+saveCurrentTime;

        String systemTime=String.valueOf(System.currentTimeMillis());
        String userId=systemTime.substring(systemTime.length()-7,systemTime.length());

        StringBuilder stringBuilder=new StringBuilder(userId);
        String myRefer=stringBuilder.reverse().toString()+String.valueOf(new Random().nextInt(100));



        SellerModel sellerModel=new SellerModel(name,joiningDate,address,selectedCountry,selectedCity,phone,password,refer,myRefer);
        userApi.registerSeller(sellerModel, progressBar, new RetrofitResponses2() {
            @Override
            public void onSuccess(String message, ProgressBar progressDialog) {
                startActivity(new Intent(SellerRegistrationActivity.this, SellerLoginActivity.class));
                finish();
                progressDialog.setVisibility(View.GONE);
                Toast.makeText(SellerRegistrationActivity.this, ""+message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String message, ProgressBar progressDialog) {
                progressDialog.setVisibility(View.GONE);
                Toast.makeText(SellerRegistrationActivity.this, ""+message, Toast.LENGTH_SHORT).show();

            }
        });
    }
}