package com.shopnest.service;

import com.shopnest.dto.OrderDTO;
import java.util.List;

public interface OrderService {
    OrderDTO createOrderFromCart(Long userId);
    OrderDTO placeOrder(Long userId, String shippingAddress);
    OrderDTO getOrderById(Long orderId);
    List<OrderDTO> getUserOrders(Long userId);
    List<OrderDTO> getAllOrders();
    void updateOrderStatus(Long orderId, String status);
}