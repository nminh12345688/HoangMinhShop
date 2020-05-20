package com.example.hoang_minh_shop.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hoang_minh_shop.R;
import com.example.hoang_minh_shop.activities.DetailProductActivity;
import com.example.hoang_minh_shop.adapters.ProductAdapter;
import com.example.hoang_minh_shop.models.Product;
import com.example.hoang_minh_shop.repositories.Repository;
import com.example.hoang_minh_shop.services.ApiCallBack;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductFragment extends Fragment implements ProductAdapter.OnItemClickListener, ApiCallBack<List<Product>>, View.OnClickListener {
    private int productType;
    private View layoutLoading;
    private TextView tvRetry;
    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    public ProductFragment(int productType) {
        // Required empty public constructor
        this.productType = productType;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product, container, false);

        layoutLoading = view.findViewById(R.id.layoutLoading);
        tvRetry = view.findViewById(R.id.tvRetry);
        recyclerView = view.findViewById(R.id.recyclerView);

        tvRetry.setOnClickListener(this);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        productAdapter = new ProductAdapter(getActivity());
        productAdapter.setOnItemClickListener(this);

        recyclerView.setAdapter(productAdapter);

        Repository.getProductByType(productType, this);


    }

    @Override
    public void onItemClick(Product product) {
        Intent intent = new Intent(getActivity(), DetailProductActivity.class);
        intent.putExtra("product", product);
        startActivity(intent);
    }

    @Override
    public void onLoading(boolean isLoading) {
        tvRetry.setVisibility(View.GONE);
        if (isLoading){
            layoutLoading.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
        }
        else{
            layoutLoading.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onSuccess(List<Product> products) {
        productAdapter.setProducts(products);
    }

    @Override
    public void onError(String error) {
        tvRetry.setVisibility(View.VISIBLE);
        layoutLoading.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        Repository.getProductByType(productType, this);
    }
}
