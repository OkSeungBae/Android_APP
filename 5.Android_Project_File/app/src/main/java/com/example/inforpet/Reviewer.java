package com.example.inforpet;

public class Reviewer {
    private String name;
    private float rating;
    private String date;
    private String context;

    public Reviewer(String name, float rating, String date, String context) {
        this.name = name;
        this.rating = rating;
        this.date = date;
        this.context = context;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
