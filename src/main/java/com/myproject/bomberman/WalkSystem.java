package com.myproject.bomberman;

import java.util.List;

public class WalkSystem extends System {

    @Override
    public void update(double tpf) {
        List<Entity> entityList = getParentWorld().getEntitiesByType(WalkInputComponent.class, FxglTransformComponent.class);
        for (Entity entity : entityList) {
            WalkInputComponent input = entity.getComponentByType(WalkInputComponent.class);
            FxglTransformComponent transform = entity.getComponentByType(FxglTransformComponent.class);
            if (input.isMoveUp()) {
                transform.getFxglComponent().translateY(-5);
            }
            if (input.isMoveDown()) {
                transform.getFxglComponent().translateY(5);
            }
            if (input.isMoveLeft()) {
                transform.getFxglComponent().translateX(-5);
            }
            if (input.isMoveRight()) {
                transform.getFxglComponent().translateX(5);
            }
        }
    }
}
