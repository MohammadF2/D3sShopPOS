package com.example.d3sshoppos.model;

import com.example.d3sshoppos.Product;

import java.util.ArrayList;
import java.util.List;

public class Regal {


    private String regalName;
    private int number;
    private List<Product> products;

    public Regal(int number, String regalName) {
        this.number = number;
        this.products = new ArrayList<>();
        this.regalName = regalName;
    }


    public void addToList(String productName, String productWeight) {
        products.add(new Product(productName, productWeight));
    }

    public int getNumber() {
        return number;
    }

    public String getRegalName() {
        return regalName;
    }

    public void setRegalName(String regalName) {
        this.regalName = regalName;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return regalName ;
    }
}
