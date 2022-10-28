package com.example.helloproject;


public class Project {
    private int serial;
    private String id;
    private int stage;

    public Project(int serial, String id, int stage) {
        this.serial = serial;
        this.id = id;
        this.stage = stage;
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
}
