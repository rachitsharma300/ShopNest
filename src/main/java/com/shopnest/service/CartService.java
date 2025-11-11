package com.shopnest.service;

import com.shopnest.dto.CartDTO;

public interface CartService {
    CartDTO getCartByUserId(Long userId);
    void addToCart(Long userId, Long productId, Integer quantity);
    void updateCartItem(Long itemId, Integer quantity);
    void removeFromCart(Long itemId);
    void clearCart(Long userId);
}