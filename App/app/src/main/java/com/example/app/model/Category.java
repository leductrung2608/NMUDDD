package com.example.app.model;

public class Category {

    //JsonDataList
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public Category( String imageurl) {
        // this.id = id;
        this.imageurl = imageurl;
    }

    public int id;
    public String imageurl;


}
