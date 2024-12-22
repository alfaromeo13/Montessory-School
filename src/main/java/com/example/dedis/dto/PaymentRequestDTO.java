package com.example.dedis.dto;

import lombok.Data;

@Data
public class PaymentRequestDTO {
    private String donorName;
    private String donorEmail;
    private Long amount;
    private String message;
}