package com.myproject.bomberman;

import java.util.List;

public class WalkInputSystem extends System {

    @Override
    public void update(double tpf) {
        List<Entity> entityList = getParentWorld().getEntitiesByType(WalkInputComponent.class, TransformComponent.class);
        for (Entity entity : entityList) {
            WalkInputComponent input = entity.getComponentByType(WalkInputComponent.class);
            TransformComponent transform = entity.getComponentByType(TransformComponent.class);
//            WalkAnimationComponent animation = entity.getComponentByType(WalkAnimationComponent.class);
            int speed = entity.getComponentByType(TransformComponent.class).getSPEED();

            if (input.isMoveUp()) {
                transform.getFxglComponent().translateY(-speed * tpf);
            }
            if (input.isMoveDown()) {
                transform.getFxglComponent().translateY(speed * tpf);
            }
            if (input.isMoveLeft()) {
                transform.getFxglComponent().translateX(-speed * tpf);
            }
            if (input.isMoveRight()) {
                transform.getFxglComponent().translateX(speed * tpf);
            }
        }
    }
}
