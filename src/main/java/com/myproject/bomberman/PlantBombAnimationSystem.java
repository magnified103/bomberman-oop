package com.myproject.bomberman;

import com.almasb.fxgl.dsl.FXGLForKtKt;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import javafx.geometry.Point2D;
import javafx.util.Duration;

import java.util.List;
import java.util.Vector;

public class PlantBombAnimationSystem extends System {
    @Override
    public void update(double tpf) {

        List<Entity> entityList = getParentWorld().getEntitiesByType(PlantBombAnimationComponent.class
                                , FxglTransformComponent.class, FxglViewComponent.class);
        List<Entity> players = getParentWorld().getEntitiesByType(WalkInputComponent.class, PlantBombInputComponent.class
                , FxglTransformComponent.class, FxglViewComponent.class);
        for (Entity player : players) {
            for (Entity entity : entityList) {
                WalkInputComponent playerTransform = player.getComponentByType(WalkInputComponent.class);
                int flameSize = player.getComponentByType(WalkInputComponent.class).getFLAME_SIZE();
                double getBombX = entity.getComponentByType(FxglTransformComponent.class).getFxglComponent().getX();
                double getBombY = entity.getComponentByType(FxglTransformComponent.class).getFxglComponent().getY();
                Vector<Entity> flame = new Vector<>();
                if (entity.getComponentByType(PlantBombAnimationComponent.class).isActive()) {
                    for (int i = 1; i <= flameSize; i++) {
                        if ((int) (getBombX / 32) - i >= 0 &&
                                playerTransform.getGRID((int) getBombX / 32 - i, (int) getBombY / 32) != 1 &&
                                playerTransform.getGRID((int) getBombX / 32 - (i - 1), (int) getBombY / 32) == 2
                        ) {
                            flame.add(getParentWorld().spawnEntity());
                            FxglTransformComponent transformComponent = new FxglTransformComponent();
                            FxglViewComponent viewComponent = new FxglViewComponent();
                            CollidableComponent collidableComponent = new CollidableComponent(Collidable.FLAME);
                            FlameComponent flameComponent;
                            FxglBoundingBoxComponent bboxComponent = new FxglBoundingBoxComponent();

                            if (i == flameSize) {
                                flameComponent = new FlameComponent("leftHead", playerTransform.getFLAME_DURATION());
                            } else {
                                flameComponent = new FlameComponent("left", playerTransform.getFLAME_DURATION());
                            }
                            flame.lastElement().addAndAttach(transformComponent);
                            flame.lastElement().addAndAttach(viewComponent);
                            flame.lastElement().addAndAttach(flameComponent);
                            flame.lastElement().addAndAttach(collidableComponent);
                            flame.lastElement().addAndAttach(bboxComponent);

                            transformComponent.getFxglComponent().setPosition(getBombX - i * 32, getBombY);
                            viewComponent.getFxglComponent().addChild(flameComponent.getMainFrame());
                            viewComponent.getFxglComponent().setZIndex(-1);
                            if (i == flameSize) {
                                bboxComponent.getFxglComponent().addHitBox(new HitBox(new Point2D(2, 2), BoundingShape.box(30, 28)));
                            } else {
                                bboxComponent.getFxglComponent().addHitBox(new HitBox(new Point2D(0, 2), BoundingShape.box(32, 28)));
                            }
                            playerTransform.setGRID((int) (getBombX / 32) - i, (int) (getBombY / 32), 2);
                        }
                        if ((int) (getBombX / 32) + i >= 0 &&
                                playerTransform.getGRID((int) getBombX / 32 + i, (int) getBombY / 32) != 1 &&
                                playerTransform.getGRID((int) getBombX / 32 + (i - 1), (int) getBombY / 32) == 2
                        ) {
                            flame.add(getParentWorld().spawnEntity());

                            FxglTransformComponent transformComponent = new FxglTransformComponent();
                            FxglViewComponent viewComponent = new FxglViewComponent();
                            CollidableComponent collidableComponent = new CollidableComponent(Collidable.FLAME);
                            FxglBoundingBoxComponent bboxComponent = new FxglBoundingBoxComponent();
                            FlameComponent flameComponent;

                            if (i == flameSize) {
                                flameComponent = new FlameComponent("rightHead", playerTransform.getFLAME_DURATION());
                            } else {
                                flameComponent = new FlameComponent("right", playerTransform.getFLAME_DURATION());
                            }
                            flame.lastElement().addAndAttach(transformComponent);
                            flame.lastElement().addAndAttach(viewComponent);
                            flame.lastElement().addAndAttach(flameComponent);
                            flame.lastElement().addAndAttach(collidableComponent);
                            flame.lastElement().addAndAttach(bboxComponent);

                            transformComponent.getFxglComponent().setPosition(getBombX + i * 32, getBombY);
                            viewComponent.getFxglComponent().addChild(flameComponent.getMainFrame());
                            viewComponent.getFxglComponent().setZIndex(-1);
                            if (i == flameSize) {
                                bboxComponent.getFxglComponent().addHitBox(new HitBox(new Point2D(0, 2), BoundingShape.box(30, 28)));
                            } else {
                                bboxComponent.getFxglComponent().addHitBox(new HitBox(new Point2D(0, 2), BoundingShape.box(32, 28)));
                            }
                            playerTransform.setGRID((int) (getBombX / 32) + i, (int) (getBombY / 32), 2);
                        }
                        if ((int) (getBombY / 32) - i >= 0 &&
                                playerTransform.getGRID((int) getBombX / 32, (int) getBombY / 32 - i) != 1 &&
                                playerTransform.getGRID((int) getBombX / 32, (int) getBombY / 32 - (i - 1)) == 2
                        ) {
                            flame.add(getParentWorld().spawnEntity());

                            FxglTransformComponent transformComponent = new FxglTransformComponent();
                            FxglViewComponent viewComponent = new FxglViewComponent();
                            CollidableComponent collidableComponent = new CollidableComponent(Collidable.FLAME);
                            FlameComponent flameComponent;
                            FxglBoundingBoxComponent bboxComponent = new FxglBoundingBoxComponent();

                            if (i == flameSize) {
                                flameComponent = new FlameComponent("upHead", playerTransform.getFLAME_DURATION());
                            } else {
                                flameComponent = new FlameComponent("up", playerTransform.getFLAME_DURATION());
                            }
                            flame.lastElement().addAndAttach(transformComponent);
                            flame.lastElement().addAndAttach(viewComponent);
                            flame.lastElement().addAndAttach(flameComponent);
                            flame.lastElement().addAndAttach(collidableComponent);
                            flame.lastElement().addAndAttach(bboxComponent);

                            transformComponent.getFxglComponent().setPosition(getBombX, getBombY - i * 32);
                            viewComponent.getFxglComponent().addChild(flameComponent.getMainFrame());
                            viewComponent.getFxglComponent().setZIndex(-1);
                            if (i == flameSize) {
                                bboxComponent.getFxglComponent().addHitBox(new HitBox(new Point2D(2, 2), BoundingShape.box(28, 30)));
                            } else {
                                bboxComponent.getFxglComponent().addHitBox(new HitBox(new Point2D(2, 0), BoundingShape.box(28, 32)));
                            }
                            playerTransform.setGRID((int) (getBombX / 32), (int) (getBombY / 32) - i, 2);
                        }
                        if ((int) (getBombY / 32) + i >= 0 &&
                                playerTransform.getGRID((int) getBombX / 32, (int) getBombY / 32 + i) != 1 &&
                                playerTransform.getGRID((int) getBombX / 32, (int) getBombY / 32 + (i - 1)) == 2
                        ) {
                            flame.add(getParentWorld().spawnEntity());

                            FxglTransformComponent transformComponent = new FxglTransformComponent();
                            FxglViewComponent viewComponent = new FxglViewComponent();
                            CollidableComponent collidableComponent = new CollidableComponent(Collidable.FLAME);
                            FlameComponent flameComponent;
                            FxglBoundingBoxComponent bboxComponent = new FxglBoundingBoxComponent();

                            if (i == flameSize) {
                                flameComponent = new FlameComponent("downHead", playerTransform.getFLAME_DURATION());
                            } else {
                                flameComponent = new FlameComponent("down", playerTransform.getFLAME_DURATION());
                            }
                            flame.lastElement().addAndAttach(transformComponent);
                            flame.lastElement().addAndAttach(viewComponent);
                            flame.lastElement().addAndAttach(flameComponent);
                            flame.lastElement().addAndAttach(collidableComponent);
                            flame.lastElement().addAndAttach(bboxComponent);

                            transformComponent.getFxglComponent().setPosition(getBombX, getBombY + i * 32);
                            viewComponent.getFxglComponent().addChild(flameComponent.getMainFrame());
                            viewComponent.getFxglComponent().setZIndex(-1);
                            if (i == flameSize) {
                                bboxComponent.getFxglComponent().addHitBox(new HitBox(new Point2D(2, 0), BoundingShape.box(28, 30)));
                            } else {
                                bboxComponent.getFxglComponent().addHitBox(new HitBox(new Point2D(2, 0), BoundingShape.box(28, 32)));
                            }
                            playerTransform.setGRID((int) (getBombX / 32), (int) (getBombY / 32) + i, 2);
                        }
                    }
                    FXGLForKtKt.getGameTimer().runOnceAfter(() -> {
                        for (Entity E : flame) {
                            playerTransform.setGRID((int) E.getComponentByType(FxglTransformComponent.class).getFxglComponent().getX() / 32,
                                    (int) E.getComponentByType(FxglTransformComponent.class).getFxglComponent().getY() / 32, 0);

                            getParentWorld().removeEntity(E);
                        }
                        flame.clear();
                    }, Duration.seconds(playerTransform.getFLAME_DURATION()));
                    entity.getComponentByType(PlantBombAnimationComponent.class).setActive(false);
                }
            }
        }
    }
}
