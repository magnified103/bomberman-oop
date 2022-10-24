package com.myproject.bomberman;

import java.util.List;

public class BotTargetedWalkSystem extends System {

    @Override
    public void update(double tpf) {
        List<Entity> entities = getParentWorld().getEntitiesByType(
                BotTargetedWalkComponent.class,
                FxglTransformComponent.class
        );
        TerrainComponent terrain = getParentWorld().getSingletonComponent(TerrainComponent.class);

        for (Entity entity : entities) {
            BotTargetedWalkComponent data = entity.getComponentByType(BotTargetedWalkComponent.class);
            FxglTransformComponent transform = entity.getComponentByType(FxglTransformComponent.class);
            int rowIndex = terrain.getRowIndex(transform.getY());
            int columnIndex = terrain.getColumnIndex(transform.getX());


        }
    }
}
