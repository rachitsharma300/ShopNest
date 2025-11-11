package com.shopnest.service;

import com.shopnest.dto.CartDTO;
import com.shopnest.dto.OrderDTO;
import com.shopnest.entity.Order;
import com.shopnest.entity.User;
import com.shopnest.repository.OrderRepository;
import com.shopnest.repository.UserRepository;
import com.shopnest.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CartService cartService;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    void testPlaceOrder_Success() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setFullName("Test User");

        CartDTO cart = new CartDTO();
        cart.setTotalPrice(1500.0);

        Order order = new Order();
        order.setId(1L);
        order.setUser(user);
        order.setTotalAmount(1500.0);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(cartService.getCartByUserId(1L)).thenReturn(cart);
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        OrderDTO result = orderService.placeOrder(1L, "Test Address");

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(1500.0, result.getTotalAmount());
        assertEquals("Test Address", result.getShippingAddress());

        verify(cartService, times(1)).clearCart(1L);
    }

    @Test
    void testGetUserOrders() {
        User user = new User();
        user.setId(1L);

        Order order1 = new Order();
        order1.setId(1L);
        order1.setUser(user);

        Order order2 = new Order();
        order2.setId(2L);
        order2.setUser(user);

        List<Order> orders = Arrays.asList(order1, order2);

        when(orderRepository.findByUserIdOrderByCreatedAtDesc(1L)).thenReturn(orders);

        List<OrderDTO> result = orderService.getUserOrders(1L);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void testGetOrderById() {
        User user = new User();
        user.setId(1L);

        Order order = new Order();
        order.setId(1L);
        order.setUser(user);
        order.setTotalAmount(1500.0);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        OrderDTO result = orderService.getOrderById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(1500.0, result.getTotalAmount());
    }
}