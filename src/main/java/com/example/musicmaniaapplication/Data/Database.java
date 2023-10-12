package com.example.musicmaniaapplication.Data;

import com.example.musicmaniaapplication.Models.User;
import com.example.musicmaniaapplication.Models.UserType;

import java.util.ArrayList;
import java.util.List;

public class Database {
    private List<User> users = new ArrayList<>();

    public Database() {
        users.add(new User("anthony", "anthony1@", UserType.Manager));
        users.add(new User("andrew", "andrew1@", UserType.Sales));
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
}
