package com.myproject.bomberman;

import java.util.List;

import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;


public class PlantBombInputSystem extends System {
    @Override
    public void update(double tpf) {
        List<Entity> entityList = getParentWorld().getEntitiesByType(PlantBombInputComponent.class);
        for (Entity entity : entityList) {
            PlantBombInputComponent input = entity.getComponentByType(PlantBombInputComponent.class);
            if (input.getBombCheck() == 1) {
                Entity bomb = getParentWorld().spawnEntity();
                TransformComponent transformComponent = new TransformComponent();
                ViewComponent viewComponent = new ViewComponent();
                PlantBombAnimationComponent plantBombAnimationComponent = new PlantBombAnimationComponent("Bomb.png");

                bomb.attachComponent(plantBombAnimationComponent);
                bomb.attachComponent(transformComponent);
                bomb.attachComponent(viewComponent);

                //Get position of player
                BoundingBoxComponent bbox = entity.getComponentByType(BoundingBoxComponent.class);

                transformComponent.getFxglComponent().setPosition((int)(bbox.getFxglComponent().getCenterWorld().getX() / 32) *32
                        ,(int)(bbox.getFxglComponent().getCenterWorld().getY() / 32 ) * 32);

                viewComponent.getFxglComponent().addChild(plantBombAnimationComponent.getMainFrame());
                input.setBombCheck(2); //set bomb check de bomb chi spawn 1 lan moi khi an

                getGameTimer().runOnceAfter(()->{
                    getParentWorld().removeEntity(bomb);
                    input.setBombCheck(0);
                    plantBombAnimationComponent.setBombExplosion();
                    //sau 2s moi co the dat
                }, Duration.seconds(2));
            }
        }
    }
}
