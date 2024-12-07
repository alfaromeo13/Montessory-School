package com.example.dedis.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class PaymentRequestDTO {
    @NotNull
    @Min(4)
    private Long amount;

    @Email
    private String email;

    @NotBlank
    @Size(min = 5, max = 200)
    private String productName;
}