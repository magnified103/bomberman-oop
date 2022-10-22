package com.myproject.bomberman;

import java.util.List;

public class BrickOnFireSystem extends System {

    @Override
    public void update(double tpf) {
        List<Entity> entityList = getParentWorld().getEntitiesByType(
                FxglTransformComponent.class,
                BrickOnFireComponent.class
        );
        TerrainComponent terrain = getParentWorld().getSingletonComponent(TerrainComponent.class);
        TerrainSystem system = getParentWorld().getSingletonSystem(TerrainSystem.class);

        for (Entity brick : entityList) {
            BrickOnFireComponent timer = brick.getComponentByType(BrickOnFireComponent.class);
            FxglTransformComponent transform = brick.getComponentByType(FxglTransformComponent.class);

            timer.tick(tpf);
            if (timer.isFinished()) {
                int rowIndex = terrain.getRowIndex(transform.getY());
                int columnIndex = terrain.getColumnIndex(transform.getX());

                system.resetTile(rowIndex, columnIndex);
                getParentWorld().removeEntityComponents(brick);
            }
        }
    }
}
