package com.leanhquan.deliveryfoodver2.Model;

import java.util.List;

public class Request {
    private String name;
    private String address;
    private String total;
    private String status;
    private List<Order> foods;

    public Request() {
    }

    public Request( String name, String address, String total, List<Order> foods) {
        this.name = name;
        this.address = address;
        this.total = total;
        this.foods = foods;
        this.status = "0"; // 0: order, 1:shipping, 2:shipped
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return address;
    }

    public void setAdress(String adress) {
        this.address = adress;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<Order> getFoods() {
        return foods;
    }

    public void setFoods(List<Order> foods) {
        this.foods = foods;
    }
}
