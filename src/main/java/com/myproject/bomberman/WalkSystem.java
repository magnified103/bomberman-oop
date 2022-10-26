package com.myproject.bomberman;

public class WalkSystem extends System {

    @Override
    public void update(double tpf) {
        getParentWorld().getEntitiesByType(
                WalkComponent.class,
                TransformComponent.class
        ).forEach((entity) -> {
            WalkComponent input = entity.getComponentByType(WalkComponent.class);
            TransformComponent transform = entity.getComponentByType(TransformComponent.class);
            transform.translate(input.getMoveVector().multiply(tpf));
        });
    }
}
