package com.example.dedis.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class EventDTO {
    private Long id;
    private Map<String, Object> content = new HashMap<>();
}