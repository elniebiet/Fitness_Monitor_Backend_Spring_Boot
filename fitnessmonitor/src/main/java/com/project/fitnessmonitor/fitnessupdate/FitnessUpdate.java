package com.project.fitnessmonitor.fitnessupdate;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FitnessUpdate {
    private String userId;
    private int bodyTemp;
    private int heartRate;
    private int numberOfSteps;

    public FitnessUpdate(@JsonProperty("userId") String userId, @JsonProperty("bodyTemp") int bodyTemp, @JsonProperty("heartRate") int heartRate, @JsonProperty("numberOfSteps") int numberOfSteps) {
        this.userId = userId;
        this.bodyTemp = bodyTemp;
        this.heartRate = heartRate;
        this.numberOfSteps = numberOfSteps;
    }

    @Override
    public String toString() {
        return "FitnessUpdate{" +
                "userId='" + userId + '\'' +
                ", bodyTemp=" + bodyTemp +
                ", heartRate=" + heartRate +
                ", numberOfSteps=" + numberOfSteps +
                '}';
    }

    public void setBodyTemp(int bodyTemp) {
        this.bodyTemp = bodyTemp;
    }

    public void setHeartRate(int heartRate) {
        this.heartRate = heartRate;
    }

    public void setNumberOfSteps(int numberOfSteps) {
        this.numberOfSteps = numberOfSteps;
    }

    public int getBodyTemp() {
        return bodyTemp;
    }

    public int getHeartRate() {
        return heartRate;
    }

    public int getNumberOfSteps() {
        return numberOfSteps;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

}
