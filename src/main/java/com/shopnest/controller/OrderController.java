package com.shopnest.controller;

import com.shopnest.dto.OrderDTO;
import com.shopnest.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/checkout")
    public String showCheckout(Model model) {
        try {
            Long userId = 1L; // Temporary
            OrderDTO order = orderService.createOrderFromCart(userId);
            model.addAttribute("order", order);
            return "orders/checkout";
        } catch (Exception e) {
            model.addAttribute("error", "Cannot proceed to checkout: " + e.getMessage());
            return "redirect:/cart";
        }
    }

    @PostMapping("/place")
    public String placeOrder(@RequestParam String shippingAddress,
                             RedirectAttributes redirectAttributes) {
        try {
            Long userId = 1L; // Temporary
            OrderDTO order = orderService.placeOrder(userId, shippingAddress);
            redirectAttributes.addFlashAttribute("success", "Order placed successfully!");
            redirectAttributes.addAttribute("orderId", order.getId());
            return "redirect:/orders/payment";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to place order: " + e.getMessage());
            return "redirect:/cart";
        }
    }

    @GetMapping("/history")
    public String orderHistory(Model model) {
        try {
            Long userId = 1L; // Temporary
            List<OrderDTO> orders = orderService.getUserOrders(userId);
            model.addAttribute("orders", orders);
            return "orders/history";
        } catch (Exception e) {
            model.addAttribute("error", "Failed to load order history");
            return "orders/history";
        }
    }

    @GetMapping("/{orderId}")
    public String viewOrder(@PathVariable Long orderId, Model model) {
        try {
            OrderDTO order = orderService.getOrderById(orderId);
            model.addAttribute("order", order);
            return "orders/details";
        } catch (Exception e) {
            model.addAttribute("error", "Order not found");
            return "redirect:/orders/history";
        }
    }

    // Admin endpoints
    @GetMapping("/admin")
    public String adminOrderManagement(Model model) {
        List<OrderDTO> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "admin/orders";
    }

    @PostMapping("/admin/update-status")
    public String updateOrderStatus(@RequestParam Long orderId,
                                    @RequestParam String status,
                                    RedirectAttributes redirectAttributes) {
        try {
            orderService.updateOrderStatus(orderId, status);
            redirectAttributes.addFlashAttribute("success", "Order status updated!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to update order status");
        }
        return "redirect:/orders/admin";
    }
}