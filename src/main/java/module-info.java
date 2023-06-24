module com.example.scrabblegui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.scrabblegui to javafx.fxml;
    exports com.example.scrabblegui;

}