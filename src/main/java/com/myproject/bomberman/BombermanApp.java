package com.myproject.bomberman;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.*;
import com.almasb.fxgl.entity.component.*;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.Trigger;
import com.almasb.fxgl.input.TriggerListener;
import com.myproject.bomberman.components.BomberInputComponent;
import com.myproject.bomberman.components.InputComponent;
import com.myproject.bomberman.components.InputState;

import java.util.List;

public class BombermanApp extends GameApplication {
    Entity player;

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
        player = FXGL.spawn("player",20,20);
        player.addComponent((InputComponent) new BomberInputComponent("W", "S", "A", "D"));
    }
    @Override
    protected void initInput() {
        Input input = FXGL.getInput();

        input.addTriggerListener(new TriggerListener() {
            private void resolveInput(Trigger trigger, InputState inputState) {
                List<Entity> entities = FXGL.getGameWorld().getEntities();
                for (Entity entity : entities) {
                    List<Component> components = entity.getComponents();
                    for (Component component : components) {
                        if (InputComponent.class.isAssignableFrom(component.getClass())) {
                            ((InputComponent) component).processInput(trigger, inputState);
                        }
                    }
                }
            }
            @Override
            protected void onAction(Trigger trigger) {
                resolveInput(trigger, InputState.HOLD);
            }

            @Override
            protected void onActionBegin(Trigger trigger) {
                resolveInput(trigger, InputState.BEGIN);
            }

            @Override
            protected void onActionEnd(Trigger trigger) {
                resolveInput(trigger, InputState.END);
            }
        });
    }
    public static void main(String[] args) {
        launch(args);
    }

}