package com.example.app.model;

import java.io.Serializable;

public class AllProductModel implements Serializable {

    public int IdGoods;
    public String Name;
    public int CurrentPrice;
    public int Status;
    public String Weight;
    public String Description;
    public String Image;
    public int Kind;

    public AllProductModel(int idGoods, String name, int currentPrice, int status, String weight, String description, String image, int kind) {
        IdGoods = idGoods;
        Name = name;
        CurrentPrice = currentPrice;
        Status = status;
        Weight = weight;
        Description = description;
        Image = image;
        Kind = kind;
    }

    public int getIdGoods() {
        return IdGoods;
    }

    public void setIdGoods(int idGoods) {
        IdGoods = idGoods;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getKind() {
        return Kind;
    }

    public void setKind(int kind) {
        Kind = kind;
    }

    public int getCurrentPrice() {
        return CurrentPrice;
    }

    public void setCurrentPrice(int currentPrice) {
        CurrentPrice = currentPrice;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public String getWeight() {
        return Weight;
    }

    public void setWeight(String weight) {
        Weight = weight;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
