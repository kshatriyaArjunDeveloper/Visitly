package com.example.visitly;

/* CLASS FOR FLIGHT CHECKINS */
public class Flight {

    private String FLIGHT_NAME;

    public Flight() {
    }

    public Flight(String FLIGHT_NAME) {
        this.FLIGHT_NAME = FLIGHT_NAME;
    }

    public String getFLIGHT_NAME() {
        return FLIGHT_NAME;
    }

    public void setFLIGHT_NAME(String FLIGHT_NAME) {
        this.FLIGHT_NAME = FLIGHT_NAME;
    }
}
