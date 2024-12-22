package com.example.dedis.mappers;

import com.example.dedis.dto.PaymentRequestDTO;
import com.example.dedis.entities.Donation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DonationMapper {
    Donation toEntity(PaymentRequestDTO paymentRequestDTO);
}
