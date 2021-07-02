package com.example.app.model;

import java.io.Serializable;

public class OrderModel implements Serializable {

    String IdBill;
    String NameUser;
    String Mobile;
    String Address;
    String Total;
    String Ship;
    String GrandTotal;//tổng
    String IdUser;
    String Situation;



    public OrderModel (String idBill, String nameUser, String mobile, String address, String total, String ship, String grandTotal, String idUser, String situation) {
        IdBill = idBill;
        NameUser = nameUser;
        Mobile = mobile;
        Address = address;
        Total = total;
        Ship = ship;
        GrandTotal = grandTotal;
        IdUser = idUser;
        Situation = situation;
    }

    public String getIdBill() {
        return IdBill;
    }

    public void setIdBill(String idBill) {
        IdBill = idBill;
    }

    public String getNameUser() {
        return NameUser;
    }

    public void setNameUser(String nameUser) {
        NameUser = nameUser;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        Total = total;
    }

    public String getShip() {
        return Ship;
    }

    public void setShip(String ship) {
        Ship = ship;
    }

    public String getGrandTotal() {
        return GrandTotal;
    }

    public void setGrandTotal(String grandTotal) {
        GrandTotal = grandTotal;
    }

    public String getIdUser() {
        return IdUser;
    }

    public void setIdUser(String idUser) {
        IdUser = idUser;
    }

    public String getSituation() {return Situation; }

    public void setSituation(String situation) { Situation = situation; }
}
