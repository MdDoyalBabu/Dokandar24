package com.example.dokandar24.Common.Api;


import com.example.dokandar24.Common.Model.SellerModel;
import com.example.dokandar24.Common.Responses.CustomResponse;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserRetrofitApi {
    @POST("seller/register")
    Call<CustomResponse> createSeller(@Body SellerModel seller);
    @POST("seller/login")
    Call<CustomResponse> loginSeller(@Body Map<String,Object> seller);
    @GET("seller/")
    Call<SellerModel> getCurrentSeller(@Header("Authorization") String token);

}
