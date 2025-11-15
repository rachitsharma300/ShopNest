## Project Structure

```
shopnest-backend/
├── src/main/java/com/shopnest/
│   ├── config/
│   │   ├── SecurityConfig.java
│   │   ├── WebMvcConfig.java
│   │   ├── RazorpayConfig.java
│   │   └── DataSeeder.java
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
│       ├── UserNotFoundException.java
│       └── ProductNotFoundException.java
├── src/main/resources/
│   ├── templates/ 
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
│   │   │   ├── style.css
│   │   │   ├── auth.css
│   │   │   ├── admin.css
│   │   │   ├── cart.css
│   │   │   └── products.css
│   │   ├── js/
│   │   │   ├── main.js
│   │   │   ├── auth.js
│   │   │   ├── cart.js
│   │   │   ├── orders.js
│   │   │   ├── payment.js
│   │   │   └── products.js
│   │   └── images/
│   └── application.properties
└── src/test/java/com/shopnest/
    ├── service/
    │   ├── UserServiceTest.java
    │   ├── ProductServiceTest.java
    │   ├── CartServiceTest.java
    │   ├── OrderServiceTest.java
    │   └── PaymentServiceTest.java
    └── controller/
        ├── UserControllerTest.java
        ├── ProductControllerTest.java
        ├── CartControllerTest.java
        ├── OrderControllerTest.java
        └── PaymentControllerTest.java
```
