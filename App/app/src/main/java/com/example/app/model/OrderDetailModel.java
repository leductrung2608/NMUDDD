package com.example.app.model;

import java.io.Serializable;

public class OrderDetailModel implements Serializable {

    int IdBillDetail;
    String IdBill;
    int Price;
    int Quantity;
    String Name;

    public int getIdBillDetail() {
        return IdBillDetail;
    }

    public void setIdBillDetail(int idBillDetail) {
        IdBillDetail = idBillDetail;
    }

    public String getIdBill() {
        return IdBill;
    }

    public void setIdBill(String idBill) {
        IdBill = idBill;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public OrderDetailModel(int idBillDetail, String idBill, String name, int price, int quantity) {
        IdBillDetail = idBillDetail;
        IdBill = idBill;
        Price = price;
        Quantity = quantity;
        Name = name;
    }
}
