package com.myproject.bomberman.systems;

import com.almasb.fxgl.dsl.FXGL;
import com.myproject.bomberman.components.BombingComponent;
import com.myproject.bomberman.components.TerrainComponent;
import com.myproject.bomberman.components.Tile;
import com.myproject.bomberman.core.TransformComponent;
import com.myproject.bomberman.core.System;

public class BombingSystem extends System {

    @Override
    public void update(double tpf) {
        TerrainComponent terrain = getParentWorld().getSingletonComponent(TerrainComponent.class);
        TerrainUtility system = getParentWorld().getSystem(TerrainUtility.class);

        getParentWorld().getEntitiesByTypes(
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
