package com.myproject.bomberman;

import java.util.List;

public class AIPathFindSystem extends System {

    @Override
    public void update(double tpf) {
        List<Entity> entities = getParentWorld().getEntitiesByType(
                AIPathFindComponent.class,
                TransformComponent.class
        );
        TerrainComponent terrain = getParentWorld().getSingletonComponent(TerrainComponent.class);

        for (Entity entity : entities) {
            AIPathFindComponent data = entity.getComponentByType(AIPathFindComponent.class);
            TransformComponent transform = entity.getComponentByType(TransformComponent.class);
            int rowIndex = terrain.getRowIndex(transform.getY());
            int columnIndex = terrain.getColumnIndex(transform.getX());


        }
    }
}
