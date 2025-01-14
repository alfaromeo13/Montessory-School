package com.example.dedis.services;

import com.example.dedis.dto.PaymentRequestDTO;
import com.example.dedis.mappers.DonationMapper;
import com.example.dedis.repositories.DonationRepository;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DonationService {

    private final DonationMapper donationMapper;
    private final DonationRepository donationRepository;

    @SneakyThrows
    public String donate(PaymentRequestDTO paymentRequestDTO) {

        PaymentIntentCreateParams params =
                PaymentIntentCreateParams.builder()
                        .setAmount(paymentRequestDTO.getAmount())
                        .setCurrency("eur")
                        .build();

        PaymentIntent intent = PaymentIntent.create(params);

        donationRepository.save(donationMapper.toEntity(paymentRequestDTO));

        log.info("Payment done successfully :)");
        return intent.getClientSecret();
    }
}