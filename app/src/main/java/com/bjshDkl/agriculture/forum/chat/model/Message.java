package com.bjshDkl.agriculture.forum.chat.model;

public class Message {
    String uid;
    String message;
    String name;
    String key;



    public Message(String message, String uid, String name) {
        this.uid = uid;
        this.message = message;
        this.name = name;
    }

    public Message() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
