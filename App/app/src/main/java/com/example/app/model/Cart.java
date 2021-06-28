package com.example.app.model;

public class Cart {
    public int IdGoods;
    public String Name;
    public String Kind;
    public double OldPrice;
    public double NewPrice;
    public String Image;
    public int Quantity;

    public Cart(int idGoods, String name, String kind, double oldPrice, double newPrice, String image, int quantity) {
        IdGoods = idGoods;
        Name = name;
        Kind = kind;
        OldPrice = oldPrice;
        NewPrice = newPrice;
        Image = image;
        Quantity = quantity;
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

    public String getKind() {
        return Kind;
    }

    public void setKind(String kind) {
        Kind = kind;
    }

    public double getOldPrice() {
        return OldPrice;
    }

    public void setOldPrice(double oldPrice) {
        OldPrice = oldPrice;
    }

    public double getNewPrice() {
        return NewPrice;
    }

    public void setNewPrice(double newPrice) {
        NewPrice = newPrice;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }
}


