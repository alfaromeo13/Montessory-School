package com.example.dedis.excel;

import com.example.dedis.entities.Child;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.util.List;

@Slf4j
public class ExcelGeneratorUtility {

    public static void employeeDetailReport(HttpServletResponse response, List<Child> children) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Employee TechGeekNext Example");

            CellStyle cellStyle = workbook.createCellStyle();

            //set border to table
            cellStyle.setBorderTop(BorderStyle.MEDIUM);
            cellStyle.setBorderRight(BorderStyle.MEDIUM);
            cellStyle.setBorderBottom(BorderStyle.MEDIUM);
            cellStyle.setBorderLeft(BorderStyle.MEDIUM);
            cellStyle.setAlignment(HorizontalAlignment.LEFT);

            // Header
            Row row = sheet.createRow(0);
            Cell cell = row.createCell(0);
            cell.setCellValue("Id");
            cell.setCellStyle(cellStyle);

            Cell cell1 = row.createCell(1);
            cell1.setCellValue("Name");
            cell1.setCellStyle(cellStyle);

            Cell cell2 = row.createCell(2);
            cell2.setCellValue("Role");
            cell2.setCellStyle(cellStyle);

            //Set data
            int rowNum = 1;
            for (Child child : children) {
                Row empDataRow = sheet.createRow(rowNum++);
                Cell empIdCell = empDataRow.createCell(0);
                empIdCell.setCellStyle(cellStyle);
                empIdCell.setCellValue(child.getId());

                Cell empNameCell = empDataRow.createCell(1);
                empNameCell.setCellStyle(cellStyle);
                empNameCell.setCellValue(child.getName());

                // TODO: finish to the end...
            }

            //write output to response
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}