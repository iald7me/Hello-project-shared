package com.example.helloproject;

import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

public class ReadStagesDetails{


    public ArrayList<ProjectEvent> getInfo(String text, ArrayList<Integer> currentStages) {

        String baseDirectory = System.getProperty("user.dir");
        String dStagesExcelFilePath = baseDirectory + "//Stages_Detailed.xls";
        ArrayList<ProjectEvent> events = new ArrayList();

        DataFormatter formatter = new DataFormatter();
        try{
            FileInputStream inputStream = new FileInputStream(new File(dStagesExcelFilePath));
            Workbook wb = WorkbookFactory.create(inputStream);
            Sheet dStagesSheet = wb.getSheetAt(0);  // << gets the first sheet in the workbook

            ArrayList<LocalDateTime> currentStagesDates = new ArrayList();
            for (Row dRow : dStagesSheet) {
                if (dRow.getRowNum() == 0)
                    continue;
                String objId = dRow.getCell(0).getStringCellValue();
                if (objId.equals(text)) {
                    LocalDateTime newDate = dRow.getCell(2).getLocalDateTimeCellValue();

                    // ****** All of this code is because there are some data entry issues ******
                    String timeIn12 = formatter.formatCellValue(dRow.getCell(3));
                    if (timeIn12.length() == 10)
                        timeIn12 = "0" + timeIn12;
                    DateTimeFormatter formatterInput = DateTimeFormatter.ofPattern("hh:mm:ss a" , Locale.US)  ;
                    LocalTime inputTime = LocalTime.parse(timeIn12 , formatterInput);
                    DateTimeFormatter formatterOutput = DateTimeFormatter.ofPattern("HH:mm:ss" ,Locale.US);
                    String timeIn24 = inputTime.format(formatterOutput);
                    int hourIn24 = Integer.parseInt(timeIn24.substring(0,2));
                    int minute = Integer.parseInt(timeIn24.substring(3,5));
                    int second = Integer.parseInt(timeIn24.substring(6,8));
                    newDate = newDate.withHour(hourIn24).withMinute(minute).withSecond(second);
                    currentStagesDates.add(newDate);
                }
            }
            // Adds all the data in stages_details to an ArrayList<ProjectEvent>
            for (int i = 0; i < currentStages.size(); i++) {
                events.add(new ProjectEvent(currentStagesDates.get(i), currentStages.get(i)));
            }
        }catch (IOException e) {
            System.out.println(e.toString());
        }


        return events;
    }

}
