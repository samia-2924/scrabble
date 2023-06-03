module com.example.scrabblegui {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.scrabblegui to javafx.fxml;
    exports com.example.scrabblegui;
}