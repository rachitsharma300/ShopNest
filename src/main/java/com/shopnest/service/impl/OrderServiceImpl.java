package com.shopnest.service.impl;

import com.shopnest.dto.OrderDTO;
import com.shopnest.dto.CartDTO;
import com.shopnest.entity.*;
import com.shopnest.exception.UserNotFoundException;
import com.shopnest.repository.OrderRepository;
import com.shopnest.repository.UserRepository;
import com.shopnest.service.CartService;
import com.shopnest.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartService cartService;

    public OrderServiceImpl(OrderRepository orderRepository,
                            UserRepository userRepository,
                            CartService cartService) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.cartService = cartService;
    }

    @Override
    public OrderDTO createOrderFromCart(Long userId) {
        // This will create an order from cart items
        // For now, return a basic order DTO
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        Order order = new Order();
        order.setUser(user);
        order.setTotalAmount(0.0); // Calculate from cart
        order.setShippingAddress("Address will be added during checkout");

        Order savedOrder = orderRepository.save(order);
        return convertToDTO(savedOrder);
    }

    @Override
    public OrderDTO placeOrder(Long userId, String shippingAddress) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        // Get cart and calculate total
        CartDTO cart = cartService.getCartByUserId(userId);

        Order order = new Order();
        order.setUser(user);
        order.setTotalAmount(cart.getTotalPrice());
        order.setShippingAddress(shippingAddress);
        order.setStatus(Order.OrderStatus.CONFIRMED);

        Order savedOrder = orderRepository.save(order);

        // Clear cart after order
        cartService.clearCart(userId);

        return convertToDTO(savedOrder);
    }

    @Override
    public OrderDTO getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return convertToDTO(order);
    }

    @Override
    public List<OrderDTO> getUserOrders(Long userId) {
        return orderRepository.findByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(Order.OrderStatus.valueOf(status.toUpperCase()));
        orderRepository.save(order);
    }

    private OrderDTO convertToDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setUserId(order.getUser().getId());
        orderDTO.setUserEmail(order.getUser().getEmail());
        orderDTO.setUserName(order.getUser().getFullName());
        orderDTO.setTotalAmount(order.getTotalAmount());
        orderDTO.setStatus(order.getStatus().toString());
        orderDTO.setShippingAddress(order.getShippingAddress());
        orderDTO.setCreatedAt(order.getCreatedAt());
        orderDTO.setUpdatedAt(order.getUpdatedAt());
        return orderDTO;
    }
}