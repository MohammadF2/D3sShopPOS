package com.example.d3sshoppos;

public class Product {
    public String productName;

    public String weight;


    public Product(String productName,  String weight) {
        this.productName = productName;
        this.weight = weight;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }


    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }


}
