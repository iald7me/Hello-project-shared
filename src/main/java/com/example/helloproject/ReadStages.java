package com.example.helloproject;

import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ReadStages{

    public ArrayList<Integer> getInfo(String text) {
        String baseDirectory = System.getProperty("user.dir");
        String stagesExcelFilePath = baseDirectory + "//Stages.xls";
        DataFormatter formatter = new DataFormatter();
        ArrayList<Integer> currentStages = new ArrayList();

        try{
            FileInputStream inputStream = new FileInputStream(new File(stagesExcelFilePath));
            Workbook wb = WorkbookFactory.create(inputStream);
            Sheet stagesSheet = wb.getSheetAt(0);  // << gets the first sheet in the workbook

            for (Row sRow : stagesSheet) {
                if (sRow.getRowNum() == 0)
                    continue;
                String objId = sRow.getCell(0).getStringCellValue();
                if (objId.equals(text)) {
                    int newStage = Integer.parseInt(formatter.formatCellValue(sRow.getCell(5)));
                    currentStages.add(newStage);
                }
            }
        }catch (IOException e) {
            System.out.println(e.toString());
        }

        return currentStages;
    }
}
