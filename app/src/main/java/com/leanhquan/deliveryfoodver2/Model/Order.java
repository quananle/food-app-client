package com.leanhquan.deliveryfoodver2.Model;

public class Order {

    private String ProductID;
    private String ProductName;
    private String ProductCount;
    private String ProductPrice;
    private String Discount;
    private String Image;

    public Order(){}

    public Order(String productID, String productName, String productCount, String productPrice, String discount, String image) {
        ProductID = productID;
        ProductName = productName;
        ProductCount = productCount;
        ProductPrice = productPrice;
        Discount = discount;
        Image = image;
    }

    public String getProductID() {
        return ProductID;
    }

    public void setProductID(String productID) {
        ProductID = productID;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductCount() {
        return ProductCount;
    }

    public void setProductCount(String productCount) {
        ProductCount = productCount;
    }

    public String getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(String productPrice) {
        ProductPrice = productPrice;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
