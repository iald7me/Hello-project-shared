package com.example.helloproject;

import javafx.scene.control.Label;

import static javafx.scene.paint.Color.BLUE;
import static javafx.scene.paint.Color.RED;

public class TimelineIndicator {
    private TimelineIndicator() {

    }

    public static void projectProgress(Label eventLabel) {
        eventLabel.setTextFill(BLUE);
    }
    public static void projectRework(Label eventLabel) {
        eventLabel.setTextFill(RED);
    }
}
