package com.bjshDkl.agriculture.newsOffers.model;

import com.google.firebase.database.PropertyName;

public class ProgramModel {
    @PropertyName("id")
    int id;

    @PropertyName("date")
    String date;

    @PropertyName("publisher")
    String publisher;

    @PropertyName("location")
    String location;

    @PropertyName("title")
    String title;

    @PropertyName("image")
    String image;

    public ProgramModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
