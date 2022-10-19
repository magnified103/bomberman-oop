package com.myproject.bomberman;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.Trigger;
import com.almasb.fxgl.input.TriggerListener;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;

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
        FxglTransformComponent transformComponent = new FxglTransformComponent();
        FxglViewComponent viewComponent = new FxglViewComponent();
        WalkAnimationComponent moveComponent = new WalkAnimationComponent("BombermanMove.png");
        CollidableComponent collidableComponent = new CollidableComponent(CollidableType.PASSIVE);
        FxglBoundingBoxComponent boundingBoxComponent = new FxglBoundingBoxComponent();

        player.addAndAttach(walkInputComponent);
        player.addAndAttach(transformComponent);
        player.addAndAttach(viewComponent);
        player.addAndAttach(moveComponent);
        player.addAndAttach(collidableComponent);
        player.addAndAttach(boundingBoxComponent);

        transformComponent.getFxglComponent().setPosition(0, 0);
        viewComponent.getFxglComponent().addChild(moveComponent.getMainFrame());
        boundingBoxComponent.getFxglComponent().addHitBox(new HitBox(BoundingShape.box(32, 32)));

        Entity enemy = world.spawnEntity();

        walkInputComponent = new WalkInputComponent("U", "J", "H", "K");
        transformComponent = new FxglTransformComponent();
        viewComponent = new FxglViewComponent();
        moveComponent = new WalkAnimationComponent("BombermanMove.png");
        collidableComponent = new CollidableComponent(CollidableType.HOSTILE);
        boundingBoxComponent = new FxglBoundingBoxComponent();

        enemy.addAndAttach(walkInputComponent);
        enemy.addAndAttach(transformComponent);
        enemy.addAndAttach(viewComponent);
        enemy.addAndAttach(moveComponent);
        enemy.addAndAttach(collidableComponent);
        enemy.addAndAttach(boundingBoxComponent);

        transformComponent.getFxglComponent().setPosition(200, 200);
        viewComponent.getFxglComponent().addChild(moveComponent.getMainFrame());
        boundingBoxComponent.getFxglComponent().addHitBox(new HitBox(BoundingShape.box(32, 32)));

        world.setSingletonSystem(new InputSystem());
        world.addSystem(new WalkSystem());
        world.addSystem(new WalkAnimationSystem());
        world.addSystem(new CollisionSystem());
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