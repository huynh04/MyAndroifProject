package com.example.my_blog.DoMain;

public class Status {
    String title;
    String picture;
    String username;
    String userimg;
    public Status(){}

    public Status(String title, String picture, String username,String userimg) {
        this.title = title;
        this.picture = picture;
        this.username = username;
        this.userimg = userimg;
    }
    public String getUserimg() {
        return userimg;
    }

    public void setUserimg(String userimg) {
        this.userimg = userimg;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
