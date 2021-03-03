package com.example.cuetcafe;

import java.util.ArrayList;

public class Order {
    private ArrayList<Cart> data;
    String name,number,address,estimatetime;

    public Order(ArrayList<Cart> data, String name, String number, String address, String estimatetime) {
        this.data = data;
        this.name = name;
        this.number = number;
        this.address = address;
        this.estimatetime = estimatetime;
    }

    public String getEstimatetime() {
        return estimatetime;
    }

    public void setEstimatetime(String estimatetime) {
        this.estimatetime = estimatetime;
    }

    public Order(ArrayList<Cart> data, String name, String number, String address) {
        this.data = data;
        this.name = name;
        this.number = number;
        this.address = address;
    }

    public ArrayList<Cart> getData() {
        return data;
    }

    public void setData(ArrayList<Cart> data) {
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Order() {
    }
}
