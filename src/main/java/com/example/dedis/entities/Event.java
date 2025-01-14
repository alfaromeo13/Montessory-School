package com.example.dedis.entities;

import com.example.dedis.entities.converter.JpaJsonConverter;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
@Entity
public class Event implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="content_blocks", nullable = false)
    @Convert(converter = JpaJsonConverter.class)
    private Map<String, Object> content = new HashMap<>();

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "event_date", nullable = false, updatable = false)
    private LocalDateTime eventDate = LocalDateTime.now();

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}