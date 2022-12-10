
package com.example.helloproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Locale;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.TemporalAdjusters.firstDayOfMonth;
import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;
import static javafx.scene.paint.Color.BLUE;
import static javafx.scene.paint.Color.RED;

public class HelloController {
    public void initialize() {

        System.out.println("READING EXCEL SHEETS. PLEASE WAIT...");
        System.out.println("# NOTE: IGNORE WARNINGS AND 'StatusLogger' ERROR!");

        // Initinalizing a list of projects retrieved from the excel sheet
        ObservableList<Project> projectList = FXCollections.observableArrayList();

        // Setting the table labels for GUI
        tableSerial.setCellValueFactory(new PropertyValueFactory<Project, Integer>("serial"));
        tableProjectId.setCellValueFactory(new PropertyValueFactory<Project, String>("id"));
        tableStage.setCellValueFactory(new PropertyValueFactory<Project, Integer>("stage"));

        // getting user's base directory to access the excel files.
        String baseDirectory = System.getProperty("user.dir");
        String projectsExcelFilePath = baseDirectory + "//Projects.xls";

        try {
            //Opens the Project.xls file and loop thru its content
            FileInputStream inputStream = new FileInputStream(new File(projectsExcelFilePath));
            Workbook wb = WorkbookFactory.create(inputStream);
            Sheet xSheet = wb.getSheetAt(0);  // << gets the first sheet in the workbook
            DataFormatter formatter = new DataFormatter();

            for (Row row : xSheet) {
                if(row.getRowNum()==0)
                    continue;

                //Initializaion of ArrayLists
                ArrayList parameters = new ArrayList();
                ArrayList<ProjectEvent> events = new ArrayList();

                //Adds the serial number for each project
                parameters.add(row.getRowNum());

                for (Cell cell : row) {
                    if (cell.getColumnIndex() > 2)
                        continue;
                    String text = formatter.formatCellValue(cell);
                    if (cell.getColumnIndex() != 0)
                        parameters.add(text);
                    else {

                        // Opens Stages.xls file and get all the necessary information
                        ReadStages stages = new ReadStages();
                        ArrayList<Integer> currentStages = stages.getInfo(text);

                        // Opens Stages_Detailed.xls file and get all the necessary information
                        ReadStagesDetails readStagesDetails = new ReadStagesDetails();
                        events = readStagesDetails.getInfo(text,currentStages);
                    }
                }
                //Sorts all the events compared to their date
                events.sort(Comparator.comparing(ProjectEvent::getDate));

                // At the end of loop, we create a new project object and append it to arraylist of projects
                projectList.add(new Project(
                        // Serial number
                        Integer.parseInt(parameters.get(0).toString()),
                        // Project ID
                        parameters.get(1).toString(),
                        // Stage Number
                        Integer.parseInt(parameters.get(2).toString()),
                        //an ArrayList of each project event detail
                        events));
            }
            System.out.println("READING COMPLETE!");

        } catch (IOException e) {
            System.out.println(e.toString());
        }

        projectsTable.setItems(projectList);

    }
    @FXML
    private AnchorPane anchorPaneTL;

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
    private final double TIMELINE_LENGTH = 1335.0;
    @FXML
    void onClickProject(MouseEvent event) {

        anchorPaneTL.getChildren().clear();

        // Storing the selected project
        Project selectedProject = projectsTable.getSelectionModel().getSelectedItem();
        labelProjectID.setText(selectedProject.getId());

        //Gets all stages/events related to the selected project
        ArrayList<ProjectEvent> currentEvents = selectedProject.getEvents();

        // getting the StartDate and EndDate from the selected project
        LocalDate timeLineStartDate = currentEvents.get(0).getDate().toLocalDate().with(firstDayOfMonth());
        LocalDate timeLineEndDate = currentEvents.get(currentEvents.size()-1).getDate().toLocalDate().with(lastDayOfMonth());


        int timelineDays = (int) DAYS.between(timeLineStartDate,timeLineEndDate) + 1;
        double incrementRatio = TIMELINE_LENGTH/timelineDays;
        Line mainLine = new Line(0,0,TIMELINE_LENGTH,0);
        mainLine.setLayoutX(10);
        mainLine.setLayoutY(300);
        anchorPaneTL.getChildren().add(mainLine);
        LocalDate firstStageDate = currentEvents.get(0).getDate().toLocalDate();
        LocalDate lastStageDate = currentEvents.get(currentEvents.size()-1).getDate().toLocalDate();
        int durationDays = (int) DAYS.between(firstStageDate,lastStageDate);
        int timelineStartToStagesStart = (int) DAYS.between(timeLineStartDate,firstStageDate);
        Line durationLine = new Line(0,0,durationDays * incrementRatio,0);
        durationLine.setLayoutX(10 + incrementRatio * timelineStartToStagesStart);
        durationLine.setLayoutY(100);
        durationLine.setStroke(RED);
        durationLine.setStrokeWidth(2);
        anchorPaneTL.getChildren().add(durationLine);
        Label durationLabel = new Label("Duration: " + durationDays + " days.");
        double durationLabelXLayout = durationDays * incrementRatio / 2 - 35 + timelineStartToStagesStart * incrementRatio;
        durationLabel.setLayoutX(durationLabelXLayout);
        durationLabel.setLayoutY(80);
        durationLabel.setFont(Font.font("System", FontWeight.BOLD, 13));
        durationLabel.setTextFill(RED);
        anchorPaneTL.getChildren().add(durationLabel);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd");
        int oldStage = -1;
        for (double i = 0; i <= TIMELINE_LENGTH + 0000000001; i += incrementRatio) {
            Line dayLine = new Line(0, -5, 0, 0);
            dayLine.setLayoutX(10 + i);
            dayLine.setLayoutY(300);
            double selectedYLayout = 318;
            Label dayLabel = null;
            ArrayList<ProjectEvent> existingEvents = new ArrayList();
            for (ProjectEvent currentEvent :
                    currentEvents) {
                if (currentEvent.getDate().toLocalDate().isEqual(timeLineStartDate)) {
                    existingEvents.add(currentEvent);
                }
            }

            if (!existingEvents.isEmpty()) {
                dayLabel = new Label(formatter.format(timeLineStartDate));
                dayLine.setStartY(-16);
                dayLine.setStroke(RED);
                dayLine.setStrokeWidth(2);
                dayLabel.setTextFill(RED);
                double cumulativeYLayout = 270;
                for (ProjectEvent currentEvent:
                     existingEvents) {
                    int currentStage = currentEvent.getStage();
                    Label eventLabel = new Label(currentStage + "");
                    eventLabel.setFont(Font.font("System", FontWeight.BOLD, 9));
                    if (oldStage == -1) {
                        eventLabel.setLayoutY(cumulativeYLayout);
                        eventLabel.setLayoutX(i+7.8);
                        eventLabel.setTextFill(BLUE);
                        anchorPaneTL.getChildren().add(eventLabel);
                        cumulativeYLayout -= 15;
                        oldStage = currentStage;
                        continue;
                    }
                    if (currentStage > oldStage)
                        eventLabel.setTextFill(BLUE);
                    else eventLabel.setTextFill(RED);
                    eventLabel.setLayoutY(cumulativeYLayout);
                    eventLabel.setLayoutX(i+7.8);
                    anchorPaneTL.getChildren().add(eventLabel);
                    cumulativeYLayout -= 15;
                    oldStage = currentStage;
                }
            }
            if (timeLineStartDate.getDayOfMonth() == 1) {
                DateTimeFormatter monthStartFormatter = DateTimeFormatter.ofPattern("MMM yy");
                dayLabel = new Label(monthStartFormatter.format(timeLineStartDate));
                dayLine.setStartY(-8);
                dayLine.setEndY(8);
                if (!existingEvents.isEmpty()) dayLabel.setTextFill(RED);
            }
            if (dayLabel != null) {
                dayLabel.setLayoutX(i);
                dayLabel.setLayoutY(selectedYLayout);
                dayLabel.setFont(Font.font("System", FontWeight.BOLD, 8));
                dayLabel.setRotate(80);
                anchorPaneTL.getChildren().add(dayLabel);
            }
            anchorPaneTL.getChildren().add(dayLine);
            timeLineStartDate = timeLineStartDate.plusDays(1);
        }
    }
}
