package com.example.helloproject;

public class ProjectReworks {
    int reworksBeforeAward;
    int reworksAfterAward;

    public ProjectReworks() {
        this.reworksBeforeAward = 0;
        this.reworksAfterAward = 0;
    }

    public int getReworksBeforeAward() {
        return reworksBeforeAward;
    }

    public int getReworksAfterAward() {
        return reworksAfterAward;
    }

    public void incrementReworks(int currentStage) {
        if (currentStage >= 4)
            reworksAfterAward++;
        else
            reworksBeforeAward++;

    }
}
