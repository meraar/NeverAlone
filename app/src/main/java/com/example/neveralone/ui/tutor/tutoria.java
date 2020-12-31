package com.example.neveralone.ui.tutor;

import java.util.Date;

public class tutoria {
    public tutoria(String compañeroID, int day, int month, int year) {
        this.compañeroID = compañeroID;
        this.day = day;
        this.month = month;
        this.year = year;
    }

    private String compañeroID;
    private int day;

    public String getCompañeroID() {
        return compañeroID;
    }

    public void setCompañeroID(String compañeroID) {
        this.compañeroID = compañeroID;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    private int month;
    private int year;





}
