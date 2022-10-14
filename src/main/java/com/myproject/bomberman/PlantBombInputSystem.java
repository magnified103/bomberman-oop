package com.myproject.bomberman;

import java.util.List;

public class PlantBombInputSystem extends System {
    @Override
    public void update(double tpf) {
        List<Entity> entityList = getParentWorld().getEntitiesByType(PlantBombInputComponent.class);
        for (Entity entity : entityList) {
            PlantBombInputComponent input = entity.getComponentByType(PlantBombInputComponent.class);
            if (input.isPlantBomb()) {
//                Entity bomb = getParentWorld().spawnEntity();
//                TransformComponent transformComponent = new TransformComponent();
//                ViewComponent viewComponent = new ViewComponent();
//                PlantBombAnimationComponent plantBombAnimationComponent = new PlantBombAnimationComponent("Bomb.png");
//
//                bomb.attachComponent(plantBombAnimationComponent);
//                bomb.attachComponent(transformComponent);
//                bomb.attachComponent(viewComponent);
//
//                transformComponent.getFxglComponent().setPosition(0,0);
//                viewComponent.getFxglComponent().addChild(plantBombAnimationComponent.getMainFrame());

            }
        }
    }
}
