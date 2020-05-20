package com.example.hoang_minh_shop.services;

import com.example.hoang_minh_shop.models.Product;
import com.example.hoang_minh_shop.models.User;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiServices {
    @GET("server/getsanpham.php")
    Call<List<Product>> getProductByType(@Query("id_product_type") int type);

    @GET("server/getsanphammoinhat.php")
    Call<List<Product>> getNewestProduct();

    @FormUrlEncoded
    @POST("server/sign_in.php")
    Call<User> signIn(@Field("phone_number") String phoneNumber, @Field("password") String password);

    @FormUrlEncoded
    @POST("server/sign_up.php")
    Call<String> signUp(@Field("phone_number") String phoneNumber,
                      @Field("password") String password,
                      @Field("full_name") String fullName,
                      @Field("address") String address);

}
