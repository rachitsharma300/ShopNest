package com.shopnest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {

    private Long orderId;
    private Double amount;
    private String currency;
    private String receipt;

    // Razorpay response fields
    private String razorpayPaymentId;
    private String razorpayOrderId;
    private String razorpaySignature;
}