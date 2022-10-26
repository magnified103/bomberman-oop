package com.myproject.bomberman;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.FXGLForKtKt;
import com.almasb.fxgl.ui.FontType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ScoringSystem extends System {
    private Font newFont = FXGLForKtKt.getUIFactoryService().newFont(FontType.MONO, 60.0);
    private ImageView iv1;
    private HBox menuBox;

    public void load() {
        TerrainComponent terrain = getParentWorld().getSingletonComponent(TerrainComponent.class);
        double topLeft = terrain.getTileHeight()*terrain.getNumberOfRows();

        Image image = new Image("assets/textures/scoreBackground.png",FXGLForKtKt.getAppWidth(),FXGLForKtKt.getAppHeight()-topLeft,false,false);
        iv1 = new ImageView();
        iv1.setTranslateY(topLeft);
        iv1.setImage(image);

        Text scoreInt = new Text();
        Text scoreString = new Text("Score: ");

        scoreInt.setFont(newFont);
        scoreString.setFont(newFont);

        scoreInt.textProperty().bind(FXGLForKtKt.getip("Score").asString());

        scoreString.setStrokeWidth(1);
        scoreString.strokeProperty().bind(scoreString.fillProperty());
        scoreInt.setStrokeWidth(2);
        scoreInt.strokeProperty().bind(scoreInt.fillProperty());

        menuBox = new HBox(scoreString,scoreInt);
//        scoreInt.setTranslateY(topLeft+(FXGLForKtKt.getAppHeight() - topLeft)/2);
//        scoreInt.setTranslateX(100);
        menuBox.setTranslateY(topLeft + (FXGLForKtKt.getAppHeight() - topLeft)/2 - newFont.getSize()/2  );
        menuBox.setTranslateX(FXGLForKtKt.getAppWidth()/2.0 - menuBox.getWidth() - 60 );

        FXGL.addUINode(iv1);
        FXGL.addUINode(menuBox);
    }

    public void unload() {
        if (iv1 != null) {
            FXGL.removeUINode(iv1);
        }
        if (menuBox != null) {
            FXGL.removeUINode(menuBox);
        }
    }
}
