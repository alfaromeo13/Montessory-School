package com.example.dedis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class DedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(DedisApplication.class, args);
    }

}
