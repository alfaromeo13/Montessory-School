package com.example.dedis.security.controller;

import com.example.dedis.excel.ExcelGeneratorUtility;
import com.example.dedis.security.dto.LoginDTO;
import com.example.dedis.services.AdminService;
import com.example.dedis.services.ChildService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    @Value("${application.admin.email}")
    private String ADMIN_EMAIL;

    private final AdminService adminService;
    private final ChildService childService;
    private final ExcelGeneratorUtility excelGeneratorUtility;

    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO){
        return ResponseEntity.ok(adminService.login(loginDTO));
    }

    @PostMapping("/request-reset-password")
    public ResponseEntity<String> requestResetPassword(){
        adminService.sendResetMail(ADMIN_EMAIL); //TODO finish in service ...
        return ResponseEntity.ok("Reset mail sent");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String code, @RequestParam String password){
        if (adminService.changePassword(code, password))
            return ResponseEntity.ok("Password changed successfully!");
        else return new ResponseEntity<>("Account not found", HttpStatus.NOT_FOUND);
    }

    @SneakyThrows
    @GetMapping("excel") // API which exports all kids data in an Excel file.
    public void childrenDetailsReport(HttpServletResponse response) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd:HH:mm:ss");
        String fileType = "attachment; filename=children_details_" + dateFormat.format(new Date()) + ".xls";
        response.setHeader("Content-Disposition", fileType);
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM.getType());
        excelGeneratorUtility.employeeDetailReport(response, childService.findAll());
    }
}