package com.example.dedis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentResponseDTO {
    private String paymentIntentId;
    private String clientSecret;
}