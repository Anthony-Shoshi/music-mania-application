package com.example.musicmaniaapplication.Utils;

import com.example.musicmaniaapplication.MusicManiaApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneFactory {

    private SceneFactory () {
    }

    public static void loadScene ( String name, Object controller, String title, Stage window) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader( MusicManiaApplication. class .getResource(name));
            fxmlLoader .setController(controller);
            Scene scene = new Scene(fxmlLoader.load(),800,550);
            scene.getStylesheets().add(MusicManiaApplication.class.getResource("style.css").toExternalForm());
            window.setTitle(title);
            window.setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}