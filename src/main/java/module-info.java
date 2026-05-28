module com.example.miniproyectosudoku6x6 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.miniproyectosudoku6x6 to javafx.fxml;
    exports com.example.miniproyectosudoku6x6;
}