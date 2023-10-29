package com.example.musicmaniaapplication.Utils;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;

public class Helper {
    public static void loadLogo(ImageView logo, String imagePath) {
        InputStream inputStream = Helper.class.getResourceAsStream(imagePath);
        if (inputStream != null) {
            Image image = new Image(inputStream);
            logo.setImage(image);
        } else {
            System.err.println("Input stream is null. Check the file path.");
        }
    }

    public static void logError(String errorMessage) {
        System.err.println(errorMessage);
    }
}
