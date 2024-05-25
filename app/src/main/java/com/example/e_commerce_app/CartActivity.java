package com.example.e_commerce_app;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    private TextView totalPriceTextView;
    private Button checkoutButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.recycler_view_cart);
        totalPriceTextView = findViewById(R.id.text_view_total_price);
        checkoutButton = findViewById(R.id.button_checkout);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartAdapter = new CartAdapter(ShopFacade.getInstance().getCartItems(), cartItem -> {
            ShopFacade.getInstance().removeFromCart(cartItem.getProduct().getId());
            updateCart();
        });
        recyclerView.setAdapter(cartAdapter);

        updateCart();

        checkoutButton.setOnClickListener(v -> showPaymentOverlay());

    }

    private void updateCart() {
        List<CartItem> cartItems = ShopFacade.getInstance().getCartItems();
        double totalPrice = 0;

        for (CartItem cartItem : cartItems) {
            totalPrice += cartItem.getProduct().getPrice() * cartItem.getQuantity();
        }

        cartAdapter.updateCartItems(cartItems);
        totalPriceTextView.setText("Total Price: ৳" + totalPrice);
    }

    private void showPaymentOverlay() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_payment);

        TextView payableAmountTextView = dialog.findViewById(R.id.text_view_payable_amount);
        RadioGroup paymentMethodsRadioGroup = dialog.findViewById(R.id.radio_group_payment_methods);
        Button confirmPaymentButton = dialog.findViewById(R.id.button_confirm_payment);

        double totalPrice = ShopFacade.getInstance().getTotalPrice();
        payableAmountTextView.setText("Payable Amount: ৳" + totalPrice);

        confirmPaymentButton.setOnClickListener(v -> {
            int selectedId = paymentMethodsRadioGroup.getCheckedRadioButtonId();
            if (selectedId != -1) {
                final String paymentMethod;
                if (selectedId == R.id.radio_bkash) {
                    paymentMethod = "Bkash";
                } else if (selectedId == R.id.radio_cash_on_delivery) {
                    // Handle cash on delivery
                    Toast.makeText(CartActivity.this, "Cash on Delivery Selected", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    return;
                } else if (selectedId == R.id.radio_nagad) {
                    paymentMethod = "Nagad";
                } else if (selectedId == R.id.radio_rocket) {
                    paymentMethod = "Rocket";
                } else {
                    paymentMethod = "";
                }

                if (!paymentMethod.isEmpty()) {
                    String merchantNumber = "YOUR_MERCHANT_NUMBER"; // Replace with your actual merchant number

                    PaymentMethod method = PaymentFactory.getPaymentMethod(paymentMethod);
                    method.pay(totalPrice, merchantNumber, new PaymentCallback() {
                        @Override
                        public void onSuccess() {
                            runOnUiThread(() -> {
                                Toast.makeText(CartActivity.this, "Payment Successful via " + paymentMethod, Toast.LENGTH_SHORT).show();
                            });
                        }

                        @Override
                        public void onFailure(String error) {
                            runOnUiThread(() -> {
                                Toast.makeText(CartActivity.this, "Payment Failed via " + paymentMethod + ": " + error, Toast.LENGTH_SHORT).show();
                            });
                        }
                    });

                    dialog.dismiss();
                }
            } else {
                Toast.makeText(CartActivity.this, "Please select a payment method", Toast.LENGTH_SHORT).show();
            }
        });


        dialog.show();
    }






}
