package com.example.dedis.dto;

import lombok.Data;

@Data
public class PasswordResetDTO {
    private String code;
    private String password;
}
