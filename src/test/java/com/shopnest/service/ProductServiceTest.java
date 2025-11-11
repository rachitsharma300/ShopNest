package com.shopnest.service;

import com.shopnest.dto.ProductDTO;
import com.shopnest.entity.Product;
import com.shopnest.exception.ProductNotFoundException;
import com.shopnest.repository.ProductRepository;
import com.shopnest.service.impl.ProductServiceImpl;
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
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void testGetAllProducts() {
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("iPhone");
        product1.setPrice(79999.0);

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Samsung");
        product2.setPrice(69999.0);

        List<Product> products = Arrays.asList(product1, product2);

        when(productRepository.findAll()).thenReturn(products);

        List<ProductDTO> result = productService.getAllProducts();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("iPhone", result.get(0).getName());
    }

    @Test
    void testGetProductById_Success() {
        Product product = new Product();
        product.setId(1L);
        product.setName("iPhone");
        product.setPrice(79999.0);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        ProductDTO result = productService.getProductById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("iPhone", result.getName());
        assertEquals(79999.0, result.getPrice());
    }

    @Test
    void testGetProductById_NotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> {
            productService.getProductById(1L);
        });
    }

    @Test
    void testSaveProduct() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("iPhone");
        productDTO.setPrice(79999.0);
        productDTO.setStockQuantity(10);

        Product product = new Product();
        product.setId(1L);
        product.setName("iPhone");
        product.setPrice(79999.0);
        product.setStockQuantity(10);

        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductDTO result = productService.saveProduct(productDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("iPhone", result.getName());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testDeleteProduct() {
        when(productRepository.existsById(1L)).thenReturn(true);

        productService.deleteProduct(1L);

        verify(productRepository, times(1)).deleteById(1L);
    }
}