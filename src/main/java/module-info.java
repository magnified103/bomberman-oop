module com.myproject.bomberman {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;

    opens com.myproject.bomberman to javafx.fxml;
    exports com.myproject.bomberman;
}