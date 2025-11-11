package com.shopnest.service;

import com.shopnest.dto.ProductDTO;
import java.util.List;

public interface ProductService {
    List<ProductDTO> getAllProducts();
    ProductDTO getProductById(Long productId);
    List<ProductDTO> searchProducts(String query);
    ProductDTO saveProduct(ProductDTO productDTO);
    ProductDTO updateProduct(Long productId, ProductDTO productDTO);
    void deleteProduct(Long productId);
}