## Project Structure

```
shopnest-backend/
├── src/main/java/com/shopnest/
│   ├── config/
│   │   ├── SecurityConfig.java
│   │   ├── WebMvcConfig.java
│   │   └── RazorpayConfig.java
│   ├── controller/
│   │   ├── HomeController.java
│   │   ├── UserController.java
│   │   ├── ProductController.java
│   │   ├── CartController.java
│   │   ├── OrderController.java
│   │   └── PaymentController.java
│   ├── entity/
│   │   ├── User.java
│   │   ├── Product.java
│   │   ├── Cart.java
│   │   ├── CartItem.java
│   │   ├── Order.java
│   │   ├── OrderItem.java
│   │   └── Payment.java
│   ├── repository/
│   │   ├── UserRepository.java
│   │   ├── ProductRepository.java
│   │   ├── CartRepository.java
│   │   ├── OrderRepository.java
│   │   └── PaymentRepository.java
│   ├── service/
│   │   ├── UserService.java
│   │   ├── ProductService.java
│   │   ├── CartService.java
│   │   ├── OrderService.java
│   │   ├── PaymentService.java
│   │   └── impl/
│   │       ├── UserServiceImpl.java
│   │       ├── ProductServiceImpl.java
│   │       ├── CartServiceImpl.java
│   │       ├── OrderServiceImpl.java
│   │       └── PaymentServiceImpl.java
│   ├── dto/
│   │   ├── UserDTO.java
│   │   ├── ProductDTO.java
│   │   ├── CartDTO.java
│   │   ├── OrderDTO.java
│   │   └── PaymentRequest.java
│   └── exception/
│       ├── GlobalExceptionHandler.java
│       └── UserNotFoundException.java
├── src/main/resources/
│   ├── templates/ (Thymeleaf - 8-10 files)
│   │   ├── home.html
│   │   ├── auth/
│   │   │   ├── login.html
│   │   │   └── register.html
│   │   ├── products/
│   │   │   ├── list.html
│   │   │   └── details.html
│   │   ├── cart/
│   │   │   └── cart.html
│   │   ├── orders/
│   │   │   ├── checkout.html
│   │   │   └── history.html
│   │   └── admin/
│   │       ├── dashboard.html
│   │       ├── products.html
│   │       └── orders.html
│   ├── static/
│   │   ├── css/
│   │   ├── js/
│   │   └── images/
│   └── application.properties
└── src/test/java/com/shopnest/
    ├── service/
    │   ├── UserServiceTest.java
    │   ├── ProductServiceTest.java
    │   ├── CartServiceTest.java
    │   └── OrderServiceTest.java
    └── controller/
        ├── UserControllerTest.java
        └── ProductControllerTest.java
```
