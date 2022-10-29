package com.example.helloproject;


import java.util.ArrayList;

public class Project {

    private int serial;
    private String id;
    private int stage;
    private ArrayList<ProjectEvent> events;

    public Project(int serial, String id, int stage, ArrayList<ProjectEvent> events) {
        this.serial = serial;
        this.id = id;
        this.stage = stage;
        this.events = events;
    }

    public int getSerial() {
        return serial;
    }

    public String getId() {
        return id;
    }

    public int getStage() {
        return stage;
    }

    public ArrayList<ProjectEvent> getEvents() {
        return events;
    }

}
