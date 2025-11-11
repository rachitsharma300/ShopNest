package com.shopnest.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RazorpayConfig {

    @Value("${razorpay.key.id:test_key_id}")
    private String razorpayKeyId;

    @Value("${razorpay.key.secret:test_key_secret}")
    private String razorpayKeySecret;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    // Razorpay configuration properties can be accessed via @Value
    // In real implementation, you might create a Razorpay client bean

    public String getRazorpayKeyId() {
        return razorpayKeyId;
    }

    public String getRazorpayKeySecret() {
        return razorpayKeySecret;
    }
}