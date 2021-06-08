package com.example.dokandar24.Common.Api;

import com.example.dokandar24.Common.Model.CashInListModel;
import com.example.dokandar24.Common.Model.ProductListModel;
import com.example.dokandar24.Common.Responses.CustomResponse;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ProductRetrofitApi {
    @Multipart
    @POST("product")
    Call<CustomResponse> uploadProductImage(
            @Header("Authorization") String token,
            @Part MultipartBody.Part[] product,
            @Part("productPrice") int price,
            @Part("description") String description,
            @Part("title") String title,
            @Part("category") String category,
            @Part("subCategory") String subCategory
            );
    @GET("product/my")
    Call<ProductListModel> getMyAllProduct(@Header("Authorization") String token);

}
