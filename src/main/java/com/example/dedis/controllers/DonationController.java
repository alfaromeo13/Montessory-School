package com.example.dedis.controllers;

import com.example.dedis.services.DonationService;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/donation")
public class DonationController {

    private final DonationService donationService;

    @SneakyThrows
    @PostMapping("/create-payment-intent")
    public ResponseEntity<Map<String,String>> createPaymentIntent(@RequestBody Map<String, Object> data) {
            PaymentIntentCreateParams params =
                    PaymentIntentCreateParams.builder()
                            .setAmount(((Number) data.get("amount")).longValue())
                            .setCurrency("usd")
                            .build();

            PaymentIntent intent = PaymentIntent.create(params);
            //todo: finish database storing

            log.info("Payment done successfully :)");
            return ResponseEntity.ok(Collections.singletonMap("clientSecret", intent.getClientSecret()));
    }
}