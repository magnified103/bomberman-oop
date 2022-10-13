package com.myproject.bomberman;

import java.util.List;

public class WalkAnimationSystem extends System {

    @Override
    public void update(double tpf) {
        List<Entity> entityList = getParentWorld().getEntitiesByType(InputComponent.class,
                WalkAnimationComponent.class);
        for (Entity entity : entityList) {
            InputComponent input = entity.getComponentByType(InputComponent.class);
            WalkAnimationComponent animation = entity.getComponentByType(WalkAnimationComponent.class);
            if (input.isMoveUp()) {
                if (!animation.isMoveUp()) {
                    animation.doMoveUp();
                }
            } else if (input.isMoveDown()) {
                if (!animation.isMoveDown()) {
                    animation.doMoveDown();
                }
            } else if (input.isMoveLeft()) {
                if (!animation.isMoveLeft()) {
                    animation.doMoveLeft();
                }
            } else if (input.isMoveRight()) {
                if (!animation.isMoveRight()) {
                    animation.doMoveRight();
                }
            } else {
                animation.stop();
            }
        }
    }
}
