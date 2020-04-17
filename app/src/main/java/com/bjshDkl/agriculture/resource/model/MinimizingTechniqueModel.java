package com.bjshDkl.agriculture.resource.model;

import com.google.firebase.database.PropertyName;

import java.io.Serializable;

public class MinimizingTechniqueModel implements Serializable {
    @PropertyName("id")
    int id;

    @PropertyName("image")
    String image;

    @PropertyName("description")
    String description;

    @PropertyName("title")
    String title;

    public MinimizingTechniqueModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
