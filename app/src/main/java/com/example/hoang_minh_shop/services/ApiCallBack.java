package com.example.hoang_minh_shop.services;

public interface ApiCallBack<T> {
    void onLoading(boolean isLoading);

    void onSuccess(T data);

    void onError(String error);
}
