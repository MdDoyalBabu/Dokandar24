package com.example.dokandar24.Common.Interfaces;

import android.app.ProgressDialog;

import com.example.dokandar24.Common.Model.ProductModel;
import com.example.dokandar24.Common.Responses.ProductListResponse;
import com.example.dokandar24.Common.Responses.RetrofitCashInListResponse;
import com.example.dokandar24.Common.Responses.RetrofitResponses;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ProductInterface {

 void addNewProduct(String token,MultipartBody.Part[] productImages,
                    ProductModel produtData,
                    ProgressDialog progressDialog,
                    RetrofitResponses retrofitResponses
                    );

 void getMYProducts(String token, ProgressDialog progressDialog, ProductListResponse retrofitResponse);


}
