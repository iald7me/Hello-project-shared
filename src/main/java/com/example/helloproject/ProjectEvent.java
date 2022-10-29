package com.example.helloproject;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ProjectEvent {
    private LocalDateTime date;
    private int stage;

    public ProjectEvent(LocalDateTime date, int stage) {
        this.date = date;
        this.stage = stage;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public int getStage() {
        return stage;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    @Override
    public String toString() {
        return "ProjectEvent{" +
                "date=" + date +
                ", stage=" + stage +
                '}';
    }
}
