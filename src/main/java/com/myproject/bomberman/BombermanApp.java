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
import javafx.geometry.Point2D;
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
        FxglTransformComponent transformComponent = new FxglTransformComponent();
        FxglViewComponent viewComponent = new FxglViewComponent();
        WalkAnimationComponent moveComponent = new WalkAnimationComponent("BombermanMove.png");
        PlantBombInputComponent bombInputComponent = new PlantBombInputComponent("Space");
        FxglBoundingBoxComponent bboxComponent = new FxglBoundingBoxComponent();

        player.addAndAttach(walkInputComponent);
        player.addAndAttach(transformComponent);
        player.addAndAttach(viewComponent);
        player.addAndAttach(moveComponent);
        player.addAndAttach(bombInputComponent);
        player.addAndAttach(bboxComponent);

        transformComponent.getFxglComponent().setPosition(0, 0);
        viewComponent.getFxglComponent().addChild(moveComponent.getMainFrame());
        bboxComponent.getFxglComponent().addHitBox(new HitBox(new Point2D(6,6), BoundingShape.box(20, 22)));

        transformComponent = new FxglTransformComponent();
        viewComponent = new FxglViewComponent();
        bboxComponent = new FxglBoundingBoxComponent();

        wall.addAndAttach(transformComponent);
        wall.addAndAttach(viewComponent);
        wall.addAndAttach(bboxComponent);
        //set grid at wall position = 1
        player.getComponentByType(FxglTransformComponent.class).setGRID(3,3,1);

        transformComponent.getFxglComponent().setPosition(96, 96);
        viewComponent.getFxglComponent().addChild(new AnimatedTexture(new AnimationChannel(
                FXGL.image("Wall.png"), 1, 32, 32, Duration.seconds(1), 0, 0
        )));
        viewComponent.getFxglComponent().setZIndex(-1);
        bboxComponent.getFxglComponent().addHitBox(new HitBox(BoundingShape.box(32, 32)));


        world.setSingletonSystem(new InputSystem());
        world.addSystem(new WalkSystem());
        world.addSystem(new WalkAnimationSystem());
        world.addSystem(new PlantBombSystem());
        world.addSystem(new PlantBombAnimationSystem());
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