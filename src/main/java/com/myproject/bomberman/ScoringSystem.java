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
    private TimerComponent timer;

    public void load() {
        TerrainComponent terrain = getParentWorld().getSingletonComponent(TerrainComponent.class);
        double topLeft = terrain.getTileHeight()*terrain.getNumberOfRows();

        Image image = new Image("assets/textures/scoreBackground.png",FXGLForKtKt.getAppWidth(),FXGLForKtKt.getAppHeight()-topLeft,false,false);
        bg = new ImageView();
        bg.setTranslateY(topLeft);
        bg.setImage(image);

        Text scoreInt = new Text();
        Text scoreString = new Text("Score: ");
        Text timeInt = new Text();
        Text timeString = new Text("     Time: ");
        timer = new TimerComponent(180);
        getParentWorld().addComponent(timer);

        scoreInt.setFont(newFont);
        scoreString.setFont(newFont);
        timeInt.setFont(newFont);
        timeString.setFont(newFont);
        int integerTime = (int)timer.getRemainingTime();
        FXGL.set("Time",integerTime);

        scoreInt.textProperty().bind(FXGLForKtKt.getip("Score").asString());
        timeInt.textProperty().bind(FXGLForKtKt.getip("Time").asString());

        scoreBox = new HBox(scoreString,scoreInt,timeString,timeInt);

        scoreBox.setTranslateY(topLeft + (FXGLForKtKt.getAppHeight() - topLeft)/2 - newFont.getSize()/2);
        scoreBox.setTranslateX(FXGLForKtKt.getAppWidth()/2.0 - 185);

        FXGL.addUINode(bg);
        FXGL.addUINode(scoreBox);
    }

    @Override
    public void update(double tpf) {
//        timer.tick(tpf);
        if (timer.isFinished()) {
            FXGL.play("sfxDead.wav");
            getParentWorld().getSystem(WorldUtility.class).pauseLevel();
            getParentWorld().getSingletonComponent(DataComponent.class).setData("gameState","dead");
        }
        int integerTime = (int)timer.getRemainingTime();
        FXGL.set("Time",integerTime);
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
