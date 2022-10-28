package com.example.helloproject;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellReference;

public class HelloController {
    public void initialize() {
//        String baseDirectory = System.getProperty("user.dir");
//        System.out.println(baseDirectory);
//        String excelFilePath = baseDirectory + "\\Projects.xls"; // << put this file in your project directory
//        System.out.println("Working Directory = " + baseDirectory);
//        System.out.println("excelFilePath " + excelFilePath);
//        try {
//            FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
//            Workbook wb = WorkbookFactory.create(inputStream);
//
//            Sheet xSheet = wb.getSheetAt(0);  // << gets the first sheet in the workbook
//            DataFormatter formatter = new DataFormatter();
//            int rows = xSheet.getLastRowNum();
//            System.out.println("First Row "+ xSheet.getFirstRowNum());
//            System.out.println("Number of Rows "+ rows);
//
//            for (Row row : xSheet) {
//                for (Cell cell : row) {
//                    CellReference cellRef = new CellReference(row.getRowNum(), cell.getColumnIndex());
//                    String text = formatter.formatCellValue(cell);
//                    System.out.println(cellRef.formatAsString() + " : <" + text + ">     " );
//                    switch (cell.getCellType()) {
//                        case STRING:
//                            System.out.println("STRING: " + cell.getRichStringCellValue().getString());
//                            break;
//                        case NUMERIC:
//                            if (DateUtil.isCellDateFormatted(cell)) {
//                                System.out.println("Formatted :" + cell.getDateCellValue());
//                            } else {
//                                System.out.println("UnFormatted :" + cell.getNumericCellValue());
//                            }
//                            break;
//                        case BOOLEAN:
//                            System.out.println(cell.getBooleanCellValue());
//                            break;
//                        case FORMULA:
//                            System.out.println("FORMULE :" + cell.getCellFormula());
//                            break;
//                        case BLANK:
//                            System.out.println("BLANK");
//                            break;
//                        default:
//                            System.out.println("Unknown");
//                    }
//                }
//            }
//        } catch (IOException e) {
//            // TODO: handle exception
//            System.out.println(e.toString());
//        }

        String baseDirectory = System.getProperty("user.dir");
        String excelFilePath = baseDirectory + "\\Projects.xls"; // << put this file in your project directory
//        System.out.println("Working Directory = " + baseDirectory);
//        System.out.println("excelFilePath " + excelFilePath);
        try {
            FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
            Workbook wb = WorkbookFactory.create(inputStream);

            Sheet xSheet = wb.getSheetAt(0);  // << gets the first sheet in the workbook
            DataFormatter formatter = new DataFormatter();
            int rows = xSheet.getLastRowNum();
//            System.out.println("First Row "+ xSheet.getFirstRowNum());
//            System.out.println("Number of Rows "+ rows);

            for (Row row : xSheet) {
                for (Cell cell : row) {
                    CellReference cellRef = new CellReference(row.getRowNum(), cell.getColumnIndex());
                    String text = formatter.formatCellValue(cell);
                    System.out.println(cellRef.formatAsString() + " : <" + text + ">     " );
                    switch (cell.getCellType()) {
                        case STRING:
                            System.out.println("STRING: " + cell.getRichStringCellValue().getString());
                            break;
                        case NUMERIC:
                            if (DateUtil.isCellDateFormatted(cell)) {
                                System.out.println("Formatted :" + cell.getDateCellValue());
                            } else {
                                System.out.println("UnFormatted :" + cell.getNumericCellValue());
                            }
                            break;
                        case BOOLEAN:
                            System.out.println(cell.getBooleanCellValue());
                            break;
                        case FORMULA:
                            System.out.println("FORMULE :" + cell.getCellFormula());
                            break;
                        case BLANK:
                            System.out.println("BLANK");
                            break;
                        default:
                            System.out.println("Unknown");
                    }
                }
            }
        } catch (IOException e) {
            // TODO: handle exception
            System.out.println(e.toString());
        }

    }
    @FXML
    private Label labelProjectID;

    @FXML
    private AnchorPane myAnchorPane;

    @FXML
    private TableView<?> projectsTable;

    @FXML
    private TableColumn<?, ?> tableProjectId;

    @FXML
    private TableColumn<?, ?> tableSerial;

    @FXML
    private TableColumn<?, ?> tableStage;
}
