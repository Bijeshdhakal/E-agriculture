package com.bjshDkl.agriculture.resource.model;

import com.google.firebase.database.PropertyName;

public class YoutubeVideo {

    @PropertyName("id")
    int id;

    @PropertyName("title")
    String title;

    @PropertyName("desc")
    String desc;

    @PropertyName("videoUrl")
    String videoUrl;

    @PropertyName("image")
    String image;


    public YoutubeVideo() {
    }

    public YoutubeVideo(int id, String title, String desc, String videoUrl) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.videoUrl = videoUrl;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}
