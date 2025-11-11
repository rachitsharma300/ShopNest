package com.shopnest.service.impl;

import com.shopnest.dto.CartDTO;
import com.shopnest.entity.Cart;
import com.shopnest.entity.CartItem;
import com.shopnest.entity.Product;
import com.shopnest.entity.User;
import com.shopnest.exception.ProductNotFoundException;
import com.shopnest.exception.UserNotFoundException;
import com.shopnest.repository.CartRepository;
import com.shopnest.repository.ProductRepository;
import com.shopnest.repository.UserRepository;
import com.shopnest.service.CartService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public CartServiceImpl(CartRepository cartRepository,
                           UserRepository userRepository,
                           ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Override
    public CartDTO getCartByUserId(Long userId) {
        Cart cart = cartRepository.findByUserIdWithItems(userId)
                .orElseGet(() -> createCartForUser(userId));
        return convertToDTO(cart);
    }

    @Override
    public void addToCart(Long userId, Long productId, Integer quantity) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> createCartForUser(userId));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        // Check if product already in cart
        CartItem existingItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
        } else {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            newItem.setPrice(product.getPrice());
            cart.addCartItem(newItem);
        }

        cartRepository.save(cart);
    }

    @Override
    public void updateCartItem(Long itemId, Integer quantity) {
        // For simplicity, we'll implement this in controller directly
        // You can enhance this later
    }

    @Override
    public void removeFromCart(Long itemId) {
        // For simplicity, we'll implement this in controller directly
        // You can enhance this later
    }

    @Override
    public void clearCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        cart.clearCart();
        cartRepository.save(cart);
    }

    private Cart createCartForUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        Cart cart = new Cart();
        cart.setUser(user);
        return cartRepository.save(cart);
    }

    private CartDTO convertToDTO(Cart cart) {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(cart.getId());
        cartDTO.setUserId(cart.getUser().getId());
        cartDTO.setTotalPrice(cart.getTotalPrice());
        // You can add cart items conversion here if needed
        return cartDTO;
    }
}