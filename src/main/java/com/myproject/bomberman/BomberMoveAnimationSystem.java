package com.myproject.bomberman;

import java.util.List;

public class BomberMoveAnimationSystem extends System {

    @Override
    public void update(double tpf) {
        List<Entity> entityList = getParentWorld().getEntitiesByType(InputComponent.class,
                BomberMoveAnimationComponent.class);
        for (Entity entity : entityList) {
            InputComponent input = entity.getComponentByType(InputComponent.class);
            BomberMoveAnimationComponent animation = entity.getComponentByType(BomberMoveAnimationComponent.class);
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
