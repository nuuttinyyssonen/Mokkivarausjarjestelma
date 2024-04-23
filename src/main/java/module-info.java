module com.example.ohjelmistotuotanto {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.ohjelmistotuotanto to javafx.fxml;
    exports com.example.ohjelmistotuotanto;
}