package com.myproject.bomberman;

import java.util.List;

import com.almasb.fxgl.dsl.FXGLForKtKt;
import javafx.util.Duration;



public class PlantBombSystem extends System {
    @Override
    public void update(double tpf) {
        List<Entity> entityList = getParentWorld().getEntitiesByType(PlantBombInputComponent.class);
        for (Entity entity : entityList) {
            PlantBombInputComponent input = entity.getComponentByType(PlantBombInputComponent.class);
            if (input.getBombCheck() == 1) {
                Entity bomb = getParentWorld().spawnEntity();
                FxglTransformComponent transformComponent = new FxglTransformComponent();
                FxglViewComponent viewComponent = new FxglViewComponent();
                PlantBombAnimationComponent plantBombAnimationComponent = new PlantBombAnimationComponent("Bomb.png");

                bomb.addAndAttach(plantBombAnimationComponent);
                bomb.addAndAttach(transformComponent);
                bomb.addAndAttach(viewComponent);

                //Get position of player
                FxglBoundingBoxComponent bbox = entity.getComponentByType(FxglBoundingBoxComponent.class);

                transformComponent.getFxglComponent().setPosition((int)(bbox.getFxglComponent().getCenterWorld().getX() / 32) *32
                        ,(int)(bbox.getFxglComponent().getCenterWorld().getY() / 32 ) * 32);
                viewComponent.getFxglComponent().addChild(plantBombAnimationComponent.getMainFrame());
                viewComponent.getFxglComponent().setZIndex(-1);

                //Set grid cua bomb -> 2
                entity.getComponentByType(FxglTransformComponent.class).setGRID((int)transformComponent.getFxglComponent().getPosition().getX()/32,
                        (int)transformComponent.getFxglComponent().getPosition().getY()/32, 2);

                input.setBombCheck(2); //set bomb check de bomb chi spawn 1 lan moi khi an

                FXGLForKtKt.getGameTimer().runOnceAfter(()->{
                    plantBombAnimationComponent.setActive(true);
                    plantBombAnimationComponent.setBombExplosion();
                    //sau 2s moi co the dat
                    FXGLForKtKt.getGameTimer().runOnceAfter(()->{
                        input.setBombCheck(0);
                        entity.getComponentByType(FxglTransformComponent.class).setGRID((int)transformComponent.getFxglComponent().getPosition().getX()/32,
                                (int)transformComponent.getFxglComponent().getPosition().getY()/32, 0);
                        getParentWorld().removeEntity(bomb);
                    }, Duration.seconds(1));
                }, Duration.seconds(2));
            }
        }
    }
}
