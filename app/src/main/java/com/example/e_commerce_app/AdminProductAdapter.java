package com.example.e_commerce_app;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class AdminProductAdapter extends RecyclerView.Adapter<AdminProductAdapter.ViewHolder> {

    private static final String TAG = "AdminProductAdapter";

    private Context context;
    private List<Product> productList;
    private FirebaseFirestore db;
    private FirebaseStorage storage;

    public AdminProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
        this.db = FirebaseFirestore.getInstance();
        this.storage = FirebaseStorage.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product_admin, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.nameTextView.setText("Name: " + product.getName());
        holder.priceTextView.setText("Price: ৳" + product.getPrice());
        holder.descriptionTextView.setText("Description: " + product.getDescription());
        holder.categoryTextView.setText("Category: " + product.getCategory());

        Glide.with(context)
                .load(product.getImageUrl())
                .into(holder.imageView);

        holder.imageView.setOnClickListener(view -> showProductDialog(product));
    }

    private void showProductDialog(Product product) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_product_info, null);
        builder.setView(dialogView);

        TextView sellerNameTextView = dialogView.findViewById(R.id.seller_name);
        Button deleteButton = dialogView.findViewById(R.id.delete_button);

        // Fetch the seller's name using the sellerId
        db.collection("users").document(product.getSellerId()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                String sellerName = task.getResult().getString("full_name");
                sellerNameTextView.setText("Seller: " + sellerName);
            } else {
                sellerNameTextView.setText("Seller: Unknown");
            }
        });

        AlertDialog dialog = builder.create();

        deleteButton.setOnClickListener(view -> deleteProduct(product, dialog));

        dialog.show();
    }

    private void deleteProduct(Product product, AlertDialog dialog) {
        // Log the image URL
        Log.d(TAG, "Deleting image at URL: " + product.getImageUrl());

        // Delete image from storage
        StorageReference imageRef = storage.getReferenceFromUrl(product.getImageUrl());
        imageRef.delete().addOnSuccessListener(aVoid -> {
            // Image deleted successfully, now delete the product from Firestore
            Log.d(TAG, "Image deleted successfully. Now deleting Firestore document for product ID: " + product.getId());

            db.collection("products").document(product.getId())
                    .delete()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Product deleted successfully from Firestore for product ID: " + product.getId());
                            Toast.makeText(context, "Product deleted successfully", Toast.LENGTH_SHORT).show();
                            removeProductFromList(product.getId());
                            dialog.dismiss();
                        } else {
                            Log.e(TAG, "Failed to delete product from Firestore for product ID: " + product.getId(), task.getException());
                            Toast.makeText(context, "Failed to delete product from Firestore", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(e -> {
                        Log.e(TAG, "Failed to delete product from Firestore for product ID: " + product.getId(), e);
                        Toast.makeText(context, "Failed to delete product from Firestore", Toast.LENGTH_SHORT).show();
                    });
        }).addOnFailureListener(e -> {
            Log.e(TAG, "Failed to delete image: " + e.getMessage());
            Toast.makeText(context, "Failed to delete image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }

    private void removeProductFromList(String productId) {
        for (int i = 0; i < productList.size(); i++) {
            if (productList.get(i).getId().equals(productId)) {
                productList.remove(i);
                notifyItemRemoved(i);
                return;
            }
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, priceTextView, descriptionTextView, categoryTextView;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.product_name);
            priceTextView = itemView.findViewById(R.id.product_price);
            descriptionTextView = itemView.findViewById(R.id.product_description);
            imageView = itemView.findViewById(R.id.product_image);
            categoryTextView = itemView.findViewById(R.id.product_cat);
        }
    }
}
