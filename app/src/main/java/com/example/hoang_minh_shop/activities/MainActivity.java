package com.example.hoang_minh_shop.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.hoang_minh_shop.models.ProductType;
import com.example.hoang_minh_shop.R;
import com.example.hoang_minh_shop.fragments.HomeFragment;
import com.example.hoang_minh_shop.fragments.ProductFragment;
import com.example.hoang_minh_shop.database.PreferencesManager;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView navigationView;
    private AlertDialog dialogConfirmSignOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        createDrawer();

        getSupportActionBar().setTitle("Trang chủ");
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new HomeFragment()).commit();

        dialogConfirmSignOut = builderDialogSignOut().create();
    }
    private AlertDialog.Builder builderDialogSignOut(){
        return new AlertDialog.Builder(this)
                .setTitle("Đăng xuất")
                .setMessage("Bạn muốn đăng xuất?")
                .setNegativeButton("Huỷ bỏ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        signOut();
                    }
                });
    }
    private void createDrawer() {
        drawerLayout = findViewById(R.id.drawerLayout);
        drawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        navigationView = findViewById(R.id.navigationViewMenu);
        navigationView.setNavigationItemSelectedListener(this);

        View viewHeader = navigationView.getHeaderView(0);
        TextView tvFullName = viewHeader.findViewById(R.id.tvFullName);
        String fullName = PreferencesManager.getInstance(this).getUser().getFullName();
        tvFullName.setText(fullName);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (drawerToggle.onOptionsItemSelected(item))
            return true;
        if (id == R.id.itemMenuCart){
            startActivity(new Intent(this, CartActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.navHome:
                getSupportActionBar().setTitle("Trang chủ");
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new HomeFragment()).commit();
                break;
            case R.id.navPants:
                getSupportActionBar().setTitle("Quần các loại");
                startFragment(ProductType.PANTS);
                break;
            case R.id.navShirt:
                getSupportActionBar().setTitle("Áo các loại");
                startFragment(ProductType.SHIRT);
                break;
            case R.id.navDress:
                getSupportActionBar().setTitle("Đầm váy");
                startFragment(ProductType.DRESS);
                break;
            case R.id.navSkirt:
                getSupportActionBar().setTitle("Chân váy");
                startFragment(ProductType.SKIRT);
                break;
            case R.id.navSignOut:
                dialogConfirmSignOut.show();
                break;
            default:
                break;
        }
        drawerLayout.closeDrawers();
        return true;
    }

    private void signOut() {
        PreferencesManager.getInstance(this).deleteUser();
        startActivity(new Intent(this, SignInActivity.class));
        finish();
    }

    private void startFragment(int productType){
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new ProductFragment(productType)).commit();
    }
}
