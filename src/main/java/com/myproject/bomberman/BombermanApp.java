package com.myproject.bomberman;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.Trigger;
import com.almasb.fxgl.input.TriggerListener;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.texture.Texture;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.util.Duration;

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
        Entity wall = world.spawnEntity();
        WalkInputComponent walkInputComponent = new WalkInputComponent("W", "S", "A", "D");
        TransformComponent transformComponent = new TransformComponent();
        ViewComponent viewComponent = new ViewComponent();
        WalkAnimationComponent moveComponent = new WalkAnimationComponent("BombermanMove.png");
        PlantBombInputComponent bombInputComponent = new PlantBombInputComponent("Space");
        BoundingBoxComponent bboxComponent = new BoundingBoxComponent();

        player.attachComponent(walkInputComponent);
        player.attachComponent(transformComponent);
        player.attachComponent(viewComponent);
        player.attachComponent(moveComponent);
        player.attachComponent(bombInputComponent);
        player.attachComponent(bboxComponent);

        transformComponent.getFxglComponent().setPosition(0, 0);
        viewComponent.getFxglComponent().addChild(moveComponent.getMainFrame());
        bboxComponent.getFxglComponent().addHitBox(new HitBox(new Point2D(6,6), BoundingShape.box(20, 22)));

        transformComponent = new TransformComponent();
        viewComponent = new ViewComponent();
        bboxComponent = new BoundingBoxComponent();

        wall.attachComponent(transformComponent);
        wall.attachComponent(viewComponent);
        wall.attachComponent(bboxComponent);

        transformComponent.getFxglComponent().setPosition(0, 0);
        viewComponent.getFxglComponent().addChild(new AnimatedTexture(new AnimationChannel(
                FXGL.image("Wall.png"), 1, 32, 32, Duration.seconds(1), 0, 0
        )));
        bboxComponent.getFxglComponent().addHitBox(new HitBox(BoundingShape.box(32, 32)));


        world.setSingletonSystem(new InputSystem());
        world.addSystem(new WalkInputSystem());
        world.addSystem(new WalkAnimationSystem());
        world.setSingletonSystem(new PlantBombInputSystem());
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