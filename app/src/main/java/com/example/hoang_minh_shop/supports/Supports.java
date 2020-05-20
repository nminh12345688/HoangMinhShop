package com.example.hoang_minh_shop.supports;

import android.content.Context;
import android.net.ConnectivityManager;
import android.text.TextUtils;
import android.util.Patterns;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import es.dmoral.toasty.Toasty;

public class Supports {

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        return !TextUtils.isEmpty(phoneNumber) &&
                Patterns.PHONE.matcher(phoneNumber).matches();
    }

    public static void showToastSuccess(Context context, String message){
        Toasty.success(context, message, Toasty.LENGTH_SHORT).show();
    }

    public static void showToastError(Context context, String message){
        Toasty.error(context, message, Toasty.LENGTH_SHORT).show();
    }

    public static String formatMoney(long money){
        //return NumberFormat.getCurrencyInstance(Locale.getDefault()).format(money);
        DecimalFormat formatter = new DecimalFormat("###,###");
        return formatter.format(money)+ " đ";
    }
}
