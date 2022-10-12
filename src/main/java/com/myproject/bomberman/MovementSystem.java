package com.myproject.bomberman;

import java.util.List;

public class MovementSystem extends System {

    @Override
    public void update(double tpf) {
        List<Entity> entityList = getParentWorld().getEntitiesByType(InputComponent.class, TransformComponent.class);
        for (Entity entity : entityList) {
            InputComponent input = entity.getComponentByType(InputComponent.class);
            TransformComponent transform = entity.getComponentByType(TransformComponent.class);
            if (input.moveUp) {
                transform.getFxglComponent().translateY(-5);
            }
            if (input.moveDown) {
                transform.getFxglComponent().translateY(5);
            }
            if (input.moveLeft) {
                transform.getFxglComponent().translateX(-5);
            }
            if (input.moveRight) {
                transform.getFxglComponent().translateX(5);
            }
        }
    }
}
