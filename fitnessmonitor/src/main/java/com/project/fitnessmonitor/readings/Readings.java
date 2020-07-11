package com.project.fitnessmonitor.readings;

public class Readings {
    private String userId = "";
    private int bodyTemp = 0;
    private int heartRate = 0;
    private int numOfSteps = 0;

    public Readings(String userId, int bodyTemp, int heartRate, int numOfSteps) {
        this.userId = userId;
        this.bodyTemp = bodyTemp;
        this.heartRate = heartRate;
        this.numOfSteps = numOfSteps;
    }

    public String getUserId() {
        return userId;
    }

    public int getBodyTemp() {
        return bodyTemp;
    }

    public int getHeartRate() {
        return heartRate;
    }

    public int getNumOfSteps() {
        return numOfSteps;
    }
}
