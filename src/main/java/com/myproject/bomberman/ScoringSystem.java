package com.myproject.bomberman;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.FXGLForKtKt;
import com.almasb.fxgl.ui.FontType;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ScoringSystem extends System {
    private Font newFont = FXGLForKtKt.getUIFactoryService().newFont(FontType.MONO, 60.0);
    private ImageView bg;
    private HBox scoreBox;

    public void load() {
        TerrainComponent terrain = getParentWorld().getSingletonComponent(TerrainComponent.class);
        double topLeft = terrain.getTileHeight()*terrain.getNumberOfRows();

        Image image = new Image("assets/textures/scoreBackground.png",FXGLForKtKt.getAppWidth(),FXGLForKtKt.getAppHeight()-topLeft,false,false);
        bg = new ImageView();
        bg.setTranslateY(topLeft);
        bg.setImage(image);

        Text scoreInt = new Text();
        Text scoreString = new Text("Score: ");

        scoreInt.setFont(newFont);
        scoreString.setFont(newFont);

        scoreInt.textProperty().bind(FXGLForKtKt.getip("Score").asString());

        scoreString.setStrokeWidth(1);
        scoreString.strokeProperty().bind(scoreString.fillProperty());
        scoreInt.setStrokeWidth(2);
        scoreInt.strokeProperty().bind(scoreInt.fillProperty());

        scoreBox = new HBox(scoreString,scoreInt);

        scoreBox.setTranslateY(topLeft + (FXGLForKtKt.getAppHeight() - topLeft)/2 - newFont.getSize()/2  );
        scoreBox.setTranslateX(FXGLForKtKt.getAppWidth()/2.0 - scoreBox.getWidth() - 60 );

        FXGL.addUINode(bg);
        FXGL.addUINode(scoreBox);
    }

    public void unload() {
        if (bg != null) {
            FXGL.removeUINode(bg);
        }
        if (scoreBox != null) {
            FXGL.removeUINode(scoreBox);
        }
    }
}
