package com.example.classyaccessories.Model;

public class AdminOrders {
    //private String name, phone, address, city, state, date, time, totalAmount;
    private String date;
    private String discount;
    private String pid;
    private String name;
    private String price;
    private String quantity ;
    private String time;
    private String phone;
    private String address;
    private String city;
    private String totalAmount;

    public AdminOrders(){

    }

    public AdminOrders(String date, String discount, String pid, String name, String price, String quantity, String time, String phone, String address, String city, String totalAmount) {
        this.date = date;
        this.discount = discount;
        this.pid = pid;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.time = time;
        this.phone = phone;
        this.address = address;
        this.city = city;
        this.totalAmount = totalAmount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

  /*public AdminOrders(){}

    public AdminOrders(String name, String phone, String address, String city, String state, String date, String time, String totalAmount) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.city = city;
        this.state = state;
        this.date = date;
        this.time = time;
        this.totalAmount = totalAmount;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getTotalAmount() {
        return totalAmount;
    }*/
}
