package com.example.hoang_minh_shop.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hoang_minh_shop.R;
import com.example.hoang_minh_shop.activities.DetailProductActivity;
import com.example.hoang_minh_shop.adapters.ProductAdapter;
import com.example.hoang_minh_shop.models.Product;
import com.example.hoang_minh_shop.repositories.Repository;
import com.example.hoang_minh_shop.services.ApiCallBack;

import java.util.List;

public class HomeFragment extends Fragment implements ApiCallBack<List<Product>>, ProductAdapter.OnItemClickListener, View.OnClickListener {
    private RecyclerView recyclerView;
    private View layoutLoading;
    private TextView tvRetry;
    private ProductAdapter productAdapter;
    private ViewFlipper viewFlipper;
    private int[] resourceImage = new int[]{R.drawable.banner1, R.drawable.banner2,
            R.drawable.banner3, R.drawable.banner4, R.drawable.banner5};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        layoutLoading = view.findViewById(R.id.layoutLoading);
        tvRetry = view.findViewById(R.id.tvRetry);
        recyclerView = view.findViewById(R.id.recyclerView);
        viewFlipper = view.findViewById(R.id.viewFlipper);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        productAdapter = new ProductAdapter(getActivity());
        productAdapter.setOnItemClickListener(this);

        recyclerView.setAdapter(productAdapter);

        tvRetry.setOnClickListener(this);

        for (int i = 0; i < resourceImage.length; i++) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setImageResource(resourceImage[i]);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }

        Repository.getNewestProduct(this);
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
    public void onItemClick(Product product) {
        Intent intent = new Intent(getActivity(), DetailProductActivity.class);
        intent.putExtra("product", product);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        Repository.getNewestProduct(this);
    }
}
