package com.example.dedis.mappers;

import com.example.dedis.dto.EventDTO;
import com.example.dedis.entities.Event;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventMapper {
    Event toEntity(EventDTO eventDTO);
}