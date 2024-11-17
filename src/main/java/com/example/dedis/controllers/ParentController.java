package com.example.dedis.controllers;

import com.example.dedis.services.ChildService;
import com.example.dedis.services.ParentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/parent")
public class ParentController {

    private final ParentService parentService;
    private final ChildService childService;

    // TODO: register kid API + send email of successful registration
}
