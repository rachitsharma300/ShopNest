package com.shopnest.service;

public interface PaymentService {
    String createRazorpayOrder(Long orderId);
    boolean verifyPayment(String razorpayOrderId, String razorpayPaymentId, String razorpaySignature);
    void updatePaymentStatus(Long orderId, String status, String razorpayPaymentId, String razorpayOrderId);
    Double getOrderAmount(Long orderId);
}