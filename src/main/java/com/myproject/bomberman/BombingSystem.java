package com.myproject.bomberman;

import java.util.List;

public class BombingSystem extends System {

    @Override
    public void update(double tpf) {
        List<Entity> entityList = getParentWorld().getEntitiesByType(
                FxglTransformComponent.class,
                BombingDataComponent.class,
                BombingInputComponent.class
        );
        TerrainComponent terrain = getParentWorld().getSingletonComponent(TerrainComponent.class);
        TerrainSystem system = getParentWorld().getSingletonSystem(TerrainSystem.class);

        for (Entity entity : entityList) {
            BombingInputComponent input = entity.getComponentByType(BombingInputComponent.class);
            FxglTransformComponent transform = entity.getComponentByType(FxglTransformComponent.class);
            BombingDataComponent data = entity.getComponentByType(BombingDataComponent.class);
            if (input.canThrowBomb()) {
                input.doThrowBomb();
                input.resetInput();
                int rowIndex = terrain.getRowIndex(transform.getY());
                int columnIndex = terrain.getColumnIndex(transform.getX());
                system.spawnBomb(rowIndex, columnIndex, 2, data.getBlastRadius(), entity);
            }
        }
    }
}
