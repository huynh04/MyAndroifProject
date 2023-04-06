package com.example.my_blog.DoMain;

public class User {
    String username;
    String useremail;
    String userpassword;

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    String coverImg;

    public String getUserpicture() {
        return userpicture;
    }

    public void setUserpicture(String userpicture) {
        this.userpicture = userpicture;
    }

    String userpicture;

    public User(){}

    public User(String username, String useremail, String userpassword,String userpicture,String coverImg) {
        this.username = username;
        this.useremail = useremail;
        this.userpassword = userpassword;
        this.userpicture = userpicture;
        this.coverImg = coverImg;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }
}
