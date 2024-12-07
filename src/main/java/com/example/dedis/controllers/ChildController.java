package com.example.dedis.controllers;

import com.example.dedis.dto.ChildDTO;
import com.example.dedis.services.ChildService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/child")
public class ChildController {

    private final ChildService childService;

    @PostMapping
    public ResponseEntity<Void> registerChild(@RequestBody ChildDTO childDTO){
        childService.registerChild(childDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}