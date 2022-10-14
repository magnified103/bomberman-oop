package com.myproject.bomberman;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.Trigger;
import com.almasb.fxgl.input.TriggerListener;

public class BombermanApp extends GameApplication {

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
        Entity player = world.spawnEntity();
        WalkInputComponent walkInputComponent = new WalkInputComponent("W", "S", "A", "D");
        TransformComponent transformComponent = new TransformComponent();
        ViewComponent viewComponent = new ViewComponent();
        WalkAnimationComponent moveComponent = new WalkAnimationComponent("BombermanMove.png");

        player.attachComponent(walkInputComponent);
        player.attachComponent(transformComponent);
        player.attachComponent(viewComponent);
        player.attachComponent(moveComponent);

        transformComponent.getFxglComponent().setPosition(0, 0);
        viewComponent.getFxglComponent().addChild(moveComponent.getMainFrame());

        world.setSingletonSystem(new InputSystem());
        world.addSystem(new WalkSystem());
        world.addSystem(new WalkAnimationSystem());
    }
    @Override
    protected void initInput() {
        Input input = FXGL.getInput();

        input.addTriggerListener(new TriggerListener() {
            @Override
            protected void onAction(Trigger trigger) {
                world.getSingletonSystem(InputSystem.class).updateInput(trigger, InputState.HOLD);
            }

            @Override
            protected void onActionBegin(Trigger trigger) {
                world.getSingletonSystem(InputSystem.class).updateInput(trigger, InputState.BEGIN);
            }

            @Override
            protected void onActionEnd(Trigger trigger) {
                world.getSingletonSystem(InputSystem.class).updateInput(trigger, InputState.END);
            }
        });
    }

    @Override
    protected void onUpdate(double tpf) {
        super.onUpdate(tpf);
        world.update(tpf);
    }

    public static void main(String[] args) {
        launch(args);
    }

}