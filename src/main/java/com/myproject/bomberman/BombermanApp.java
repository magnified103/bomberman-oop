package com.myproject.bomberman;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.*;
import com.almasb.fxgl.input.*;
import com.myproject.bomberman.components.BomberComponent;
import javafx.event.EventHandler;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.*;

public class BombermanApp extends GameApplication {
    Entity player, Bomb;
    boolean bombCheck = true;

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(800);
        settings.setHeight(600);
        settings.setTitle("Bomberman");
        settings.setDeveloperMenuEnabled(true);
    }

    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new BombermanFactory());
        player = spawn("player",32,32);
    }
    @Override
    protected void initInput() {
        Input input = FXGL.getInput();
        input.addTriggerListener(new TriggerListener() {
            @Override
            protected void onActionBegin(Trigger trigger) {
                if (trigger.isKey()) {
                    if (((KeyTrigger) trigger).getKey() == KeyCode.SPACE) {
                        if (bombCheck) Bomb = spawn("boom",player.getX(),player.getY());
                        bombCheck = false;
                        runOnce(() -> {
                            Bomb.removeFromWorld();
                            bombCheck = true;
                        }, Duration.seconds(2.5));
                    }
                }
            }

            @Override
            protected void onAction(Trigger trigger) {
                if (trigger.isKey()) {
                    if (((KeyTrigger) trigger).getKey() == KeyCode.D) {
                        player.getComponent(BomberComponent.class).moveRight();
                    }
                    if (((KeyTrigger) trigger).getKey() == KeyCode.W) {
                        player.getComponent(BomberComponent.class).moveUp();
                    }
                    if (((KeyTrigger) trigger).getKey() == KeyCode.S) {
                        player.getComponent(BomberComponent.class).moveDown();
                    }
                    if (((KeyTrigger) trigger).getKey() == KeyCode.A) {
                        player.getComponent(BomberComponent.class).moveLeft();
                    }
                }
            }

            @Override
            protected void onActionEnd(Trigger trigger) {
                player.getComponent(BomberComponent.class).stop();
            }
        });
    }
    public static void main(String[] args) {
        launch(args);
    }

}