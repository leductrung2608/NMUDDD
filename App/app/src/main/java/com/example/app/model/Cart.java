package com.example.app.model;

public class Cart {
    public int IdGoods;
    public String Name;
    public int CurrentPrice;
    public String Weight;
    public int Quantity;
    public String Image;
    public int MaxQuantity;
    public String idUser;

    public Cart(int idGoods, String name, int currentPrice, String weight, int quantity, String image,int maxQuantity, String idUser) {
        IdGoods = idGoods;
        Name = name;
        CurrentPrice = currentPrice;
        Weight = weight;
        Quantity = quantity;
        Image = image;
        MaxQuantity= maxQuantity;
        this.idUser = idUser;
    }

    public int getMaxQuantity() {
        return MaxQuantity;
    }

    public void setMaxQuantity(int maxQuantity) {
        MaxQuantity = maxQuantity;
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

    public int getCurrentPrice() {
        return CurrentPrice;
    }

    public void setCurrentPrice(int currentPrice) {
        CurrentPrice = currentPrice;
    }

    public String getWeight() {
        return Weight;
    }

    public void setWeight(String weight) {
        Weight = weight;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
}


