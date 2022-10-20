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
        Entity brick = world.spawnEntity();
        Entity item = world.spawnEntity();
        WalkInputComponent walkInputComponent = new WalkInputComponent("W", "S", "A", "D");
        FxglTransformComponent transformComponent = new FxglTransformComponent();
        FxglViewComponent viewComponent = new FxglViewComponent();
        WalkAnimationComponent moveComponent = new WalkAnimationComponent("BombermanMove.png");
        PlantBombInputComponent bombInputComponent = new PlantBombInputComponent("Space");
        CollidableComponent collidableComponent = new CollidableComponent(CollidableType.PASSIVE);
        FxglBoundingBoxComponent bboxComponent = new FxglBoundingBoxComponent();
//      PLAYER ------------------------------------------------------------------------------------------
        player.addAndAttach(walkInputComponent);
        player.addAndAttach(transformComponent);
        player.addAndAttach(viewComponent);
        player.addAndAttach(moveComponent);
        player.addAndAttach(bombInputComponent);
        player.addAndAttach(collidableComponent);
        player.addAndAttach(bboxComponent);

        transformComponent.getFxglComponent().setPosition(0, 0);
        viewComponent.getFxglComponent().addChild(moveComponent.getMainFrame());
        bboxComponent.getFxglComponent().addHitBox(new HitBox(new Point2D(6,6), BoundingShape.box(20, 22)));
//      WALL ------------------------------------------------------------------------------------------
        transformComponent = new FxglTransformComponent();
        viewComponent = new FxglViewComponent();
        bboxComponent = new FxglBoundingBoxComponent();
        collidableComponent = new CollidableComponent(CollidableType.STATIC);


        wall.addAndAttach(transformComponent);
        wall.addAndAttach(viewComponent);
        wall.addAndAttach(bboxComponent);
        wall.addAndAttach(collidableComponent);
        //set grid at wall position = 1
        player.getComponentByType(FxglTransformComponent.class).setGRID(3,3,1);

        transformComponent.getFxglComponent().setPosition(96, 96);
        viewComponent.getFxglComponent().addChild(new AnimatedTexture(new AnimationChannel(
                FXGL.image("Wall.png"), 1, 32, 32, Duration.seconds(1), 0, 0
        )));
        viewComponent.getFxglComponent().setZIndex(-1);
        bboxComponent.getFxglComponent().addHitBox(new HitBox(BoundingShape.box(32, 32)));
//      BRICK ------------------------------------------------------------------------------------------
        transformComponent = new FxglTransformComponent();
        viewComponent = new FxglViewComponent();
        bboxComponent = new FxglBoundingBoxComponent();
        BrickComponent brickComponent = new BrickComponent();
        collidableComponent = new CollidableComponent(CollidableType.MULTIFORM);

        brick.addAndAttach(transformComponent);
        brick.addAndAttach(viewComponent);
        brick.addAndAttach(bboxComponent);
        brick.addAndAttach(collidableComponent);
        brick.addAndAttach(brickComponent);
        //set grid at brick position = 1
        player.getComponentByType(FxglTransformComponent.class).setGRID(10,3,1);

        transformComponent.getFxglComponent().setPosition(320, 96);
        viewComponent.getFxglComponent().addChild(brickComponent.getMainFrame());;
        viewComponent.getFxglComponent().setZIndex(-1);
        bboxComponent.getFxglComponent().addHitBox(new HitBox(BoundingShape.box(32, 32)));
//      ITEM ------------------------------------------------------------------------------------------
        transformComponent = new FxglTransformComponent();
        viewComponent = new FxglViewComponent();
        bboxComponent = new FxglBoundingBoxComponent();
        collidableComponent = new CollidableComponent(CollidableType.ITEM);
        ItemComponent itemComponent = new ItemComponent(ItemType.SPEED);

        item.addAndAttach(transformComponent);
        item.addAndAttach(viewComponent);
        item.addAndAttach(bboxComponent);
        item.addAndAttach(collidableComponent);
        item.addAndAttach(itemComponent);

        transformComponent.getFxglComponent().setPosition(320, 320);
        viewComponent.getFxglComponent().addChild(itemComponent.getMainFrame());;
        viewComponent.getFxglComponent().setZIndex(-2);
        bboxComponent.getFxglComponent().addHitBox(new HitBox(new Point2D(2,2), BoundingShape.box(30, 30)));
//      END SPAWN
        world.setSingletonSystem(new InputSystem());
        world.addSystem(new WalkSystem());
        world.addSystem(new WalkAnimationSystem());
        world.addSystem(new PlantBombSystem());
        world.addSystem(new PlantBombAnimationSystem());
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