package com.example.hoang_minh_shop.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.hoang_minh_shop.R;
import com.example.hoang_minh_shop.models.Product;
import com.example.hoang_minh_shop.supports.Supports;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private Context context;
    private List<Product> products;
    private OnItemClickListener onItemClickListener;

    public ProductAdapter(Context context) {
        this.context = context;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Product product = products.get(position);
        Glide
                .with(context)
                .load(product.getImage())
                .centerCrop()
                .addListener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.pbLoading.setVisibility(View.INVISIBLE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.pbLoading.setVisibility(View.INVISIBLE);
                        return false;
                    }
                })
                .into(holder.image);
        holder.tvName.setText(product.getName());
        holder.tvPrice.setText("Giá: " + Supports.formatMoney(product.getPrice()));
    }

    @Override
    public int getItemCount() {
        return products == null ? 0 : products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView tvName, tvPrice;
        ProgressBar pbLoading;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            pbLoading = itemView.findViewById(R.id.pbLoading);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(products.get(getAdapterPosition()));
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Product product);
    }
}
