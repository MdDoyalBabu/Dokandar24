package com.example.dokandar24.Seller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.dokandar24.Common.ApiCall.ProductApi;
import com.example.dokandar24.Common.LocalDb.UserDb;
import com.example.dokandar24.Common.Model.ProductModel;
import com.example.dokandar24.Common.Responses.RetrofitResponses;
import com.example.dokandar24.FileUtils;
import com.example.dokandar24.R;
import com.example.dokandar24.RealPathUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ProductUploadActivity extends AppCompatActivity {
    public static final int PICK_IMAGE=100;

    private ImageView imageView;
    private EditText titleEt,descriptionEt,priceEt;
    private Button chooseImageBtn,saveProductButton;
    private ProgressDialog progressDialog;
    ArrayList<Uri> imageList = new ArrayList();
    private  boolean isImageSelected=false;
    private UserDb userDb;
    private ProductApi productApi;

    String category="Test",subCategory="Test";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_upload);

        init();
        chooseImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open file manager for choose book all images
                if(!isImageSelected) {

                    if(ContextCompat.checkSelfPermission(ProductUploadActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(ProductUploadActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
                    }

                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    startActivityForResult(intent, PICK_IMAGE);
                }
                else{
                    Toast.makeText(ProductUploadActivity.this, "Image Already Selected", Toast.LENGTH_SHORT).show();
                }
            }
        });
        saveProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(imageList.size()>0){
                    String title=titleEt.getText().toString();
                    String description=descriptionEt.getText().toString();
                    String price=priceEt.getText().toString();
                    if(title.isEmpty()){
                        titleEt.setError("Enter Product Title.");
                        titleEt.requestFocus();
                        return;
                    }else if(description.isEmpty()){
                        descriptionEt.setError("Enter Product Description.");
                        descriptionEt.requestFocus();
                        return;
                    }else if(price.isEmpty()){
                        priceEt.setError("Enter Product Price.");
                        priceEt.requestFocus();
                        return;
                    }else{
                        uploadProduct(title,description,price);
                    }
                }else{
                    Toast.makeText(ProductUploadActivity.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                }
            }
        });




    }
    private void init(){
        productApi=new ProductApi(this);
        userDb=new UserDb(this);
        imageView=findViewById(R.id.image_id);
        titleEt=findViewById(R.id.title_Et);
        descriptionEt=findViewById(R.id.description_Et);
        priceEt=findViewById(R.id.priceEt);
        chooseImageBtn=findViewById(R.id.chose_img_btn_id);
        saveProductButton=findViewById(R.id.save_ProductButton);
        progressDialog=new ProgressDialog(this);
    }
    private void uploadProduct(String title,String description,String price){
        progressDialog.setMessage("Uploading Product...");
        ProductModel productModel=new ProductModel(Integer.parseInt(price),category,subCategory,title,description);
        ArrayList<String> filePaths = new ArrayList<>();
        for(int i=0; i<imageList.size(); i++){
            filePaths.add(RealPathUtil.getRealPath(getApplicationContext(),imageList.get(i)));
        }
       MultipartBody.Part[] surveyImagesParts = new MultipartBody.Part[imageList.size()];
            for (int index = 0; index <imageList.size(); index++){
                File file = new File(filePaths.get(index));
             RequestBody productBody = RequestBody.create(MediaType.parse("image/*"),file);
             surveyImagesParts[index] = MultipartBody.Part.createFormData("product",file.getName(), productBody);
            }
        productApi.addNewProduct(userDb.getAccessToken(),surveyImagesParts,productModel, progressDialog, new RetrofitResponses() {
            @Override
            public void onSuccess(String message, ProgressDialog progressDialog) {
                clearAll();
                progressDialog.dismiss();
                Toast.makeText(ProductUploadActivity.this, ""+message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String message, ProgressDialog progressDialog) {
                progressDialog.dismiss();
                Toast.makeText(ProductUploadActivity.this, ""+message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clearAll() {
        imageList.clear();
        isImageSelected=false;
        priceEt.setText("");
        descriptionEt.setText("");
        titleEt.setText("");
        chooseImageBtn.setText("Choose Image");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE) {
            if (resultCode == RESULT_OK) {
                if (data.getClipData() != null) {
                    int countClipData = data.getClipData().getItemCount();
                    int currentImageSlect = 0;
                    imageList.clear();
                    while (currentImageSlect < countClipData) {
                        Uri imageUri = data.getClipData().getItemAt(currentImageSlect).getUri();
                        imageList.add(imageUri);
                        currentImageSlect = currentImageSlect + 1;
                    }
                    imageView.setImageURI(Uri.parse(imageList.get(0).toString()));
                    isImageSelected = true;
                    chooseImageBtn.setText(imageList.size() + " Image Selected.");

                } else {
                    imageList.add(data.getData());
                    imageView.setImageURI(data.getData());
                    isImageSelected = true;
                    chooseImageBtn.setText(1 + " Image Selected.");
                }
            }
        }
    }
}
