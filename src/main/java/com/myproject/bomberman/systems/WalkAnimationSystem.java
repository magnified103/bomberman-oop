package com.myproject.bomberman.systems;

import com.myproject.bomberman.core.ViewComponent;
import com.myproject.bomberman.components.WalkComponent;
import com.myproject.bomberman.core.System;

public class WalkAnimationSystem extends System {

    @Override
    public void update(double tpf) {
        getParentWorld().getEntitiesByTypes(
                WalkComponent.class,
                ViewComponent.class
        ).forEach((input, animation) -> {
            if ((input.isMoveDown() && input.isMoveUp())
                    || (input.isMoveLeft() && input.isMoveRight())) {
                animation.stop();
            } else if (input.isMoveUp()) {
                if ((!"up".equals(animation.getCurrentAnimationName()) || animation.isAnimationStopped())
                        && animation.hasAnimation("up")) {
                    animation.loop("up");
                }
            } else if (input.isMoveDown()) {
                if ((!"down".equals(animation.getCurrentAnimationName()) || animation.isAnimationStopped())
                        && animation.hasAnimation("down")) {
                    animation.loop("down");
                }
            } else if (input.isMoveLeft()) {
                if ((!"left".equals(animation.getCurrentAnimationName()) || animation.isAnimationStopped())
                        && animation.hasAnimation("left")) {
                    animation.loop("left");
                }
            } else if (input.isMoveRight()) {
                if ((!"right".equals(animation.getCurrentAnimationName()) || animation.isAnimationStopped())
                        && animation.hasAnimation("right")) {
                    animation.loop("right");
                }
            } else {
                animation.stop();
            }
        });
    }
}
