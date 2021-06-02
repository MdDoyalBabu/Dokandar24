package com.example.dokandar24.Common.Api;


import com.example.dokandar24.Common.Model.CashInListModel;
import com.example.dokandar24.Common.Model.SellerListModel;
import com.example.dokandar24.Common.Model.SellerModel;
import com.example.dokandar24.Common.Model.SendMoneyListModel;
import com.example.dokandar24.Common.Model.ShopModel;
import com.example.dokandar24.Common.Responses.CustomResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserRetrofitApi {
    @POST("seller/register")
    Call<CustomResponse> createSeller(@Body SellerModel seller);
    @POST("seller/login")
    Call<CustomResponse> loginSeller(@Body Map<String,Object> seller);

    @POST("seller/active")
    Call<CustomResponse> createShop(@Header("Authorization") String token,@Body Map<String,Object> shop);
    @POST("seller/cash/in")
    Call<CustomResponse> sendCashInRequest(@Header("Authorization") String token,@Body Map<String,Object> cash);

   @POST("seller/cash/send")
    Call<CustomResponse> sendMoney(@Header("Authorization") String token,@Body Map<String,Object> cash);

   @GET("seller/cash/in/{type}")
    Call<CashInListModel> getCashIn(@Header("Authorization") String token, @Path("type") String cashInType);
  @GET("seller/cash/send")
    Call<SendMoneyListModel> getSendMoney(@Header("Authorization") String token);

    @GET("seller/")
    Call<SellerListModel> getCurrentSeller(@Header("Authorization") String token);
    @GET("seller/shop")
    Call<ShopModel> getCurrentSellerShop(@Header("Authorization") String token);

}
