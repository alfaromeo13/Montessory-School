package com.example.dedis.security.dto;

import lombok.Data;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Getter
@Service
public class LoginDTO {
    private String username;
    private String password;
}