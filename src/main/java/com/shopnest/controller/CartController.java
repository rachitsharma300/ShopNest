package com.shopnest.controller;

import com.shopnest.dto.CartDTO;
import com.shopnest.service.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public String viewCart(Model model) {
        try {
            // In real app, get current user from session
            Long userId = 1L; // Temporary - replace with actual user from security context
            CartDTO cart = cartService.getCartByUserId(userId);
            model.addAttribute("cart", cart);
            return "cart/cart";
        } catch (Exception e) {
            model.addAttribute("error", "Cart not found");
            return "cart/cart";
        }
    }

    @PostMapping("/add/{productId}")
    public String addToCart(@PathVariable Long productId,
                            @RequestParam(defaultValue = "1") Integer quantity,
                            RedirectAttributes redirectAttributes) {
        try {
            Long userId = 1L; // Temporary
            cartService.addToCart(userId, productId, quantity);
            redirectAttributes.addFlashAttribute("success", "Product added to cart!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to add product to cart");
        }
        return "redirect:/products";
    }

    @PostMapping("/update/{itemId}")
    public String updateCartItem(@PathVariable Long itemId,
                                 @RequestParam Integer quantity,
                                 RedirectAttributes redirectAttributes) {
        try {
            cartService.updateCartItem(itemId, quantity);
            redirectAttributes.addFlashAttribute("success", "Cart updated!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to update cart");
        }
        return "redirect:/cart";
    }

    @GetMapping("/remove/{itemId}")
    public String removeFromCart(@PathVariable Long itemId,
                                 RedirectAttributes redirectAttributes) {
        try {
            cartService.removeFromCart(itemId);
            redirectAttributes.addFlashAttribute("success", "Item removed from cart!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to remove item");
        }
        return "redirect:/cart";
    }

    @GetMapping("/clear")
    public String clearCart(RedirectAttributes redirectAttributes) {
        try {
            Long userId = 1L; // Temporary
            cartService.clearCart(userId);
            redirectAttributes.addFlashAttribute("success", "Cart cleared!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to clear cart");
        }
        return "redirect:/cart";
    }
}