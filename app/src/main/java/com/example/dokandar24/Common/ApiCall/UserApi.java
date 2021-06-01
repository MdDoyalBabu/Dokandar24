package com.example.dokandar24.Common.ApiCall;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.core.graphics.drawable.IconCompat;

import com.example.dokandar24.BaseUrl;
import com.example.dokandar24.Common.Api.UserRetrofitApi;
import com.example.dokandar24.Common.Interfaces.UserInterface;
import com.example.dokandar24.Common.LocalDb.UserDb;
import com.example.dokandar24.Common.Model.SellerModel;
import com.example.dokandar24.Common.Responses.CustomResponse;
import com.example.dokandar24.Common.Responses.RetrofitResponses;
import com.example.dokandar24.Common.Responses.RetrofitResponses2;
import com.example.dokandar24.Common.Responses.RetrofitSellerResponses;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserApi implements UserInterface{

    UserRetrofitApi userRetrofitApi;
    UserDb userDb;
    private Activity context;
    public UserApi(Activity context){
        this.context=context;

        userDb=new UserDb(context);

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(BaseUrl.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userRetrofitApi=retrofit.create(UserRetrofitApi.class);

    }
    @Override
    public void registerSeller(SellerModel seller, ProgressBar progressDialog, RetrofitResponses2 retrofitResponses) {
        progressDialog.setVisibility(View.VISIBLE);
        Call<CustomResponse> call=userRetrofitApi.createSeller(seller);
        call.enqueue(new Callback<CustomResponse>() {
            @Override
            public void onResponse(Call<CustomResponse> call, Response<CustomResponse> response) {
                CustomResponse customResponse=response.body();
                if(!response.isSuccessful()){
                    String message=customResponse==null?"Seller Register Failed.":customResponse.getMessage();
                    retrofitResponses.onError(message,progressDialog);
                }else{
                    String message=customResponse==null?"Register Successful.":customResponse.getMessage();
                    retrofitResponses.onSuccess(message,progressDialog);
                }
            }

            @Override
            public void onFailure(Call<CustomResponse> call, Throwable t) {
                retrofitResponses.onError("Seller Register Failed.",progressDialog);

            }
        });
    }
    @Override
    public void loginSeller(Map<String, Object> sellerMap, ProgressBar progressDialog, RetrofitResponses2 retrofitResponses) {
        progressDialog.setVisibility(View.VISIBLE);
        Call<CustomResponse> call=userRetrofitApi.loginSeller(sellerMap);
        call.enqueue(new Callback<CustomResponse>() {
            @Override
            public void onResponse(Call<CustomResponse> call, Response<CustomResponse> response) {
                CustomResponse customResponse=response.body();
               if(!response.isSuccessful()){
                   String message=customResponse==null?"Login Failed.":customResponse.getMessage();
                   retrofitResponses.onError(message,progressDialog);
                }else{
                   try{
                       userDb.setUserData("Bearer "+customResponse.getToken(),"seller");
                       String message=customResponse==null?"Login Successful.":customResponse.getMessage();
                       retrofitResponses.onSuccess(message,progressDialog);

                   }catch (Exception e){
                       Toast.makeText(context, ""+e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                   }
                }
            }
            @Override
            public void onFailure(Call<CustomResponse> call, Throwable t) {
                retrofitResponses.onError("Login Failed",progressDialog);

            }
        });
    }

    @Override
    public void getCurrentUser(String token, ProgressDialog progressDialog, RetrofitSellerResponses retrofitResponses) {
       progressDialog.show();
       Call<SellerModel> call=userRetrofitApi.getCurrentSeller(token);
      call.enqueue(new Callback<SellerModel>() {
          @Override
          public void onResponse(Call<SellerModel> call, Response<SellerModel> response) {
              SellerModel seller=response.body();
              if(!response.isSuccessful()){
                  retrofitResponses.onError("User Not Found",progressDialog);
              }else{
                  try{
                      if(seller!=null){
                          retrofitResponses.onSuccess("Success",progressDialog,seller);
                      }else{
                          retrofitResponses.onError("User Not Found",progressDialog);
                      }
                    }catch (Exception e){
                      Toast.makeText(context, ""+e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                  }
              }
          }

          @Override
          public void onFailure(Call<SellerModel> call, Throwable t) {
              retrofitResponses.onError("User Not Found",progressDialog);
          }
      });



    }
}
