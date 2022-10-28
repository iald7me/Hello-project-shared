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
package com.example.helloproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellReference;

public class HelloController {
    public void initialize() {
        ObservableList<Project> list = FXCollections.observableArrayList();
        tableSerial.setCellValueFactory(new PropertyValueFactory<Project, Integer>("serial"));
        tableProjectId.setCellValueFactory(new PropertyValueFactory<Project, String>("id"));
        tableStage.setCellValueFactory(new PropertyValueFactory<Project, Integer>("stage"));


        /////////////////////////////////////////////////////////////////////////


        String baseDirectory = System.getProperty("user.dir");
        String excelFilePath = baseDirectory + "\\Projects.xls"; // << put this file in your project directory
        try {
            FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
            Workbook wb = WorkbookFactory.create(inputStream);

            Sheet xSheet = wb.getSheetAt(0);  // << gets the first sheet in the workbook
            DataFormatter formatter = new DataFormatter();
            int rows = xSheet.getLastRowNum();
//            System.out.println("First Row "+ xSheet.getFirstRowNum());
//            System.out.println("Number of Rows "+ rows);

            for (Row row : xSheet) {
                if(row.getRowNum()==0)
                    continue;
                ArrayList parameters = new ArrayList();
                parameters.add(row.getRowNum());
                for (Cell cell : row) {
                    if (cell.getColumnIndex() == 0 || cell.getColumnIndex() > 2)
                        continue;
                    CellReference cellRef = new CellReference(row.getRowNum(), cell.getColumnIndex());
                    String text = formatter.formatCellValue(cell);
//                    System.out.println(text);
                    parameters.add(text);
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
                }
                list.add(new Project(Integer.parseInt(parameters.get(0).toString()), parameters.get(1).toString(),Integer.parseInt(parameters.get(2).toString())));
            }
        } catch (IOException e) {
            // TODO: handle exception
            System.out.println(e.toString());
        }
        projectsTable.setItems(list);

    }
    @FXML
    private Label labelProjectID;

    @FXML
    private AnchorPane myAnchorPane;

    @FXML
    private TableView<Project> projectsTable;

    @FXML
    private TableColumn<Project, String> tableProjectId;

    @FXML
    private TableColumn<Project, Integer> tableSerial;

    @FXML
    private TableColumn<Project, Integer> tableStage;
}
