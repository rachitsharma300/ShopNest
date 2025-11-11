package com.shopnest.service.impl;

import com.shopnest.entity.Order;
import com.shopnest.entity.Payment;
import com.shopnest.repository.OrderRepository;
import com.shopnest.repository.PaymentRepository;
import com.shopnest.service.PaymentService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    @Value("${razorpay.key.id:test_key}")
    private String razorpayKeyId;

    @Value("${razorpay.key.secret:test_secret}")
    private String razorpayKeySecret;

    public PaymentServiceImpl(PaymentRepository paymentRepository, OrderRepository orderRepository) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public String createRazorpayOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // Create payment record
        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setAmount(order.getTotalAmount());
        payment.setStatus(Payment.PaymentStatus.PENDING);

        Payment savedPayment = paymentRepository.save(payment);

        // For demo, return a mock Razorpay order ID
        // In real implementation, call Razorpay API
        String razorpayOrderId = "order_" + System.currentTimeMillis();
        payment.setRazorpayOrderId(razorpayOrderId);
        paymentRepository.save(payment);

        return razorpayOrderId;
    }

    @Override
    public boolean verifyPayment(String razorpayOrderId, String razorpayPaymentId, String razorpaySignature) {
        // For demo, always return true
        // In real implementation, verify Razorpay signature
        return true;
    }

    @Override
    public void updatePaymentStatus(Long orderId, String status, String razorpayPaymentId, String razorpayOrderId) {
        Payment payment = paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        payment.setStatus(Payment.PaymentStatus.valueOf(status.toUpperCase()));
        payment.setRazorpayPaymentId(razorpayPaymentId);
        payment.setRazorpayOrderId(razorpayOrderId);

        paymentRepository.save(payment);

        // Update order status if payment successful
        if ("SUCCESS".equalsIgnoreCase(status)) {
            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new RuntimeException("Order not found"));
            order.setStatus(Order.OrderStatus.CONFIRMED);
            orderRepository.save(order);
        }
    }

    @Override
    public Double getOrderAmount(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return order.getTotalAmount();
    }
}