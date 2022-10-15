package com.myproject.bomberman;

import java.util.List;

public class WalkSystem extends System {

    @Override
    public void update(double tpf) {
        List<Entity> entityList = getParentWorld().getEntitiesByType(WalkInputComponent.class, FxglTransformComponent.class);
        for (Entity entity : entityList) {
            WalkInputComponent input = entity.getComponentByType(WalkInputComponent.class);
//            WalkAnimationComponent animation = entity.getComponentByType(WalkAnimationComponent.class);
            FxglTransformComponent transform = entity.getComponentByType(FxglTransformComponent.class);
            int speed = entity.getComponentByType(FxglTransformComponent.class).getSPEED();
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
