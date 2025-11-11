package com.shopnest.dto;

import com.shopnest.entity.Order;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    private Long id;
    private Long userId;
    private String userEmail;
    private String userName;

    @NotNull(message = "Total amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Total amount must be greater than 0")
    private Double totalAmount;

    private String status;

    @NotBlank(message = "Shipping address is required")
    private String shippingAddress;

//    private List<OrderItemDTO> orderItems = new ArrayList<>();
//    private PaymentDTO payment;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructor for basic order info
    public OrderDTO(Long id, Double totalAmount, String status, LocalDateTime createdAt) {
        this.id = id;
        this.totalAmount = totalAmount;
        this.status = status;
        this.createdAt = createdAt;
    }
}