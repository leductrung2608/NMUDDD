package com.example.ibeauty.value;

public class User {
    String username, phoneNo, email, password, address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public User(String username, String phoneNo, String email, String password, String address) {
        this.username = username;
        this.phoneNo = phoneNo;
        this.email = email;
        this.password = password;
        this.address = address;
    }

    public User(){

    }

    public User(String username, String phoneNo, String email, String password) {
        this.username = username;
        this.phoneNo = phoneNo;
        this.email = email;
        this.password = password;
    }

    public User(String username, String phoneNo, String email) {
        this.username = username;
        this.phoneNo = phoneNo;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
