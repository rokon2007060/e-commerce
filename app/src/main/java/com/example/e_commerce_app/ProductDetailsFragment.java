package com.example.e_commerce_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.bumptech.glide.Glide;

public class ProductDetailsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_product_details, container, false);

        Bundle bundle = getArguments();
        if (bundle != null) {
            String productName = bundle.getString("productName");
            String productDescription = bundle.getString("productDescription");
            double productPrice = bundle.getDouble("productPrice");
            String productImageUrl = bundle.getString("productImageUrl");
            String productCategory = bundle.getString("productCategory");
            float productRating = bundle.getFloat("productRating");

            ImageView productImageView = rootView.findViewById(R.id.product_image);
            TextView productNameTextView = rootView.findViewById(R.id.product_name);
            TextView productDescriptionTextView = rootView.findViewById(R.id.product_description);
            TextView productPriceTextView = rootView.findViewById(R.id.product_price);
            TextView productCategoryTextView = rootView.findViewById(R.id.product_category);
//            RatingBar productRatingBar = rootView.findViewById(R.id.product_rating);
            ImageView addCart = rootView.findViewById(R.id.add_products_cart);
            ImageView addRavourite = rootView.findViewById(R.id.add_favourite);

            Button buyNowButton = rootView.findViewById(R.id.button_buy_now);

            Glide.with(requireContext()).load(productImageUrl).into(productImageView);
            productNameTextView.setText(productName);
            productDescriptionTextView.setText(productDescription);
            productPriceTextView.setText(String.format("৳%.2f", productPrice));
            productCategoryTextView.setText(productCategory);
//            productRatingBar.setRating(productRating);
        }

        return rootView;
    }
}
