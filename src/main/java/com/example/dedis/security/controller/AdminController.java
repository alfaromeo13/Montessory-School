package com.example.dedis.security.controller;

import com.example.dedis.services.ChildService;
import com.example.dedis.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final EventService eventService;
    private final ChildService childService;

    /*TODO: add events (blogs)
       send email to parents
       save content + save images on disk
    */

    /*TODO: edit events (PUT verb)
       overwrite existing one with specific id and content body
     */

    //TODO: export all kids data into Excel report

}