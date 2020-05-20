package com.example.hoang_minh_shop.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hoang_minh_shop.R;
import com.example.hoang_minh_shop.adapters.ProductCartAdapter;
import com.example.hoang_minh_shop.database.DatabaseHelper;
import com.example.hoang_minh_shop.models.Product;
import com.example.hoang_minh_shop.database.PreferencesManager;
import com.example.hoang_minh_shop.supports.Supports;

import java.util.List;

import es.dmoral.toasty.Toasty;

public class CartActivity extends AppCompatActivity implements ProductCartAdapter.OnItemClickListenerRecyclerView, View.OnClickListener {
    private static final String TAG = "CartActivity";
    private DatabaseHelper databaseHelper;
    private RecyclerView recyclerView;
    private ProductCartAdapter productCartAdapter;
    private TextView tvEmpty, tvTotalPrice;
    private Button btPayment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvEmpty = findViewById(R.id.tvEmpty);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        btPayment = findViewById(R.id.btPayment);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        productCartAdapter = new ProductCartAdapter(this);
        productCartAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(productCartAdapter);

        databaseHelper = DatabaseHelper.getInstance(this);

        btPayment.setOnClickListener(this);
        handler();
    }

    private AlertDialog.Builder builderDialogUpdateAmount(final String phoneNumber, final int idProduct, final int amount){
        View viewDialogUpdateAmount = LayoutInflater.from(this).inflate(R.layout.dialog_update_amount, null, false);
        final EditText etAmount = viewDialogUpdateAmount.findViewById(R.id.etAmount);
        etAmount.setText(String.valueOf(amount));
        return new AlertDialog.Builder(this)
                .setCancelable(false)
                .setView(viewDialogUpdateAmount)
                .setTitle("Số lượng")
                .setNegativeButton("Huỷ bỏ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int newAmount = Integer.parseInt(etAmount.getText().toString().trim());
                        databaseHelper.updateAmountProduct(phoneNumber, idProduct, newAmount);
                        handler();
                    }
                });
    }
    private void handler(){
        String phoneNumber = PreferencesManager.getInstance(this).getUser().getPhoneNumber();
        List<Product> products = databaseHelper.getProductsByPhoneNumber(phoneNumber);
        if (products.size() == 0){
            tvEmpty.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
            tvTotalPrice.setText("Tổng tiền: 0 đ");
        }
        else{
            tvEmpty.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            productCartAdapter.setProducts(products);
            tvTotalPrice.setText("Tổng tiền: " + Supports.formatMoney(totalPrice(products)));
        }
    }

    private int totalPrice(List<Product> products){
        int totalPrice = 0;
        for (Product product : products){
            totalPrice += (product.getPrice() * product.getAmount());
        }
        return totalPrice;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClickDelete(Product product) {
        databaseHelper.deleteProduct(product);
        handler();
    }

    @Override
    public void onItemClickUpdateAmount(Product product) {
        AlertDialog dialog = builderDialogUpdateAmount(product.getPhoneNumber(), product.getId(), product.getAmount()).create();
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        Toast toastSuccess = Toasty.success(this, "Thanh toán đơn hàng thành công", Toasty.LENGTH_SHORT);
        toastSuccess.setGravity(Gravity.CENTER, 0, 0);
        toastSuccess.show();
    }
}
