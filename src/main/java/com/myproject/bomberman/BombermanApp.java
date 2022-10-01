package com.myproject.bomberman;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.*;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BombermanApp extends GameApplication {
//    Entity player;

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(800);
        settings.setHeight(600);
        settings.setTitle("Bomberman");
        settings.setDeveloperMenuEnabled(true);
    }

    @Override
    protected void initGame() {
        FXGL.getGameWorld().addEntityFactory(new BombermanFactory());
        for (int i = 0; i < 800; i += 40) {
            for (int j = 0; j < 600; j += 40) {
                FXGL.getGameWorld().spawn("grass", i, j);
            }
        }
    }
//    @Override
//    protected void initInput() {
//        Input input = FXGL.getInput();
//
//        input.addAction(new UserAction("Move Right") {
//            @Override
//            protected void onAction() {
//                player.translateX(5);
//            }
//        }, KeyCode.D);
//        input.addAction(new UserAction("Move Left") {
//            @Override
//            protected void onAction() {
//                player.translateX(-5);
//            }
//        }, KeyCode.A);
//        input.addAction(new UserAction("Move Up") {
//            @Override
//            protected void onAction() {
//                player.translateY(-5);
//            }
//        }, KeyCode.W);
//        input.addAction(new UserAction("Move Down") {
//            @Override
//            protected void onAction() {
//                player.translateY(5);
//            }
//        }, KeyCode.S);
//    }
    public static void main(String[] args) {
        launch(args);
    }

}