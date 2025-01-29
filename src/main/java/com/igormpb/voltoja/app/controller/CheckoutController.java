package com.igormpb.voltoja.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/checkout")
public class CheckoutController {

    @PostMapping
    public ResponseEntity Checkout() {
        return ResponseEntity.ok(null);
    }

}
