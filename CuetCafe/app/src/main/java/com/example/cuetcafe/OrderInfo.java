package com.example.cuetcafe;

public class OrderInfo {
    private String customerName,foodName,foodAmount,foodTaka,customerMobile,address,token;

    public OrderInfo(String customerName, String foodName, String foodAmount, String foodTaka, String customerMobile, String address, String token) {
        this.customerName = customerName;
        this.foodName = foodName;
        this.foodAmount = foodAmount;
        this.foodTaka = foodTaka;
        this.customerMobile = customerMobile;
        this.address = address;
        this.token = token;
    }

    public OrderInfo() {
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodAmount() {
        return foodAmount;
    }

    public void setFoodAmount(String foodAmount) {
        this.foodAmount = foodAmount;
    }

    public String getFoodTaka() {
        return foodTaka;
    }

    public void setFoodTaka(String foodTaka) {
        this.foodTaka = foodTaka;
    }

    public String getCustomerMobile() {
        return customerMobile;
    }

    public void setCustomerMobile(String customerMobile) {
        this.customerMobile = customerMobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

