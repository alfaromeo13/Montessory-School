package com.example.dedis.excel;

import com.example.dedis.entities.Child;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
public class ExcelGeneratorUtility {

    public void employeeDetailReport(HttpServletResponse response, List<Child> children) {

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Children export example");

            CellStyle cellStyle = workbook.createCellStyle();

            //set border to table
            cellStyle.setBorderTop(BorderStyle.MEDIUM);
            cellStyle.setBorderRight(BorderStyle.MEDIUM);
            cellStyle.setBorderBottom(BorderStyle.MEDIUM);
            cellStyle.setBorderLeft(BorderStyle.MEDIUM);
            cellStyle.setAlignment(HorizontalAlignment.LEFT);

            // Header
            Row row = sheet.createRow(0);

            String[] headers = {
                    "Id", "Parent Email", "Name", "Surname", "Date of Birth",
                    "Enrollment Year", "Grade", "Gender", "Address", "Postal Code",
                    "City", "Created At"
            };

            for (int i = 0; i < headers.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(cellStyle);
            }

            //Set data
            int rowNum = 1;
            for (Child child : children) {
                Row childDataRow = sheet.createRow(rowNum++);

                Cell cell0 = childDataRow.createCell(0);
                cell0.setCellStyle(cellStyle);
                cell0.setCellValue(child.getId());

                Cell cell1 = childDataRow.createCell(1);
                cell1.setCellStyle(cellStyle);
                cell1.setCellValue(child.getParent().getEmail());

                Cell cell2 = childDataRow.createCell(2);
                cell2.setCellStyle(cellStyle);
                cell2.setCellValue(child.getName());

                Cell cell3 = childDataRow.createCell(3);
                cell3.setCellStyle(cellStyle);
                cell3.setCellValue(child.getSurname());

                Cell cell4 = childDataRow.createCell(4);
                cell4.setCellStyle(cellStyle);
                cell4.setCellValue(child.getDateOfBirth().toString());

                Cell cell5 = childDataRow.createCell(5);
                cell5.setCellStyle(cellStyle);
                cell5.setCellValue(child.getEnrollmentYear());

                Cell cell6 = childDataRow.createCell(6);
                cell6.setCellStyle(cellStyle);
                cell6.setCellValue(child.getGrade().name());

                Cell cell7 = childDataRow.createCell(7);
                cell7.setCellStyle(cellStyle);
                cell7.setCellValue(child.getGender().name());

                Cell cell8 = childDataRow.createCell(8);
                cell8.setCellStyle(cellStyle);
                cell8.setCellValue(child.getAddress());

                Cell cell9 = childDataRow.createCell(9);
                cell9.setCellStyle(cellStyle);
                cell9.setCellValue(child.getPostalCode());

                Cell cell10 = childDataRow.createCell(10);
                cell10.setCellStyle(cellStyle);
                cell10.setCellValue(child.getCity());

                Cell cell11 = childDataRow.createCell(11);
                cell11.setCellStyle(cellStyle);
                cell11.setCellValue(child.getCreatedAt().toString());
            }
            //write output to response
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}