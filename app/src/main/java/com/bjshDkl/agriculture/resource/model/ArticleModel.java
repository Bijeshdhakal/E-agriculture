package com.bjshDkl.agriculture.resource.model;

import com.google.firebase.database.PropertyName;

public class ArticleModel {
    @PropertyName("id")
    int id;
    @PropertyName("title")
    String title;
    @PropertyName("authors")
    String authors;
    @PropertyName("url")
    String url;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
