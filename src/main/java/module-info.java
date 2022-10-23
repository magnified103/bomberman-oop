module com.myproject.bomberman {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.almasb.fxgl.all;

    opens com.myproject.bomberman;

    opens assets.textures;
    opens assets.ui.fonts;
    opens assets.music;
    opens assets.sounds;
}