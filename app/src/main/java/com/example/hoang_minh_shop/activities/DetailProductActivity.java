package com.example.hoang_minh_shop.activities;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.hoang_minh_shop.R;
import com.example.hoang_minh_shop.database.DatabaseHelper;
import com.example.hoang_minh_shop.models.Product;
import com.example.hoang_minh_shop.database.PreferencesManager;
import com.example.hoang_minh_shop.supports.Supports;

import es.dmoral.toasty.Toasty;

public class DetailProductActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "DetailProductActivity";
    private Product product;
    private ImageView image;
    private TextView tvName, tvPrice, tvDescription;
    private EditText etAmount;
    private ProgressBar loading;
    private Button btAdd;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);

        this.product = (Product) getIntent().getSerializableExtra("product");

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        image = findViewById(R.id.imageProduct);
        tvName = findViewById(R.id.tvName);
        tvPrice = findViewById(R.id.tvPrice);
        tvDescription = findViewById(R.id.tvDescription);
        etAmount = findViewById(R.id.etAmount);
        btAdd = findViewById(R.id.btAdd);
        loading = findViewById(R.id.pbLoading);

        Glide.with(this)
                .load(product.getImage())
                .centerCrop()
                .addListener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        loading.setVisibility(View.INVISIBLE);
                        return false;
                    }
                })
                .into(image);

        tvName.setText(product.getName());
        tvPrice.setText("Giá: " + Supports.formatMoney(product.getPrice()));
        tvDescription.setText(product.getDescription());

        btAdd.setOnClickListener(this);

        databaseHelper = DatabaseHelper.getInstance(this);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        int amount = Integer.parseInt(etAmount.getText().toString().trim());
        if (amount == 0) {
            Supports.showToastError(this, "Số lượng không hợp lệ!");
            return;
        }
        String phoneNumber = PreferencesManager.getInstance(this).getUser().getPhoneNumber();
        int idProduct = this.product.getId();
        if (databaseHelper.isProductExists(phoneNumber, idProduct)) {
            int oldAmount = databaseHelper.getAmountOfProductByProductId(phoneNumber, idProduct);
            databaseHelper.updateAmountProduct(phoneNumber, idProduct, oldAmount + amount);
        } else {
            Product product = new Product(
                    phoneNumber,
                    idProduct,
                    this.product.getName(),
                    this.product.getImage(),
                    this.product.getPrice(),
                    amount
            );
            databaseHelper.insertProduct(product);
        }
        Toast toastSuccess = Toasty.success(this, "Thêm thành công", Toasty.LENGTH_SHORT);
        toastSuccess.setGravity(Gravity.CENTER, 0, 0);
        toastSuccess.show();
    }

}
