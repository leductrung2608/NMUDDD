package com.example.ibeauty.value;

public class ListProduct {
    private String Name;
    private String Describe;
    private String Price;
    private int Image;

    public ListProduct(String name, String describe, String price, int image) {
        Name = name;
        Describe = describe;
        Price = price;
        Image = image;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescribe() {
        return Describe;
    }

    public void setDescribe(String describe) {
        Describe = describe;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }
}
