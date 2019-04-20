package com.example.splashactivity.retrofitapi;

import com.example.splashactivity.Item.Products;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIInterface {

    @FormUrlEncoded
    @POST("upload.jsp")
    Call<List<Products>> uploadImage(
            @Field("title") String title,
            @Field("image") String image
    );
}
