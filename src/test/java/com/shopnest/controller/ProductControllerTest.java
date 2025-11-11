package com.shopnest.controller;

import com.shopnest.dto.ProductDTO;
import com.shopnest.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    void testGetAllProducts() throws Exception {
        ProductDTO product1 = new ProductDTO(1L, "iPhone", "Smartphone", 79999.0, "image1.jpg", 10, "Electronics");
        ProductDTO product2 = new ProductDTO(2L, "Samsung", "Android Phone", 69999.0, "image2.jpg", 15, "Electronics");
        List<ProductDTO> products = Arrays.asList(product1, product2);

        when(productService.getAllProducts()).thenReturn(products);

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(view().name("products/list"))
                .andExpect(model().attributeExists("products"));
    }

    @Test
    void testGetProductById() throws Exception {
        ProductDTO product = new ProductDTO(1L, "iPhone", "Smartphone", 79999.0, "image1.jpg", 10, "Electronics");

        when(productService.getProductById(1L)).thenReturn(product);

        mockMvc.perform(get("/products/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("products/details"))
                .andExpect(model().attributeExists("product"));
    }

    @Test
    void testSearchProducts() throws Exception {
        ProductDTO product = new ProductDTO(1L, "iPhone", "Smartphone", 79999.0, "image1.jpg", 10, "Electronics");
        List<ProductDTO> products = Arrays.asList(product);

        when(productService.searchProducts("iphone")).thenReturn(products);

        mockMvc.perform(get("/products/search")
                        .param("query", "iphone"))
                .andExpect(status().isOk())
                .andExpect(view().name("products/list"))
                .andExpect(model().attributeExists("products"))
                .andExpect(model().attributeExists("searchQuery"));
    }
}