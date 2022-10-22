package com.myproject.bomberman;

import java.util.List;

public class DeathSystem extends System {

    @Override
    public void update(double tpf) {
        List<Entity> entityList = getParentWorld().getEntitiesByType(DeathComponent.class);

        for (Entity entity : entityList) {
            DeathComponent death = entity.getComponentByType(DeathComponent.class);
            death.tick(tpf);

            if (death.isFinished()) {
                getParentWorld().removeEntityComponents(entity);
            }
        }
    }
}
