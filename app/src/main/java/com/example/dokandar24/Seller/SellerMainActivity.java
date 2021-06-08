package com.example.dokandar24.Seller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dokandar24.Common.ApiCall.UserApi;
import com.example.dokandar24.Common.LocalDb.UserDb;
import com.example.dokandar24.Common.Model.SellerModel;
import com.example.dokandar24.Common.Responses.RetrofitResponses;
import com.example.dokandar24.Common.Responses.RetrofitSellerResponses;
import com.example.dokandar24.R;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SellerMainActivity extends AppCompatActivity {

    private TextView phoneTv,nameTv,joiningDateTv;
    private TextView cashBalanceTv;
    private Button createShopButton,myShopButton,sendBalanceButton;
    private Button addBalance;
    private Toolbar toolbar;
    private CircleImageView profileImageView;

    UserApi userApi;
    UserDb userDb;
    ProgressDialog progressDialog;
    Bitmap mBitmap;
    private Uri imageUri;
    private Button uploadButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_main);
        init();

        createShopButton.setOnClickListener(v -> showCreateShopDialogue());
        myShopButton.setOnClickListener(v -> startActivity(new Intent(SellerMainActivity.this,SellerShopActivity.class)));
        addBalance.setOnClickListener(v -> startActivity(new Intent(SellerMainActivity.this,CashinActivity.class)));
        sendBalanceButton.setOnClickListener(v -> startActivity(new Intent(SellerMainActivity.this,SendMoneyActivity.class)));
        profileImageView.setOnClickListener(v -> {
            openfilechooser();
        });
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mBitmap!=null){
                    uploadProfileImage();
                }else{
                    Toast.makeText(SellerMainActivity.this, "Select An Image", Toast.LENGTH_SHORT).show();
                }

            }
        });



    }
    private void openfilechooser() {
        Intent intentf=new Intent();
        intentf.setType("image/*");
        intentf.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intentf,1);
    }
    private void init() {
        toolbar=findViewById(R.id.appBarId);
        setSupportActionBar(toolbar);
        this.setTitle("Profile");


        nameTv=findViewById(R.id.nameTv);
        uploadButton=findViewById(R.id.uploadButtonid);
        phoneTv=findViewById(R.id.phoneTv);
        joiningDateTv=findViewById(R.id.joindateTv);
        cashBalanceTv=findViewById(R.id.cashBalanceTv);
        createShopButton=findViewById(R.id.createShopButton);
        myShopButton=findViewById(R.id.myShopButton);
        addBalance=findViewById(R.id.addBalanceButton);
        sendBalanceButton=findViewById(R.id.sendBalanceButton);
        profileImageView=findViewById(R.id.profileImageView);

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
                    myShopButton.setVisibility(View.GONE);
                }else{
                    myShopButton.setVisibility(View.VISIBLE);
                    createShopButton.setVisibility(View.GONE);
                }
                nameTv.setText("Name: "+seller.getName());
                phoneTv.setText("Phone: "+seller.getPhone());
                joiningDateTv.setText("Joining Date: "+seller.getJoiningDate());
                cashBalanceTv.setText(""+seller.getCashBalance()+" tk");
                if(!seller.getProfileImage().equals("none"))
                    Picasso.get().load(seller.getProfileImage()).placeholder(R.drawable.profile).into(profileImageView);

            }

            @Override
            public void onError(String message, ProgressDialog progressDialog) {
                progressDialog.dismiss();
                /*userDb.setUserData("","");
                startActivity(new Intent(SellerMainActivity.this,SellerLoginActivity.class));
                finish();*/
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.logout_menu){
            logout();
        }
        return super.onOptionsItemSelected(item);
    }
    private void logout() {
        UserDb userDb=new UserDb(this);
        userDb.setUserData("","");
        startActivity(new Intent(SellerMainActivity.this, SellerLoginActivity.class));
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {
                imageUri=data.getData();
                 if (imageUri != null) {
                    try {
                        mBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
                        profileImageView.setImageBitmap(mBitmap);
                        uploadButton.setVisibility(View.VISIBLE);
                   } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

        }

    }

    private void uploadProfileImage() {
        try {

            progressDialog.setMessage("Uploading..");
            progressDialog.setTitle("Pleae wait..");
            progressDialog.show();
            String token=userDb.getAccessToken();

            File filesDir = getApplicationContext().getFilesDir();
            File file = new File(filesDir, "image" +".jpg");

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            mBitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
            byte[] bitmapdata = bos.toByteArray();


            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();


            RequestBody reqFile = RequestBody.create(MediaType.parse("image/jpg"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("profile", file.getName(), reqFile);
            RequestBody name = RequestBody.create(MediaType.parse("text/plain"), "profile");
            userApi.updateProfileImage(body, name, token, progressDialog, new RetrofitResponses() {
                @Override
                public void onSuccess(String message, ProgressDialog progressDialog) {
                    onStart();
                    uploadButton.setVisibility(View.GONE);
                    progressDialog.dismiss();
                    Toast.makeText(SellerMainActivity.this, ""+message, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(String message, ProgressDialog progressDialog) {
                    progressDialog.dismiss();
                    Toast.makeText(SellerMainActivity.this, ""+message, Toast.LENGTH_SHORT).show();

                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}