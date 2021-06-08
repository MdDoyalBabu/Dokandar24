package com.example.dokandar24.Common.ApiCall;

import android.app.Activity;
import android.app.ProgressDialog;
import android.widget.Toast;

import com.example.dokandar24.BaseUrl;
import com.example.dokandar24.Common.Api.ProductRetrofitApi;
import com.example.dokandar24.Common.Api.UserRetrofitApi;
import com.example.dokandar24.Common.Interfaces.ProductInterface;
import com.example.dokandar24.Common.LocalDb.UserDb;
import com.example.dokandar24.Common.Model.CashInListModel;
import com.example.dokandar24.Common.Model.ProductListModel;
import com.example.dokandar24.Common.Model.ProductModel;
import com.example.dokandar24.Common.Model.SellerModel;
import com.example.dokandar24.Common.Responses.CustomResponse;
import com.example.dokandar24.Common.Responses.ProductListResponse;
import com.example.dokandar24.Common.Responses.RetrofitResponses;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class ProductApi implements ProductInterface{
    ProductRetrofitApi productRetrofitApi;
    UserDb userDb;
    private Activity context;
    public ProductApi(Activity context){
        this.context=context;
        userDb=new UserDb(context);
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(BaseUrl.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        productRetrofitApi=retrofit.create(ProductRetrofitApi.class);
    }

    @Override
    public void addNewProduct(String token, MultipartBody.Part[] productImages, ProductModel productData, ProgressDialog progressDialog, RetrofitResponses retrofitResponses) {
        progressDialog.show();
        Call<CustomResponse>call= productRetrofitApi.uploadProductImage(token,productImages,productData.getProductPrice(),productData.getDescription(),productData.getTitle(),productData.getCategory(),productData.getSubCategory());
        call.enqueue(new Callback<CustomResponse>() {
            @Override
            public void onResponse(Call<CustomResponse> call, Response<CustomResponse> response) {
                CustomResponse customResponse=response.body();
               if(!response.isSuccessful()){
                   String message=customResponse==null?"Product Uploaded Failed.":customResponse.getMessage();
                   retrofitResponses.onError(message,progressDialog);
               }else{
                   if(response.code()==201){
                       String message=customResponse==null?"Product Uploaded Successful.":customResponse.getMessage();
                       retrofitResponses.onSuccess(message,progressDialog);
                   }else{
                       String message=customResponse==null?"Product Uploaded Failed.":customResponse.getMessage();
                       retrofitResponses.onError(message,progressDialog);
                   }
               }
             }
            @Override
            public void onFailure(Call<CustomResponse> call, Throwable t) {
                retrofitResponses.onError("Error",progressDialog);
            }
        });


    }
    @Override
    public void getMYProducts(String token, ProgressDialog progressDialog, ProductListResponse retrofitResponse) {
        progressDialog.show();
        Call<ProductListModel> call=productRetrofitApi.getMyAllProduct(token);
        call.enqueue(new Callback<ProductListModel>() {
            @Override
            public void onResponse(Call<ProductListModel> call, Response<ProductListModel> response) {

                ProductListModel productListModel=response.body();
                if(!response.isSuccessful()){
                    retrofitResponse.onError("Error..",progressDialog);
                }else{
                    try{
                        if(productListModel.getProducts()!=null && productListModel.getProducts().size()>0){
                            retrofitResponse.onSuccess("Success",progressDialog,productListModel);
                        }else{
                            retrofitResponse.onError("No Products In Found",progressDialog);
                        }
                    }catch (Exception e){
                        Toast.makeText(context, ""+e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ProductListModel> call, Throwable t) {
                retrofitResponse.onError("Error..",progressDialog);
            }
        });
    }
}
