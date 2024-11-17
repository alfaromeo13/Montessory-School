package com.example.dedis.security.controller;

import com.example.dedis.excel.ExcelGeneratorUtility;
import com.example.dedis.services.ChildService;
import com.example.dedis.services.EventService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    // API which exports all kids in an Excel file.
    @SneakyThrows
    @GetMapping("excel")
    public void childrenDetailsReport(HttpServletResponse response) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd:HH:mm:ss");
        String fileType = "attachment; filename=children_details_" + dateFormat.format(new Date()) + ".xls";
        response.setHeader("Content-Disposition", fileType);
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM.getType());
        ExcelGeneratorUtility.employeeDetailReport(response, childService.findAll());
    }
}