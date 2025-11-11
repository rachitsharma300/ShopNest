package com.shopnest.controller;

import com.shopnest.dto.ProductDTO;
import com.shopnest.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private final ProductService productService;

    public HomeController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String home(Model model) {
        try {
            // Featured products for home page
            List<ProductDTO> featuredProducts = productService.getAllProducts().subList(0, Math.min(4, productService.getAllProducts().size()));
            model.addAttribute("featuredProducts", featuredProducts);
            model.addAttribute("title", "ShopNest - Home");
            return "home";
        } catch (Exception e) {
            model.addAttribute("title", "ShopNest - Home");
            return "home";
        }
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("title", "Dashboard");
        return "admin/dashboard";
    }
}