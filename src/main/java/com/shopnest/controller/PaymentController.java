package com.shopnest.controller;

import com.shopnest.service.PaymentService;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping
    public String showPaymentPage(@RequestParam Long orderId, Model model) {
        try {
            String razorpayOrderId = paymentService.createRazorpayOrder(orderId);
            model.addAttribute("orderId", orderId);
            model.addAttribute("razorpayOrderId", razorpayOrderId);
            model.addAttribute("amount", paymentService.getOrderAmount(orderId));
            return "orders/payment";
        } catch (Exception e) {
            model.addAttribute("error", "Payment initialization failed");
            return "redirect:/orders/checkout";
        }
    }

    @PostMapping("/verify")
    public String verifyPayment(@RequestParam String razorpay_payment_id,
                                @RequestParam String razorpay_order_id,
                                @RequestParam String razorpay_signature,
                                @RequestParam Long orderId,
                                RedirectAttributes redirectAttributes) {
        try {
            boolean isValid = paymentService.verifyPayment(razorpay_order_id,
                    razorpay_payment_id,
                    razorpay_signature);

            if (isValid) {
                paymentService.updatePaymentStatus(orderId, "SUCCESS",
                        razorpay_payment_id, razorpay_order_id);
                redirectAttributes.addFlashAttribute("success", "Payment successful! Order confirmed.");
                return "redirect:/orders/history";
            } else {
                paymentService.updatePaymentStatus(orderId, "FAILED", null, null);
                redirectAttributes.addFlashAttribute("error", "Payment verification failed");
                return "redirect:/orders/checkout";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Payment processing error");
            return "redirect:/orders/checkout";
        }
    }

    @GetMapping("/success")
    public String paymentSuccess() {
        return "orders/success";
    }

    @GetMapping("/failure")
    public String paymentFailure() {
        return "orders/failure";
    }
}