package com.shopnest.config;

import com.shopnest.entity.User;
import com.shopnest.entity.Product;
import com.shopnest.repository.UserRepository;
import com.shopnest.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(UserRepository userRepository,
                      ProductRepository productRepository,
                      PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Override
    public void run(String... args) throws Exception {
        // Clear existing data
        userRepository.deleteAll();
        productRepository.deleteAll();


        String adminPassword = "admin";
        String encodedAdminPassword = passwordEncoder.encode(adminPassword);
        System.out.println("🔐 Admin Password: " + adminPassword);
        System.out.println("🔐 Encoded Admin Password: " + encodedAdminPassword);
        System.out.println("🔐 Password Match: " + passwordEncoder.matches(adminPassword, encodedAdminPassword));

        // Create Admin User
        User admin = new User();
        admin.setEmail("admin@shopnest.com");
        admin.setPassword(passwordEncoder.encode("admin")); // BCrypt encoded
        admin.setFullName("Admin User");
        admin.setRole(User.Role.ADMIN);
        userRepository.save(admin);

        // Create Customer User
        User customer = new User();
        customer.setEmail("customer@shopnest.com");
        customer.setPassword(passwordEncoder.encode("password")); // BCrypt encoded
        customer.setFullName("Test Customer");
        customer.setRole(User.Role.CUSTOMER);
        userRepository.save(customer);

        // Create Sample Products
        Product product1 = new Product();
        product1.setName("iPhone 14");
        product1.setDescription("Latest Apple smartphone");
        product1.setPrice(79999.00);
        product1.setImageUrl("/images/iphone14.jpg");
        product1.setStockQuantity(50);
        product1.setCategory("Electronics");
        productRepository.save(product1);

        Product product2 = new Product();
        product2.setName("Samsung Galaxy S23");
        product2.setDescription("Android flagship phone");
        product2.setPrice(69999.00);
        product2.setImageUrl("/images/galaxy-s23.jpg");
        product2.setStockQuantity(30);
        product2.setCategory("Electronics");
        productRepository.save(product2);

        Product product3 = new Product();
        product3.setName("Nike Air Max");
        product3.setDescription("Comfortable sports shoes");
        product3.setPrice(5999.00);
        product3.setImageUrl("/images/nike-airmax.jpg");
        product3.setStockQuantity(100);
        product3.setCategory("Fashion");
        productRepository.save(product3);

        Product product4 = new Product();
        product4.setName("MacBook Pro");
        product4.setDescription("Apple laptop for professionals");
        product4.setPrice(199999.00);
        product4.setImageUrl("/images/macbook-pro.jpg");
        product4.setStockQuantity(20);
        product4.setCategory("Electronics");
        productRepository.save(product4);

        System.out.println("✅ Data seeded successfully!");
        System.out.println("👤 Admin: admin@shopnest.com / admin");
        System.out.println("👤 Customer: customer@shopnest.com / password");
    }
}