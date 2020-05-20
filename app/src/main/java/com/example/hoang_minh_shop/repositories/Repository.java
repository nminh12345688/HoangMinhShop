package com.example.hoang_minh_shop.repositories;

import android.util.Log;

import com.example.hoang_minh_shop.models.Product;
import com.example.hoang_minh_shop.models.User;
import com.example.hoang_minh_shop.services.ApiCallBack;
import com.example.hoang_minh_shop.services.ApiServices;
import com.example.hoang_minh_shop.services.AppClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {
    private static final String TAG = "Repository";
    private static ApiServices apiServices = AppClient.getInstance().createRequest();

    public static void signIn(String phoneNumber, String password, final ApiCallBack<User> apiCallBack) {
        apiCallBack.onLoading(true);
        apiServices.signIn(phoneNumber, password).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                apiCallBack.onLoading(false);
                Log.d(TAG, "onResponse: " + response.body().getFullName());
                if (response.isSuccessful()) {
                    apiCallBack.onSuccess(response.body());
                } else {
                    apiCallBack.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                apiCallBack.onLoading(false);
                apiCallBack.onError(t.getMessage());
            }
        });
    }

    public static void getProductByType(int type, final ApiCallBack<List<Product>> apiCallBack) {
        apiCallBack.onLoading(true);
        apiServices.getProductByType(type).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                apiCallBack.onLoading(false);
                if (response.isSuccessful()) {
                    List<Product> products = response.body();
                    apiCallBack.onSuccess(products);
                } else {
                    apiCallBack.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                apiCallBack.onLoading(false);
                apiCallBack.onError(t.getMessage());
            }
        });
    }

    public static void signUp(String phoneNumber, String password, String fullName, String address, final ApiCallBack<String> apiCallBack){
        apiCallBack.onLoading(true);
        apiServices.signUp(phoneNumber, password, fullName, address).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    if (response.body().equals("success")){
                        apiCallBack.onSuccess("Đăng ký thành công");
                    }
                    else{
                        apiCallBack.onError("Số điện thoại đã được sử dụng bởi một tài khoản khác");
                    }
                }else{
                    apiCallBack.onError("Đăng ký thất bại");
                }
                apiCallBack.onLoading(false);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                apiCallBack.onError("Đăng ký thất bại");
                apiCallBack.onLoading(false);
            }
        });
    }

    public static void getNewestProduct(final ApiCallBack<List<Product>> apiCallBack){
        apiCallBack.onLoading(true);
        apiServices.getNewestProduct().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                apiCallBack.onLoading(false);
                if (response.isSuccessful()){
                    List<Product> products = response.body();
                    apiCallBack.onSuccess(products);
                }
                else{
                    apiCallBack.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                apiCallBack.onLoading(false);
                apiCallBack.onError(t.getMessage());
            }
        });
    }
}
