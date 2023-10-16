package com.example.musicmaniaapplication.Utils;

import com.example.musicmaniaapplication.MusicManiaApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneFactory {

    private SceneFactory() {
    }

    public static void loadScene(String name, Object controller, String title, Stage window, double width, double height) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MusicManiaApplication.class.getResource(name));
            fxmlLoader.setController(controller);
            Scene scene = new Scene(fxmlLoader.load(), width, height);
            if (controller.getClass().getName().equals("MainController")) {
                scene.getStylesheets().add(MusicManiaApplication.class.getResource("css/style.css").toExternalForm());
            }
            window.setTitle(title);
            window.setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException("error in loadscene method: " + e.getMessage());
        }
    }

    public static void loadContent(String fileName, Object controller, AnchorPane mainContent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MusicManiaApplication.class.getResource(fileName));
            fxmlLoader.setController(controller);
            Parent content = fxmlLoader.load();
            mainContent.getChildren().clear();
            mainContent.getChildren().add(content);
        } catch (IOException e) {
            throw new RuntimeException("error in loadContent method: " + e.getMessage());
        }
    }
}