package com.myproject.bomberman.systems;

import com.myproject.bomberman.components.*;
import com.myproject.bomberman.components.DataComponent;
import com.myproject.bomberman.components.TimerComponent;
import com.myproject.bomberman.core.ViewComponent;

public class PortalSystem extends CollisionSystem {

    @Override
    public void update(double tpf) {
        getTileCollisions(Collidable.PASSIVE, Collidable.PORTAL).forEach((player, portal) -> {
            // if AIs haven't been destroyed
            if (getParentWorld().getComponentsByType(CollidableComponent.class).stream()
                    .anyMatch((component) -> (component.getType() == Collidable.HOSTILE))) {
                return;
            }
            portal.getComponentByType(ViewComponent.class).play();

            getParentWorld().getSystem(WorldUtility.class).pauseLevel();
            getParentWorld().addComponent(new TimerComponent(4, (timeout, tpf1) -> {
                getParentWorld().removeComponent(timeout);
                getParentWorld().getSingletonComponent(DataComponent.class)
                        .setData("gameState", "levelCompleted");
            }));
        });
    }
}