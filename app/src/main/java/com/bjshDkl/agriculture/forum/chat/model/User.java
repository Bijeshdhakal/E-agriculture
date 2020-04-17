package com.bjshDkl.agriculture.forum.chat.model;

public class User {
    String uid;
    String name;
    String email;


    public User() {
    }

    public User(String uid, String name, String email) {
        this.uid = uid;
        this.name = name;
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return  "User{"+
                "uid - " + uid +
                "name - " + name +
                "email - " + email +
                "}";
    }
}
