package com.example.dedis.services;

import com.example.dedis.dto.PaymentRequestDTO;
import com.example.dedis.dto.PaymentResponseDTO;
import com.example.dedis.repositories.DonationRepository;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DonationService {

    private final DonationRepository donationRepository;

    @SneakyThrows
    public PaymentResponseDTO createPaymentIntent(PaymentRequestDTO request) {
        PaymentIntentCreateParams params =
                PaymentIntentCreateParams.builder()
                        .setAmount(request.getAmount() * 100L)
                        .putMetadata("productName", request.getProductName())
                        .setCurrency("usd")
                        .setAutomaticPaymentMethods(
                                PaymentIntentCreateParams
                                        .AutomaticPaymentMethods
                                        .builder()
                                        .setEnabled(true)
                                        .build()
                        ).build();
        PaymentIntent intent = PaymentIntent.create(params);
        return new PaymentResponseDTO(intent.getId(), intent.getClientSecret());
    }
}