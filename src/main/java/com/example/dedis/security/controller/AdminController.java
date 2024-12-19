package com.example.dedis.security.controller;

import com.example.dedis.dto.EventDTO;
import com.example.dedis.entities.Event;
import com.example.dedis.excel.ExcelGeneratorUtility;
import com.example.dedis.projections.EventProjection;
import com.example.dedis.security.dto.LoginDTO;
import com.example.dedis.security.jwt.JwtTokenProvider;
import com.example.dedis.services.AdminService;
import com.example.dedis.services.ChildService;
import com.example.dedis.services.EventService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final EventService eventService;
    private final AdminService adminService;
    private final ChildService childService;
    private final JwtTokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;

    // TODO:
    //  test all methods +
    //  check if caching works
    //  check if mail is sent properly
    //  password renew for admin!

    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody LoginDTO login) {
        try {
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    login.getUsername(),
                    login.getPassword()
            );

            // this does all background logic for us. Checks users password with BCrypt
            Authentication auth = authenticationManager.authenticate(authentication);
            log.info("Authentication after successful login: {}", auth);
            String JWT = tokenProvider.generateToken(auth);
            return ResponseEntity.ok(JWT);
        } catch (Exception e) {
            log.error("Error occurred on login. Message: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("list-events") // TODO: vrati sa jos jednom slikom dodatnom za cover CARD elementa u angular
    public ResponseEntity<List<EventProjection>> getEventName(){
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    @GetMapping("get-event/{eventId}")
    public ResponseEntity<Event> getSpecificEvent(@PathVariable Long eventId){
        var res = eventService.getSpecificEvent(eventId);
        return res.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @SneakyThrows
    @PostMapping("create-event")
    public ResponseEntity<Event> createEvent(
            @RequestParam String payload, @RequestParam(value="image", required = false) MultipartFile[] images) {
        EventDTO eventDTO = new ObjectMapper().readValue(payload, EventDTO.class);
        return new ResponseEntity<>(eventService.createEvent(eventDTO,images),HttpStatus.CREATED);
    }

    @SneakyThrows
    @PostMapping("update-event/{id}")
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

    @SneakyThrows
    @GetMapping("excel") // API which exports all kids data in an Excel file.
    public void childrenDetailsReport(HttpServletResponse response) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd:HH:mm:ss");
        String fileType = "attachment; filename=children_details_" + dateFormat.format(new Date()) + ".xls";
        response.setHeader("Content-Disposition", fileType);
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM.getType());
        ExcelGeneratorUtility.employeeDetailReport(response, childService.findAll());
    }
}