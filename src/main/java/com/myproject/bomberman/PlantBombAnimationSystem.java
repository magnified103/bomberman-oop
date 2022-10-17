package com.myproject.bomberman;

import com.almasb.fxgl.dsl.FXGLForKtKt;
import javafx.util.Duration;
import java.util.*;

//import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameTimer;

public class PlantBombAnimationSystem extends System {
    @Override
    public void update(double tpf) {

        List<Entity> entityList = getParentWorld().getEntitiesByType(PlantBombAnimationComponent.class
                                , FxglTransformComponent.class, FxglViewComponent.class);
        Entity player = getParentWorld().getEntitiesByType(WalkInputComponent.class, PlantBombInputComponent.class
                , FxglTransformComponent.class, FxglViewComponent.class).get(0);

        for (Entity entity : entityList) {

            FxglTransformComponent playerTransform = player.getComponentByType(FxglTransformComponent.class);
            int flameSize = player.getComponentByType(FxglTransformComponent.class).getFLAME_SIZE();
            double getBombX = entity.getComponentByType(FxglTransformComponent.class).getFxglComponent().getX();
            double getBombY = entity.getComponentByType(FxglTransformComponent.class).getFxglComponent().getY();
            Vector<Entity> flame = new Vector<>();
            if (entity.getComponentByType(PlantBombAnimationComponent.class).isActive()) {
                for (int i = 1; i <= flameSize; i++) {
                    if ((int)(getBombX / 32) - i >= 0 &&
                            playerTransform.getGRID((int)getBombX / 32 - i,(int)getBombY / 32) != 1 &&
                            playerTransform.getGRID((int)getBombX / 32 - (i - 1),(int)getBombY / 32) == 2
                    ) {
                        flame.add(getParentWorld().spawnEntity());

                        FxglTransformComponent transformComponent = new FxglTransformComponent();
                        FxglViewComponent viewComponent = new FxglViewComponent();
                        FlameComponent flameComponent;
                        if (i == flameSize) {
                            flameComponent = new FlameComponent("leftHead");
                        } else {
                            flameComponent = new FlameComponent("left");
                        }
                        flame.lastElement().addAndAttach(transformComponent);
                        flame.lastElement().addAndAttach(viewComponent);
                        flame.lastElement().addAndAttach(flameComponent);

                        transformComponent.getFxglComponent().setPosition(getBombX - i * 32, getBombY);
                        viewComponent.getFxglComponent().addChild(flameComponent.getMainFrame());
                        playerTransform.setGRID((int)(getBombX / 32) - i, (int)(getBombY / 32), 2);
                    }
                    if ((int)(getBombX / 32) + i >= 0 &&
                            playerTransform.getGRID((int)getBombX / 32 + i,(int)getBombY / 32) != 1 &&
                            playerTransform.getGRID((int)getBombX / 32 + (i - 1),(int)getBombY / 32) == 2
                    ) {
                        flame.add(getParentWorld().spawnEntity());

                        FxglTransformComponent transformComponent = new FxglTransformComponent();
                        FxglViewComponent viewComponent = new FxglViewComponent();
                        FlameComponent flameComponent;
                        if (i == flameSize) {
                            flameComponent = new FlameComponent("rightHead");
                        } else {
                            flameComponent = new FlameComponent("right");
                        }
                        flame.lastElement().addAndAttach(transformComponent);
                        flame.lastElement().addAndAttach(viewComponent);
                        flame.lastElement().addAndAttach(flameComponent);

                        transformComponent.getFxglComponent().setPosition(getBombX + i * 32, getBombY);
                        viewComponent.getFxglComponent().addChild(flameComponent.getMainFrame());
                        playerTransform.setGRID((int)(getBombX / 32) + i, (int)(getBombY / 32), 2);
                    }
                    if ((int)(getBombY / 32) - i >= 0 &&
                            playerTransform.getGRID((int)getBombX / 32,(int)getBombY / 32  - i) != 1 &&
                            playerTransform.getGRID((int)getBombX / 32,(int)getBombY / 32 - (i - 1)) == 2
                    ) {
                        flame.add(getParentWorld().spawnEntity());

                        FxglTransformComponent transformComponent = new FxglTransformComponent();
                        FxglViewComponent viewComponent = new FxglViewComponent();
                        FlameComponent flameComponent;
                        if (i == flameSize) {
                            flameComponent = new FlameComponent("upHead");
                        } else {
                            flameComponent = new FlameComponent("up");
                        }
                        flame.lastElement().addAndAttach(transformComponent);
                        flame.lastElement().addAndAttach(viewComponent);
                        flame.lastElement().addAndAttach(flameComponent);

                        transformComponent.getFxglComponent().setPosition(getBombX, getBombY - i * 32);
                        viewComponent.getFxglComponent().addChild(flameComponent.getMainFrame());
                        playerTransform.setGRID((int)(getBombX / 32), (int)(getBombY / 32) - i, 2);
                    }
                    if ((int)(getBombY / 32) + i >= 0  &&
                            playerTransform.getGRID((int)getBombX / 32,(int)getBombY / 32  + i) != 1 &&
                            playerTransform.getGRID((int)getBombX / 32,(int)getBombY / 32 + (i - 1)) == 2
                    ) {
                        flame.add(getParentWorld().spawnEntity());

                        FxglTransformComponent transformComponent = new FxglTransformComponent();
                        FxglViewComponent viewComponent = new FxglViewComponent();
                        FlameComponent flameComponent;
                        if (i == flameSize) {
                            flameComponent = new FlameComponent("downHead");
                        } else {
                            flameComponent = new FlameComponent("down");
                        }
                        flame.lastElement().addAndAttach(transformComponent);
                        flame.lastElement().addAndAttach(viewComponent);
                        flame.lastElement().addAndAttach(flameComponent);

                        transformComponent.getFxglComponent().setPosition(getBombX, getBombY + i * 32);
                        viewComponent.getFxglComponent().addChild(flameComponent.getMainFrame());
                        playerTransform.setGRID((int)(getBombX / 32), (int)(getBombY / 32) + i, 2);
                    }
                }
                FXGLForKtKt.getGameTimer().runOnceAfter(()->{
                    for (Entity E:flame) {
                        playerTransform.setGRID((int)E.getComponentByType(FxglTransformComponent.class).getFxglComponent().getX() / 32,
                                (int)E.getComponentByType(FxglTransformComponent.class).getFxglComponent().getY() / 32, 0);
                        getParentWorld().removeEntity(E);
                    }
                    flame.clear();
                }, Duration.seconds(1));
                entity.getComponentByType(PlantBombAnimationComponent.class).setActive(false);
            }
        }

    }
}
