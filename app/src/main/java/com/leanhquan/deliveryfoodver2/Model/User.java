package com.leanhquan.deliveryfoodver2.Model;

public class User {
    private String name;
    private String password;
    private String phone;
    private String rescue;


    public User(){}

    public User(String name, String password, String phone, String rescue) {
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.rescue = rescue;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRescue() {
        return rescue;
    }

    public void setRescue(String rescue) {
        this.rescue = rescue;
    }
}
