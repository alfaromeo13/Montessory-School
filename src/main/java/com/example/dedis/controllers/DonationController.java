package com.example.dedis.controllers;


import com.example.dedis.services.DonationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/donation")
public class DonationController {

    private final DonationService donationService;


    @GetMapping
    String h(){
        return "hahahaha LDD";
    }
    //TODO: donate API
}
