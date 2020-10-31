package com.eatin.eatinv2.Model;

public class UserModel {
    private String uid,name,phone,address;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public UserModel(String uid, String name, String phone, String address) {
        this.uid = uid;
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    public UserModel() {
    }
}
