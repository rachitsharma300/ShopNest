package com.shopnest.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public String handleUserNotFoundException(UserNotFoundException ex,
                                              RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", ex.getMessage());
        return "redirect:/auth/login";
    }

    @ExceptionHandler(Exception.class)
    public String handleGenericException(Exception ex,
                                         RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", "Something went wrong. Please try again.");
        return "redirect:/";
    }
}