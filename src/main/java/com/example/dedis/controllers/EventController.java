package com.example.dedis.controllers;

import com.example.dedis.dto.EventDTO;
import com.example.dedis.entities.Event;
import com.example.dedis.projections.EventProjection;
import com.example.dedis.services.EventService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/event")
public class EventController {

    private final EventService eventService;

    @GetMapping("list-events")
    public ResponseEntity<List<EventProjection>> allEvents(){
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    @GetMapping("get-event/{eventId}")
    public ResponseEntity<Event> getSpecificEvent(@PathVariable Long eventId){
        var res = eventService.getSpecificEvent(eventId);
        return res.map(event
                -> new ResponseEntity<>(event, HttpStatus.OK)).orElseGet(()
                -> new ResponseEntity<>(HttpStatus.NOT_FOUND)
        );
    }

    @SneakyThrows
    @PostMapping("create-event")
    public ResponseEntity<Event> createEvent(
            @RequestParam String payload, @RequestParam(value="image", required = false) MultipartFile[] images) {
        EventDTO eventDTO = new ObjectMapper().readValue(payload, EventDTO.class);
        return new ResponseEntity<>(eventService.createEvent(eventDTO,images),HttpStatus.CREATED);
    }

    // TODO: radi ali lose slike updejtjuje sve stavlja u prvi niz uopste ne ide u naredni. Vidji radi kli i za kreiranje isto
    @SneakyThrows
    @PutMapping("update-event/{id}")
    public ResponseEntity<Event> updateEvent(
            @PathVariable Long id,
            @RequestParam String payload,
            @RequestParam(value="image", required = false) MultipartFile[] images) {
        EventDTO eventDTO = new ObjectMapper().readValue(payload, EventDTO.class);
        return new ResponseEntity<>(eventService.updateEvent(id,eventDTO,images),HttpStatus.CREATED);
    }

    @DeleteMapping("cancel-event/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id){
        eventService.cancelEvent(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}