package com.example.cuetcafe;

public class Cart {
    private String name,amount,taka;

    public Cart() {
    }

    public Cart(String name, String amount, String taka) {
        this.name = name;
        this.amount = amount;
        this.taka = taka;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTaka() {
        return taka;
    }

    public void setTaka(String taka) {
        this.taka = taka;
    }
}
