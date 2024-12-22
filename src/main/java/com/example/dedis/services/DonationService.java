package com.example.dedis.services;

import com.example.dedis.mappers.DonationMapper;
import com.example.dedis.repositories.DonationRepository;
import com.stripe.model.Charge;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class DonationService {

    private final DonationMapper donationMapper;
    private final DonationRepository donationRepository;

    public Charge chargeCreditCard(String token, double amount) throws Exception {
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", (int)(amount * 100)); // stripe expects amount in cents.
        chargeParams.put("currency", "EUR");
        chargeParams.put("source", token);
        //  donationRepository.save(donationMapper.toEntity(request));
        log.info("DONE! :D");
        return Charge.create(chargeParams);
    }

}