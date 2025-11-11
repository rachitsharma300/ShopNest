package com.shopnest.controller;

import com.shopnest.dto.ProductDTO;
import com.shopnest.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String getAllProducts(Model model) {
        try {
            List<ProductDTO> products = productService.getAllProducts();
            model.addAttribute("products", products);
            model.addAttribute("title", "Products - ShopNest");
            return "products";
        } catch (Exception e) {
            model.addAttribute("error", "Failed to load products");
            return "products";
        }
    }

    @GetMapping("/{id}")
    public String getProductById(@PathVariable Long id, Model model) {
        try {
            ProductDTO product = productService.getProductById(id);
            model.addAttribute("product", product);
            model.addAttribute("title", product.getName() + " - ShopNest");
            return "product-details";
        } catch (Exception e) {
            model.addAttribute("error", "Product not found");
            return "redirect:/products";
        }
    }

    // Admin product management
    @GetMapping("/admin")
    public String adminProducts(Model model) {
        List<ProductDTO> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "admin/products";
    }

    @GetMapping("/admin/new")
    public String showAddProductForm(Model model) {
        model.addAttribute("productDTO", new ProductDTO());
        return "admin/add-product";
    }

    @PostMapping("/admin/save")
    public String saveProduct(@ModelAttribute ProductDTO productDTO, Model model) {
        try {
            productService.saveProduct(productDTO);
            model.addAttribute("success", "Product added successfully!");
            return "redirect:/products/admin";
        } catch (Exception e) {
            model.addAttribute("error", "Failed to add product: " + e.getMessage());
            return "admin/add-product";
        }
    }
}