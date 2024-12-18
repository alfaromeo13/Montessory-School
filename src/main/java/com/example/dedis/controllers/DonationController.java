package com.example.dedis.controllers;


import com.example.dedis.dto.PaymentRequestDTO;
import com.example.dedis.dto.PaymentResponseDTO;
import com.example.dedis.services.DonationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/donation")
public class DonationController {

    private final DonationService donationService;

    //TODO: test donate API
    @PostMapping("/create-payment-intent")
    public PaymentResponseDTO createPaymentIntent(@RequestBody PaymentRequestDTO request) {
        return donationService.createPaymentIntent(request);
    }
}