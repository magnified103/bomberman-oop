package com.myproject.bomberman;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.FXGLForKtKt;
import com.almasb.fxgl.ui.FontType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.List;

public class ScoringSystem extends System {
    Font newFont = FXGLForKtKt.getUIFactoryService().newFont(FontType.MONO, 30.0);
    public void load() {
        TerrainComponent terrain = getParentWorld().getSingletonComponent(TerrainComponent.class);
        double topLeft = terrain.getTileHeight()*terrain.getNumberOfRows();

        Image image = new Image("assets/textures/scoreBackground.png",FXGLForKtKt.getAppWidth(),FXGLForKtKt.getAppHeight()-topLeft,false,false);
        ImageView iv1 = new ImageView();
        iv1.setTranslateY(topLeft);
        iv1.setImage(image);

        Text scoreInt = new Text();
        Text scoreString = new Text("Score: ");

        scoreInt.setFont(newFont);
        scoreString.setFont(newFont);

        scoreInt.textProperty().bind(FXGLForKtKt.getip("Score").asString());
        var menuBox = new HBox(scoreString,scoreInt);
//        scoreInt.setTranslateY(topLeft+(FXGLForKtKt.getAppHeight() - topLeft)/2);
//        scoreInt.setTranslateX(100);
        menuBox.setTranslateY(topLeft + (FXGLForKtKt.getAppHeight() - topLeft)/2);

        FXGL.addUINode(iv1);
        FXGL.addUINode(menuBox);

    }
}
