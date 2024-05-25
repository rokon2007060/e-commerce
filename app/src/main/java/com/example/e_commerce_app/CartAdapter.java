package com.example.e_commerce_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private List<CartItem> cartItems;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemRemove(CartItem cartItem);
    }

    public CartAdapter(List<CartItem> cartItems, OnItemClickListener listener) {
        this.cartItems = cartItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem cartItem = cartItems.get(position);
        holder.bind(cartItem, listener);
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public void updateCartItems(List<CartItem> newCartItems) {
        this.cartItems = newCartItems;
        notifyDataSetChanged();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        private TextView productNameTextView;
        private TextView productPriceTextView;
        private Spinner spinnerQuantity;
        private ImageView productImageView;
        private Button removeButton;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            productNameTextView = itemView.findViewById(R.id.text_view_product_name);
            productPriceTextView = itemView.findViewById(R.id.text_view_product_price);
            spinnerQuantity = itemView.findViewById(R.id.spinner_quantity);
            productImageView = itemView.findViewById(R.id.image_view_product);
            removeButton = itemView.findViewById(R.id.button_remove);
        }

        public void bind(final CartItem cartItem, final OnItemClickListener listener) {
            productNameTextView.setText(cartItem.getProduct().getName());
            productPriceTextView.setText("৳" + cartItem.getProduct().getPrice());

            // Set the product image
            // productImageView.setImageResource(cartItem.getProduct().getImageResource()); // Assuming you have a method to get the image resource

            // Setup the spinner for quantity selection
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(itemView.getContext(),
                    R.array.quantity_array, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerQuantity.setAdapter(adapter);

            // Set the spinner to the current quantity
            spinnerQuantity.setSelection(cartItem.getQuantity() - 1);

            removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemRemove(cartItem);
                }
            });

            // Optional: Handle spinner selection changes if needed
            spinnerQuantity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    int selectedQuantity = position + 1;
                    cartItem.setQuantity(selectedQuantity);
                    // Optionally update the cartItem in the database or notify the adapter about the change
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // Do nothing
                }
            });
        }
    }
}
