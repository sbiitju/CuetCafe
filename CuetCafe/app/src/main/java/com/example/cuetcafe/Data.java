package com.example.cuetcafe;

public class Data {
    private String name,url,price,despcription,inStock;

    public Data(String name, String url, String price, String despcription, String inStock) {
        this.name = name;
        this.url = url;
        this.price = price;
        this.despcription = despcription;
        this.inStock = inStock;
    }

    public Data() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDespcription() {
        return despcription;
    }

    public void setDespcription(String despcription) {
        this.despcription = despcription;
    }

    public String getInStock() {
        return inStock;
    }

    public void setInStock(String inStock) {
        this.inStock = inStock;
    }
}
