package com.example.dedis.entities;

import com.example.dedis.entities.converter.JpaJsonConverter;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="content_blocks", nullable = false)
    @Convert(converter = JpaJsonConverter.class)
    private Map<String, Object> content = new HashMap<>();

    @Column(name = "event_date", nullable = false, updatable = false)
    private LocalDateTime eventDate = LocalDateTime.now();

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}