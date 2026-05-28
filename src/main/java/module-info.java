module com.example.miniproyectosudoku6x6 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens com.example.miniproyectosudoku6x6 to javafx.fxml;
    exports com.example.miniproyectosudoku6x6;

    exports com.example.miniproyectosudoku6x6.model;

    exports com.example.miniproyectosudoku6x6.controller;
    opens com.example.miniproyectosudoku6x6.controller to javafx.fxml;
}