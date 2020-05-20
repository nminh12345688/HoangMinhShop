package com.example.hoang_minh_shop.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.hoang_minh_shop.R;
import com.example.hoang_minh_shop.repositories.Repository;
import com.example.hoang_minh_shop.services.ApiCallBack;
import com.example.hoang_minh_shop.supports.Supports;
import com.google.android.material.textfield.TextInputLayout;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener, ApiCallBack<String> {
    private EditText etFullName, etAddress, etPhoneNumber, etPassword, etConfirmPassword;
    private TextInputLayout layoutFullName, layoutAddress, layoutPhoneNumber, layoutPassword, layoutConfirmPassword;
    private View layoutLoading, layoutSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);

        createView();

        addTextChangedListener();

        findViewById(R.id.btSignUp).setOnClickListener(this);
    }

    private void addTextChangedListener() {
        etFullName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                layoutFullName.setError(s.toString().isEmpty() ? "Họ tên không thể trống" : null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                layoutAddress.setError(s.toString().isEmpty() ? "Địa chỉ không thể trống" : null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                layoutPhoneNumber.setError(s.toString().isEmpty() ? "Số điện thoại không thể trống" : null);
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
                layoutPassword.setError(s.toString().isEmpty() ? "Mật khẩu không thể trống" : null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                layoutConfirmPassword.setError(s.toString().isEmpty() ? "Vui lòng xác nhận mật khẩu" : null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void createView() {
        etFullName = findViewById(R.id.etFullName);
        etAddress = findViewById(R.id.etAddress);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        layoutLoading = findViewById(R.id.layoutLoading);
        layoutSignUp = findViewById(R.id.layoutSignUp);
        layoutFullName = findViewById(R.id.layoutFullName);
        layoutAddress = findViewById(R.id.layoutAddress);
        layoutPhoneNumber = findViewById(R.id.layoutPhoneNumber);
        layoutPassword = findViewById(R.id.layoutPassword);
        layoutConfirmPassword = findViewById(R.id.layoutConfirmPassword);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        String phoneNumber = etPhoneNumber.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String fullName = etFullName.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();
        if (!Supports.isNetworkConnected(this)){
            Supports.showToastError(this, "Không có kết nối internet");
            return;
        }
        if (fullName.isEmpty()){
            layoutFullName.setError("Họ tên không thể trống");
            return;
        }
        if (address.isEmpty()){
            layoutAddress.setError("Địa chỉ không thể trống");
            return;
        }
        if (!Supports.isValidPhoneNumber(phoneNumber)){
            layoutPhoneNumber.setError("Số điện thoại không hợp lệ");
            return;
        }
        if (password.isEmpty()){
            layoutPassword.setError("Mật khẩu không thể trống");
            return;
        }
        if (!password.equals(confirmPassword)){
            layoutConfirmPassword.setError("Mật khẩu không khớp");
            return;
        }
        if (!password.isEmpty() && !fullName.isEmpty() && !address.isEmpty()){
            Repository.signUp(phoneNumber, password, fullName, address, this);
        }

    }

    @Override
    public void onLoading(boolean isLoading) {
        if (isLoading){
            layoutLoading.setVisibility(View.VISIBLE);
            layoutSignUp.setVisibility(View.INVISIBLE);
        } else {
            layoutLoading.setVisibility(View.INVISIBLE);
            layoutSignUp.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onSuccess(String data) {
        Supports.showToastSuccess(this, data);
        finish();
    }

    @Override
    public void onError(String error) {
        Supports.showToastError(this, error);
    }
}
