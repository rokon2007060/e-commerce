package com.example.e_commerce_app;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class CustomerProductAdapter extends RecyclerView.Adapter<CustomerProductAdapter.ProductViewHolder> {

    private Context context;
    private List<Product> productList;

    public CustomerProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_customer_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.productName.setText(product.getName());
        holder.productDescription.setText(product.getDescription());
        holder.productPrice.setText("৳" + product.getPrice());
        holder.productCategory.setText(product.getCategory());
        Glide.with(context).load(product.getImageUrl()).into(holder.productImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProductDetails(product);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productDescription, productPrice, productCategory;
        ImageView productImage;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.product_name);
            productDescription = itemView.findViewById(R.id.product_description);
            productPrice = itemView.findViewById(R.id.product_price);
            productCategory = itemView.findViewById(R.id.product_category);
            productImage = itemView.findViewById(R.id.product_image);
        }
    }

    private void showProductDetails(Product product) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.overlay_product_details);

        ImageView productImage = dialog.findViewById(R.id.product_image);
        TextView productName = dialog.findViewById(R.id.product_name);
        TextView productDescription = dialog.findViewById(R.id.product_description);
        TextView productPrice = dialog.findViewById(R.id.product_price);
        TextView productCategory = dialog.findViewById(R.id.product_category);
        RatingBar productRating = dialog.findViewById(R.id.product_rating);
        Button addToCartButton = dialog.findViewById(R.id.add_to_cart_button);
        Button addToFavoritesButton = dialog.findViewById(R.id.add_to_favorites_button);
        Button closeButton = dialog.findViewById(R.id.close_button);

        Glide.with(context).load(product.getImageUrl()).into(productImage);
        productName.setText(product.getName());
        productDescription.setText(product.getDescription());
        productPrice.setText("৳" + product.getPrice());
        productCategory.setText(product.getCategory());
        productRating.setRating(product.getRating());

        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add product to cart
                // Add your add to cart logic here
                dialog.dismiss();
            }
        });

        addToFavoritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add product to favorites
                // Add your add to favorites logic here
                dialog.dismiss();
            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
