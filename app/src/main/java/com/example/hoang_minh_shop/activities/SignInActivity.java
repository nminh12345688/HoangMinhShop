package com.example.hoang_minh_shop.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hoang_minh_shop.R;
import com.example.hoang_minh_shop.models.User;
import com.example.hoang_minh_shop.repositories.Repository;
import com.example.hoang_minh_shop.services.ApiCallBack;
import com.example.hoang_minh_shop.database.PreferencesManager;
import com.example.hoang_minh_shop.supports.Supports;
import com.google.android.material.textfield.TextInputLayout;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener, ApiCallBack<User> {
    private static final String TAG = "SignInActivity";
    private EditText etPhoneNumber, etPassword;
    private View layoutLoading, layoutSignIn;
    private TextInputLayout layoutPassword;
    private TextInputLayout layoutPhoneNumber;
    private TextView tvSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        createView();

        addTextChangedListener();

        findViewById(R.id.btSignIn).setOnClickListener(this);

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
            }
        });
    }

    private void createView(){
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        etPassword = findViewById(R.id.etPassword);
        layoutLoading = findViewById(R.id.layoutLoading);
        layoutSignIn = findViewById(R.id.layoutSignIn);
        layoutPassword = findViewById(R.id.layoutPassword);
        layoutPhoneNumber = findViewById(R.id.layoutPhoneNumber);
        tvSignUp = findViewById(R.id.tvSignUp);
    }

    private void addTextChangedListener(){
        etPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().isEmpty()){
                    layoutPhoneNumber.setError("Số điện thoại không thể trống");
                }
                else{
                    layoutPhoneNumber.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().isEmpty()){
                    layoutPassword.setError("Mật khẩu không thể trống");
                }
                else{
                    layoutPassword.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        String phoneNumber = etPhoneNumber.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        if (!Supports.isValidPhoneNumber(phoneNumber)){
            layoutPhoneNumber.setError("Số điện thoại không hợp lệ");
            return;
        }
        if (password.isEmpty()){
            layoutPassword.setError("Mật khẩu không thể trống");
            return;
        }
        if (!Supports.isNetworkConnected(this)){
            Supports.showToastError(this, "Không có kết nối internet");
            return;
        }
        Repository.signIn(phoneNumber, password, this);
    }

    @Override
    public void onLoading(boolean isLoading) {
        if (isLoading) {
            layoutSignIn.setVisibility(View.INVISIBLE);
            layoutLoading.setVisibility(View.VISIBLE);
        } else {
            layoutSignIn.setVisibility(View.VISIBLE);
            layoutLoading.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onSuccess(User user) {
        PreferencesManager.getInstance(this).saveUser(user);
        Supports.showToastSuccess(this, "Đăng nhập thành công");
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void onError(String error) {
        Supports.showToastError(this, "Số điện thoại hoặc mật khẩu không chính xác");
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (PreferencesManager.getInstance(this).getUser() != null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }
}
