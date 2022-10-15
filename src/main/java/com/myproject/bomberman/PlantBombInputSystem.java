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

                List<Entity> list = getParentWorld().getEntitiesByType(BoundingBoxComponent.class);
                //Get position of player
                BoundingBoxComponent bbox = list.get(0).getComponentByType(BoundingBoxComponent.class);

                transformComponent.getFxglComponent().setPosition((int)((bbox.getFxglComponent().getMinXWorld() + 14/2) / 32) *32
                        ,(int)(((bbox.getFxglComponent().getMinYWorld() + 16/2)) / 32 ) * 32);


                viewComponent.getFxglComponent().addChild(plantBombAnimationComponent.getMainFrame());
                input.setBombCheck(2); //set bomb check de bomb chi spawn 1 lan moi khi an

                getGameTimer().runOnceAfter(()->{
                    getParentWorld().removeEntity(bomb);
                    input.setBombCheck(0);
                    //sau 2s moi co the dat
                }, Duration.seconds(2));
            }
        }
    }
}
