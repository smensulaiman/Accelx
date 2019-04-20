package com.example.accels.retrofitapi;

import com.example.accels.Item.Products;
import com.example.accels.Item.Test;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIInterface {

    @FormUrlEncoded
    @POST("upload.jsp")
    Call<List<Products>> uploadImage(
            @Field("title") String title,
            @Field("image") String image
    );

    @GET("/posts")
    Call<List<Test>>demoData();
}
