package com.myproject.bomberman.systems;

import com.myproject.bomberman.core.TransformComponent;
import com.myproject.bomberman.components.WalkComponent;
import com.myproject.bomberman.core.System;

public class WalkSystem extends System {

    @Override
    public void update(double tpf) {
        getParentWorld().getEntitiesByTypes(
                WalkComponent.class,
                TransformComponent.class
        ).forEach((input, transform) -> {
            transform.translate(input.getMoveVector().multiply(tpf));
        });
    }
}
