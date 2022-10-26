package com.myproject.bomberman;

import com.almasb.fxgl.dsl.FXGL;

public class BombingSystem extends System {

    @Override
    public void update(double tpf) {
        TerrainComponent terrain = getParentWorld().getSingletonComponent(TerrainComponent.class);
        TerrainUtility system = getParentWorld().getSystem(TerrainUtility.class);

        getParentWorld().getEntitiesByType(
                TransformComponent.class,
                BombingComponent.class
        ).forEach((entity) -> {
            BombingComponent input = entity.getComponentByType(BombingComponent.class);
            TransformComponent transform = entity.getComponentByType(TransformComponent.class);
            int rowIndex = terrain.getRowIndex(transform.getY());
            int columnIndex = terrain.getColumnIndex(transform.getX());
            if (input.canThrowBomb() && terrain.getTile(rowIndex, columnIndex) == Tile.GRASS) {
                input.doThrowBomb();
                FXGL.play("sfxPlant.wav");
                system.spawnBomb(rowIndex, columnIndex, 2, input.getBlastRadius(), entity);
            }
            input.resetInput();
        });
    }
}
