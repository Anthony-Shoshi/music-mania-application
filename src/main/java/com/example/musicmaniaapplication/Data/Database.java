package com.example.musicmaniaapplication.Data;

import com.example.musicmaniaapplication.Models.*;
import com.example.musicmaniaapplication.Utils.Constants;
import com.example.musicmaniaapplication.Utils.Helper;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Database implements Serializable {
    private List<User> users = new ArrayList<>();
    private List<Product> products = new ArrayList<>();
    private List<OrderProduct> orderProducts = new ArrayList<>();
    private List<Order> orders = new ArrayList<>();

    public Database() {
        if (!isSerializedDataFileExists()) {
            loadUsersFromCSV();
        } else {
            deserializeDatabase();
        }
    }

    private boolean isSerializedDataFileExists() {
        File file = new File("database.dat");
        return file.exists();
    }

    public User getUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public List<User> getUsers() {
        return users;
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

    public void serializeDatabase() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("database.dat"))) {
            oos.writeObject(this);
        } catch (IOException e) {
            Helper.logError("Error while serializing the database: " + e.getMessage());
        }
    }

    public void deserializeDatabase() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("database.dat"))) {
            Database deserializedDatabase = (Database) in.readObject();
            this.users = deserializedDatabase.users;
            this.products = deserializedDatabase.products;
            this.orderProducts = deserializedDatabase.orderProducts;
            this.orders = deserializedDatabase.orders;
        } catch (FileNotFoundException e) {
            Helper.logError("Serialized file not found. Loading data from users.csv.");
            loadUsersFromCSV();
        } catch (IOException | ClassNotFoundException e) {
            Helper.logError("Error while deserializing the database: " + e.getMessage());
        }
    }

    public void loadUsersFromCSV() {
        try {
            InputStream inputStream = Helper.class.getResourceAsStream(Constants.USER_FILE);

            if (inputStream != null) {
                try (Scanner scanner = new Scanner(inputStream)) {
                    if (scanner.hasNextLine()) {
                        scanner.nextLine();
                    }

                    while (scanner.hasNextLine()) {
                        String line = scanner.nextLine();
                        String[] parts = line.split(",");

                        if (parts.length >= 3) {
                            String username = parts[0];
                            String password = parts[1];
                            UserType userType = UserType.valueOf(parts[2]);

                            users.add(new User(username, password, userType));
                        }
                    }
                }
            } else {
                Helper.logError("Input stream is null. Check the file path.");
            }
        } catch (Exception e) {
            Helper.logError("Error while loading users from CSV: " + e.getMessage());
        }
    }

}
