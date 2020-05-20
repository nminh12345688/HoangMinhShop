package com.example.hoang_minh_shop.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.hoang_minh_shop.models.Product;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper instance;

    private static final String DB_NAME = "db_hoangminh_shop";
    private static final String TAG = "DatabaseHelper";
    private static final String TABLE_CART = "carts";
    private static final String COL_ID = "id";
    private static final String COL_ID_PRODUCT = "id_product";
    private static final String COL_PHONE_NUMBER = "phone_number";
    private static final String COL_NAME = "name";
    private static final String COL_IMAGE = "image";
    private static final String COL_PRICE = "price";
    private static final String COL_AMOUNT = "amount";

    public static DatabaseHelper getInstance(Context context){
        if (instance == null){
            instance = new DatabaseHelper(context);
        }
        return instance;
    }

    private DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE "
                + TABLE_CART + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_PHONE_NUMBER + " TEXT, "
                + COL_ID_PRODUCT + " INTEGER, "
                + COL_NAME + " TEXT, "
                + COL_IMAGE + " TEXT, "
                + COL_PRICE + " INTEGER, "
                + COL_AMOUNT + " INTEGER)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
        onCreate(db);
    }

    public long insertProduct(Product product) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_PHONE_NUMBER, product.getPhoneNumber());
        values.put(COL_ID_PRODUCT, product.getId());
        values.put(COL_NAME, product.getName());
        values.put(COL_IMAGE, product.getImage());
        values.put(COL_PRICE, product.getPrice());
        values.put(COL_AMOUNT, product.getAmount());
        return database.insert(TABLE_CART, null, values);
    }

    public void deleteProduct(Product product) {
        SQLiteDatabase database = this.getWritableDatabase();
        String whereClause = COL_PHONE_NUMBER + "=? AND " + COL_ID_PRODUCT + "=?";
        database.delete(TABLE_CART, whereClause, new String[]{product.getPhoneNumber(), String.valueOf(product.getId())});
        database.close();
    }

    public void updateAmountProduct(String phoneNumber, int idProduct, int newAmount) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_AMOUNT, newAmount);
        String whereClause = COL_PHONE_NUMBER + "=? and " + COL_ID_PRODUCT + "=?";
        database.update(TABLE_CART, values, whereClause, new String[]{phoneNumber, String.valueOf(idProduct)});
        database.close();
    }

    public int getAmountOfProductByProductId(String phoneNumber, int idProduct){
        SQLiteDatabase database = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_CART + " WHERE " + COL_PHONE_NUMBER + " = \'" + phoneNumber + "\' AND "
                + COL_ID_PRODUCT + " = \'" + idProduct + "\'";
        Cursor cursor = database.rawQuery(sql, null);
        int amount = 0;
        if (cursor.moveToFirst()) {
            amount = cursor.getInt(6);
        }
        return amount;
    }
    public List<Product> getProductsByPhoneNumber(String phoneNumber) {
        SQLiteDatabase database = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_CART + " WHERE " + COL_PHONE_NUMBER + " = \'" + phoneNumber + "\'";
        Cursor cursor = database.rawQuery(sql, null);
        List<Product> products = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Product product = new Product(
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getInt(5),
                        cursor.getInt(6)
                );
                products.add(product);
            } while (cursor.moveToNext());
        }
        return products;
    }

    public boolean isProductExists(String phoneNumber, int idProduct){
        SQLiteDatabase database = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_CART + " WHERE " + COL_PHONE_NUMBER + " = \'" + phoneNumber + "\' AND "
                + COL_ID_PRODUCT + " = \'" + idProduct + "\'";
        Cursor cursor = database.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            database.close();
           return true;
        }
        database.close();
        return false;
    }

//    public List<Product> getProducts() {
//        List<Product> products = new ArrayList<>();
//        SQLiteDatabase database = this.getReadableDatabase();
//        String sql = "SELECT * FROM " + TABLE_CART;
//        Cursor cursor = database.rawQuery(sql, null);
//        if (cursor.moveToFirst()) {
//            do {
//                Product product = new Product(
//                        cursor.getString(1),
//                        cursor.getInt(2),
//                        cursor.getString(3),
//                        cursor.getString(4),
//                        cursor.getInt(5),
//                        cursor.getInt(6)
//                );
//                products.add(product);
//            } while (cursor.moveToNext());
//        }
//        database.close();
//        return products;
//    }
}
