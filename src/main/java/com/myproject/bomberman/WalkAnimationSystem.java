package com.myproject.bomberman;

public class WalkAnimationSystem extends System {

    @Override
    public void update(double tpf) {
        getParentWorld().getEntitiesByType(
                WalkComponent.class,
                ViewComponent.class
        ).forEach((entity) -> {
            WalkComponent input = entity.getComponentByType(WalkComponent.class);
            ViewComponent animation = entity.getComponentByType(ViewComponent.class);
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
