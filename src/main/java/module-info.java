module com.example.musicmaniaapplication {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.musicmaniaapplication to javafx.fxml;
    exports com.example.musicmaniaapplication;
    exports com.example.musicmaniaapplication.Controllers;
}