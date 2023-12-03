package com.example.EatHub.Transformer;

import com.example.EatHub.dto.responseDTOs.CartResponse;
import com.example.EatHub.model.Cart;

import java.util.ArrayList;

public class CartTransformer {
    public static CartResponse CartToCartResponse(Cart cart){
        return CartResponse.builder()
                .cartTotal(cart.getCartTotal())
                .foodItems(new ArrayList<>())
                .build();
    }

}
