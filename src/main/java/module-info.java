module com.example.dispensary_management_system {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.swing;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.dispensary_management_system to javafx.fxml;
    exports com.example.dispensary_management_system;
}