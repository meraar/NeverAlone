package com.example.neveralone.ui.tutor;

import java.util.Date;

public class tutoria {
    public tutoria(String compañeroID, String day, String month, String year) {
        this.compañeroID = compañeroID;
        this.day = day;
        this.month = month;
        this.year = year;
    }

    private String compañeroID;
    private String day;
    private String month;
    private String year;

    public String getCompañeroID() {
        return compañeroID;
    }

    public void setCompañeroID(String compañeroID) {
        this.compañeroID = compañeroID;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }






}
