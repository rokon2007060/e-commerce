// ProductAdapter.java
package com.example.e_commerce_app;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<Product> productList;
    private Context context;
    private ListenerRegistration registration;

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;

        // Attach a listener for real-time updates
        attachSnapshotListener();
    }

    private void attachSnapshotListener() {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        registration = firestore.collection("products")
                .whereEqualTo("sellerId", userId)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshots,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w("ProductAdapter", "listen:error", e);
                            return;
                        }

                        // Update the productList with the new data
                        productList.clear();
                        for (DocumentSnapshot doc : snapshots.getDocuments()) {
                            Product product = doc.toObject(Product.class);
                            productList.add(product);
                        }
                        notifyDataSetChanged();
                    }
                });
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        if (registration != null) {
            registration.remove();
        }
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.productName.setText(product.getName());
        holder.productDescription.setText(product.getDescription());
        holder.productPrice.setText("৳" + product.getPrice());
        holder.productCategory.setText(product.getCategory());

        RequestOptions requestOptions = new RequestOptions()
                .override(100, 100)
                .centerCrop()
                .circleCrop();

        Glide.with(context)
                .load(product.getImageUrl())
                .apply(requestOptions)
                .into(holder.productImage);

        holder.productImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUpdateDeleteDialog(product);
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

    private void showUpdateDeleteDialog(Product product) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_update_delete_product, null);
        builder.setView(view);

        EditText editProductPrice = view.findViewById(R.id.edit_product_price);
        EditText editProductDescription = view.findViewById(R.id.edit_product_description);
        EditText editProductCategory = view.findViewById(R.id.edit_product_category);
        Button btnUpdateProduct = view.findViewById(R.id.btn_update_product);
        Button btnDeleteProduct = view.findViewById(R.id.btn_delete_product);

        editProductPrice.setText(String.valueOf(product.getPrice()));
        editProductDescription.setText(product.getDescription());
        editProductCategory.setText(product.getCategory());

        AlertDialog dialog = builder.create();

        btnUpdateProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double newPrice = Double.parseDouble(editProductPrice.getText().toString().trim());
                String newDescription = editProductDescription.getText().toString().trim();
                String newCategory = editProductCategory.getText().toString().trim();

                updateProduct(product.getId(), newPrice, newDescription, newCategory);
                dialog.dismiss();
            }
        });

        btnDeleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteProduct(product.getId(), product.getImageUrl());
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void updateProduct(String productId, double newPrice, String newDescription, String newCategory) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
       Map<String,Object> p=new HashMap<>();
        Map<String, Object> productMap = new HashMap<>();
        productMap.put("price", newPrice);
        productMap.put("description", newDescription);
        productMap.put("category", newCategory);
        productMap.put("id", productId);




        firestore.collection("products").document(productId)
                .update(productMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(context, "Product updated", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Failed to update product", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void deleteProduct(String productId, String imageUrl) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        FirebaseStorage storage = FirebaseStorage.getInstance();

        // Delete image from storage
        StorageReference imageRef = storage.getReferenceFromUrl(imageUrl);
        imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // Image deleted successfully
                // Now delete the product from Firestore
                firestore.collection("products").document(productId)

                        .delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(context, "Product deleted", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "Failed to delete product", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Failed to delete image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

