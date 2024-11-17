package com.example.dedis.services;

import com.example.dedis.entities.Child;
import com.example.dedis.repositories.ChildRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChildService {

    private final ChildRepository childRepository;

    //TODO: map to DTO?
    public List<Child> findAll() {
        return childRepository.findAll();
    }
}