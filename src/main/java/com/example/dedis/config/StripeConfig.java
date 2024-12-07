package com.example.dedis.config;

import com.stripe.Stripe;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

@Configuration
@Description("Stripe Integration configuration")
public class StripeConfig {

    @Value("${stripe.api.secretKey}")
    private String secretKey;

    @PostConstruct
    //This annotation is used on a method that needs to be executed after
    //dependency injection is done to perform any initialization.
    public void  initSecretKey(){
        Stripe.apiKey = secretKey;
    }
}
