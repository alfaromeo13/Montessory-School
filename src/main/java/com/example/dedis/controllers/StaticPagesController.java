package com.example.dedis.controllers;

import com.example.dedis.services.StaticPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/content")
public class StaticPagesController {

    private final StaticPageService staticPageService;

    //TODO: retrieve content API. Dodaj Kesiranje contenta
    // DODAJ DA KADA ADMINISTRATOR KREIRA EVENT NEKI da se eviktuje sve? Znaci moras ispratiti kad ces raditi evict

}
