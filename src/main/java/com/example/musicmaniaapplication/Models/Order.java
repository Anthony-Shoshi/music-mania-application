package com.example.musicmaniaapplication.Models;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private String customerFirstName;
    private String customerLastName;
    private String customerPhone;
    private String customerEmail;
    private List<OrderProduct> products;
    private LocalDateTime orderDateTime = LocalDateTime.now();

    public Order(String customerFirstName, String customerLastName, String customerPhone, String customerEmail, List<OrderProduct> products) {
        this.customerFirstName = customerFirstName;
        this.customerLastName = customerLastName;
        this.customerPhone = customerPhone;
        this.customerEmail = customerEmail;
        this.products = products;
    }

    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public List<OrderProduct> getProducts() {
        return products;
    }

    public void setProducts(List<OrderProduct> products) {
        this.products = products;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(LocalDateTime orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    public double calculateTotalPrice() {
        return products.stream()
                .mapToDouble(orderProduct -> orderProduct.getProduct().getPrice())
                .sum();
    }
}
