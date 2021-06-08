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
import com.example.dokandar24.Common.Model.CashInListModel;
import com.example.dokandar24.Common.Model.SellerListModel;
import com.example.dokandar24.Common.Model.SellerModel;
import com.example.dokandar24.Common.Model.SendMoneyListModel;
import com.example.dokandar24.Common.Model.ShopModel;
import com.example.dokandar24.Common.Responses.CustomResponse;
import com.example.dokandar24.Common.Responses.RetrofitCashInListResponse;
import com.example.dokandar24.Common.Responses.RetrofitResponses;
import com.example.dokandar24.Common.Responses.RetrofitResponses2;
import com.example.dokandar24.Common.Responses.RetrofitSellerResponses;
import com.example.dokandar24.Common.Responses.RetrofitSendMoneyListResponse;
import com.example.dokandar24.Common.Responses.RetrofitShopResponse;

import java.util.HashMap;
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
                    if(response.code()==200){
                        String message=customResponse==null?"Register Successful.":customResponse.getMessage();
                        retrofitResponses.onSuccess(message,progressDialog);
                    }else{
                        String message=customResponse==null?"Seller Register Failed.":customResponse.getMessage();
                        retrofitResponses.onError(message,progressDialog);

                    }
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
    public void updateProfileImage(MultipartBody.Part image, RequestBody name,String token, ProgressDialog progressDialog, RetrofitResponses retrofitResponses) {
        progressDialog.show();
        Call<ResponseBody> call = userRetrofitApi.updateProfileImage(token,image, name);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(!response.isSuccessful()){
                    retrofitResponses.onError("Profile Image Upload Failed.",progressDialog);
                }else{
                    if(response.code()==201){
                        retrofitResponses.onSuccess("Profile Image Uploaded Successful",progressDialog);
                    }else{
                        retrofitResponses.onError("Profile Image Upload Failed.",progressDialog);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                retrofitResponses.onError("Profile Image Upload Failed.",progressDialog);
            }
        });



    }

    @Override
    public void sendCashInRequest(Map<String, Object> cash, String token, ProgressDialog progressDialog, RetrofitResponses retrofitResponses) {
        progressDialog.show();
        Call<CustomResponse> call=userRetrofitApi.sendCashInRequest(token,cash);
        call.enqueue(new Callback<CustomResponse>() {
            @Override
            public void onResponse(Call<CustomResponse> call, Response<CustomResponse> response) {
                CustomResponse customResponse=response.body();
                if(!response.isSuccessful()){
                    String message=customResponse==null?"Cash in Request Send Failed.":customResponse.getMessage();
                    retrofitResponses.onError(message,progressDialog);
                }else{
                    String message=customResponse==null?"Cash In Request Sent Successful.":customResponse.getMessage();
                    retrofitResponses.onSuccess(message,progressDialog);
                }
            }

            @Override
            public void onFailure(Call<CustomResponse> call, Throwable t) {
                retrofitResponses.onError("Cash in Request Send Failed.",progressDialog);

            }
        });
    }

    @Override
    public void sendMoney(Map<String, Object> cash, String token, ProgressDialog progressDialog, RetrofitResponses retrofitResponses) {
        progressDialog.show();
        Call<CustomResponse> call=userRetrofitApi.sendMoney(token,cash);
        call.enqueue(new Callback<CustomResponse>() {
            @Override
            public void onResponse(Call<CustomResponse> call, Response<CustomResponse> response) {
                CustomResponse customResponse=response.body();
                if(!response.isSuccessful()){
                    String message=customResponse==null?"Send Money Failed.":customResponse.getMessage();
                    retrofitResponses.onError(message,progressDialog);
                }else{
                    String message=customResponse==null?"Successfully Sent.":customResponse.getMessage();
                    retrofitResponses.onSuccess(message,progressDialog);
                }
            }

            @Override
            public void onFailure(Call<CustomResponse> call, Throwable t) {
                retrofitResponses.onError("Send Money Failed.",progressDialog);

            }
        });
    }

    @Override
    public void getCurrentUser(String token, ProgressDialog progressDialog, RetrofitSellerResponses retrofitResponses) {
       progressDialog.show();
       Call<SellerListModel> call=userRetrofitApi.getCurrentSeller(token);
       call.enqueue(new Callback<SellerListModel>() {
           @Override
           public void onResponse(Call<SellerListModel> call, Response<SellerListModel> response) {
               SellerListModel sellerModelList=response.body();
               if(!response.isSuccessful()){
                   retrofitResponses.onError("User Not Found",progressDialog);
               }else{
                   try{
                       if(sellerModelList.getSeller()!=null && sellerModelList.getSeller().size()>0){
                           SellerModel seller=sellerModelList.getSeller().get(0);
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
           public void onFailure(Call<SellerListModel> call, Throwable t) {
               retrofitResponses.onError("User Not Found",progressDialog);
           }
       });
    }

    @Override
    public void getCashIn(String token, String cashInType, ProgressDialog progressDialog, RetrofitCashInListResponse retrofitResponse) {
          progressDialog.show();
        Call<CashInListModel> call=userRetrofitApi.getCashIn(token,cashInType);
        call.enqueue(new Callback<CashInListModel>() {
            @Override
            public void onResponse(Call<CashInListModel> call, Response<CashInListModel> response) {

                CashInListModel cashInList=response.body();
                if(!response.isSuccessful()){
                    retrofitResponse.onError("Error..",progressDialog);
                }else{
                    try{
                        if(cashInList.getCashIn()!=null && cashInList.getCashIn().size()>0){
                            retrofitResponse.onSuccess("Success",progressDialog,cashInList.getCashIn());
                         }else{
                            retrofitResponse.onError("No Cash In Found",progressDialog);
                        }
                    }catch (Exception e){
                        Toast.makeText(context, ""+e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<CashInListModel> call, Throwable t) {
                retrofitResponse.onError("Error..",progressDialog);
            }
        });



    }

    @Override
    public void getSendMoneyHistory(String token, ProgressDialog progressDialog, RetrofitSendMoneyListResponse retrofitResponse) {
        progressDialog.show();
        Call<SendMoneyListModel> call=userRetrofitApi.getSendMoney(token);
        call.enqueue(new Callback<SendMoneyListModel>() {
            @Override
            public void onResponse(Call<SendMoneyListModel> call, Response<SendMoneyListModel> response) {

                SendMoneyListModel sendMoneyListModel=response.body();
                if(!response.isSuccessful()){
                    retrofitResponse.onError("Error..",progressDialog);
                }else{
                    try{
                        if(sendMoneyListModel.getSendMoney()!=null && sendMoneyListModel.getSendMoney().size()>0){
                            retrofitResponse.onSuccess("Success",progressDialog,sendMoneyListModel.getSendMoney());
                        }else{
                            retrofitResponse.onError("No SendMoney In Found",progressDialog);
                        }
                    }catch (Exception e){
                        Toast.makeText(context, ""+e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<SendMoneyListModel> call, Throwable t) {
                retrofitResponse.onError("Error..",progressDialog);
            }
        });
    }
    @Override
    public void createSellerShop(Map<String, Object> shopMap,String token, ProgressDialog progressDialog, RetrofitResponses retrofitResponses) {
        progressDialog.show();
        Call<CustomResponse> call=userRetrofitApi.createShop(token,shopMap);

        call.enqueue(new Callback<CustomResponse>() {
            @Override
            public void onResponse(Call<CustomResponse> call, Response<CustomResponse> response) {
                CustomResponse customResponse=response.body();
                if(!response.isSuccessful()){
                    String message=customResponse==null?"Shop Create Failed.":customResponse.getMessage();
                    retrofitResponses.onError(message,progressDialog);
                }else{
                    String message=customResponse==null?"Shop Create Successful.":customResponse.getMessage();
                    retrofitResponses.onSuccess(message,progressDialog);
                }
            }
            @Override
            public void onFailure(Call<CustomResponse> call, Throwable t) {
                retrofitResponses.onError("Shop Create Failed.",progressDialog);
            }
        });
    }

    @Override
    public void getShop(String token, ProgressDialog progressDialog, RetrofitShopResponse retrofitResponses) {
        progressDialog.show();
        Call<ShopModel> call=userRetrofitApi.getCurrentSellerShop(token);
        call.enqueue(new Callback<ShopModel>() {
            @Override
            public void onResponse(Call<ShopModel> call, Response<ShopModel> response) {
               if(!response.isSuccessful()){
                    retrofitResponses.onError("Error.",progressDialog);
                }else{
                   ShopModel shop=response.body();
                   if(shop!=null){
                       retrofitResponses.onSuccess("Success.",progressDialog,shop);
                   }else{
                       retrofitResponses.onError("Error.",progressDialog);
                   }
                }
            }

            @Override
            public void onFailure(Call<ShopModel> call, Throwable t) {
                retrofitResponses.onError("Error.",progressDialog);
            }
        });

    }
}
