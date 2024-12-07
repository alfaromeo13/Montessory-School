package com.example.dedis.security.controller;

import com.example.dedis.excel.ExcelGeneratorUtility;
import com.example.dedis.security.dto.LoginDTO;
import com.example.dedis.services.AdminService;
import com.example.dedis.services.ChildService;
import com.example.dedis.services.EventService;
import jakarta.servlet.http.HttpServletRequest;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final EventService eventService;
    private final AdminService adminService;
    private final ChildService childService;

    private final AuthenticationManager authenticationManager;

    /*TODO: add events (blogs)
       send email to parents
       save content + save images on disk
    */

    /*TODO: edit events (PUT verb)
       overwrite existing one with specific id and content body
     */



    @SneakyThrows
    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginDTO login) {
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    login.getUsername(),
                    login.getPassword()
            );
            // this does all background logic for us.Checks users password with BCrypt
            Authentication auth = authenticationManager.authenticate(authentication);
            log.info("Authentication after successful login: {}", auth);
            return new ResponseEntity<>(HttpStatus.OK);
    }

    @SneakyThrows
    @PostMapping("logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        SecurityContextHolder.clearContext(); // Clear security context
        request.getSession().invalidate();
        request.getSession(true); // Create a new session to avoid reuse of the old one
        log.info("User logged out successfully.");
        return new ResponseEntity<>("Logged out successfully", HttpStatus.OK);
    }

    @SneakyThrows
    @GetMapping("excel") // API which exports all kids in an Excel file.
    public void childrenDetailsReport(HttpServletResponse response) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd:HH:mm:ss");
        String fileType = "attachment; filename=children_details_" + dateFormat.format(new Date()) + ".xls";
        response.setHeader("Content-Disposition", fileType);
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM.getType());
        ExcelGeneratorUtility.employeeDetailReport(response, childService.findAll());
    }
}