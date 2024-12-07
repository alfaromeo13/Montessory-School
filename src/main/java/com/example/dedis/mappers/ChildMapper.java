package com.example.dedis.mappers;

import com.example.dedis.dto.ChildDTO;
import com.example.dedis.entities.Child;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChildMapper {

   Child toEntity(ChildDTO childDTO);
}