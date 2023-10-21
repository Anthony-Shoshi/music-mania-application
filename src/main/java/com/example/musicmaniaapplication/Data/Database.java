package com.example.musicmaniaapplication.Data;

import com.example.musicmaniaapplication.Models.*;

import java.util.ArrayList;
import java.util.List;

public class Database {
    private List<User> users = new ArrayList<>();
    private List<Product> products = new ArrayList<>();
    private List<OrderProduct> orderProducts = new ArrayList<>();
    private List<Order> orders = new ArrayList<>();

    public Database() {
        //users
        users.add(new User("anthony", "anthony1@", UserType.Manager));
        users.add(new User("andrew", "andrew1@", UserType.Sales));

        //products
        Product product1 = new Product("product 1", "PC", 12, 1200.00, "This is product 1.");
        Product product2 = new Product("product 2", "Car", 15, 900.00, "This is product 2.");
        products.add(product1);
        products.add(product2);
    }

    public User getUser(String username, String password) {
        for (User user :
                users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public List<Product> getProducts() {
        return products;
    }

    public List<OrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public List<Order> getOrders() {
        return orders;
    }
}
