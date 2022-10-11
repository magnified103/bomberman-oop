package com.myproject.bomberman;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.Trigger;
import com.almasb.fxgl.input.TriggerListener;
import com.myproject.bomberman.ecs.World;

public class BombermanApp extends GameApplication {
//    Entity player;

    World world;

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(800);
        settings.setHeight(600);
        settings.setTitle("Bomberman");
        settings.setDeveloperMenuEnabled(true);
    }

    @Override
    protected void initGame() {
        world = new World();
        world.addComponent(new InputComponent("W", "S", "A", "D"));
        world.addSystem(new InputSystem());
    }
    @Override
    protected void initInput() {
        Input input = FXGL.getInput();

        input.addTriggerListener(new TriggerListener() {
            private void resolveInput(Trigger trigger, InputState inputState) {
            }
            @Override
            protected void onAction(Trigger trigger) {
                world.getSystemByType(InputSystem.class).updateInput(trigger, InputState.HOLD);
            }

            @Override
            protected void onActionBegin(Trigger trigger) {
                world.getSystemByType(InputSystem.class).updateInput(trigger, InputState.BEGIN);
            }

            @Override
            protected void onActionEnd(Trigger trigger) {
                world.getSystemByType(InputSystem.class).updateInput(trigger, InputState.END);
            }
        });
    }
    public static void main(String[] args) {
        launch(args);
    }

}