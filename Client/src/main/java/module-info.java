module com.example.test2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.test2 to javafx.fxml;
    exports com.example.test2;
    exports server;
    opens server to javafx.fxml;
}