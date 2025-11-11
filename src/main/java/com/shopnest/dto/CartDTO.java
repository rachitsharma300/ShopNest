package com.shopnest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {

    private Long id;
    private Long userId;
    private List<Object> cartItems = new ArrayList<>(); // Simple Object use karo
    private Double totalPrice;

    public Double getTotalPrice() {
        // Simple calculation
        return 0.0;
    }
}