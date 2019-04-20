package com.example.splashactivity.Item;

import com.google.gson.annotations.SerializedName;

public class Products {


    @SerializedName("title")
    private String Title;
    @SerializedName("image")
    private String Image;
    @SerializedName("price")
    private String Price;

    public String getTitle() {
        return Title;
    }

    public String getImage() {
        return Image;
    }

    public String getPrice() {
        return Price;
    }
}
