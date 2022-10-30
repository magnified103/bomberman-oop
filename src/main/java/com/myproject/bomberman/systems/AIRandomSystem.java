package com.myproject.bomberman.systems;

import com.myproject.bomberman.core.Entity;
import com.myproject.bomberman.components.*;
import com.myproject.bomberman.core.System;
import com.myproject.bomberman.core.TransformComponent;

import java.util.List;

public class AIRandomSystem extends System {

    public void randomMove(int rowIndex, int columnIndex, AIRandomComponent data, WalkComponent walk) {
        TerrainComponent terrain = getParentWorld().getSingletonComponent(TerrainComponent.class);

        data.compare(rowIndex, columnIndex);
        data.setIndex(rowIndex, columnIndex);

        data.signal(WalkDirection.UP, terrain.validTile(rowIndex - 1, columnIndex) && (
                terrain.getTile(rowIndex - 1, columnIndex) == Tile.GRASS
                        || terrain.getTile(rowIndex - 1, columnIndex).isItem()));

        data.signal(WalkDirection.DOWN, terrain.validTile(rowIndex + 1, columnIndex) && (
                terrain.getTile(rowIndex + 1, columnIndex) == Tile.GRASS
                        || terrain.getTile(rowIndex + 1, columnIndex).isItem()));

        data.signal(WalkDirection.LEFT, terrain.validTile(rowIndex, columnIndex - 1) && (
                terrain.getTile(rowIndex, columnIndex - 1) == Tile.GRASS
                        || terrain.getTile(rowIndex, columnIndex - 1).isItem()));

        data.signal(WalkDirection.RIGHT, terrain.validTile(rowIndex, columnIndex + 1) && (
                terrain.getTile(rowIndex, columnIndex + 1) == Tile.GRASS
                        || terrain.getTile(rowIndex, columnIndex + 1).isItem()));

        if (data.overflow() || data.coinFlip()) {
            WalkDirection direction = data.randomDirection();
            walk.setMove(direction);
            data.setLastDirection(direction);
            data.setCounter(1);
        } else {
            WalkDirection direction = data.getLastDirection();
            walk.setMove(direction);
        }
    }

    @Override
    public void update(double tpf) {
        TerrainComponent terrain = getParentWorld().getSingletonComponent(TerrainComponent.class);

        getParentWorld().getEntitiesByTypes(
                AIRandomComponent.class,
                WalkComponent.class,
                TransformComponent.class
        ).forEach((data, walk, transform) -> {
            int rowIndex = terrain.getRowIndex(transform.getY());
            int columnIndex = terrain.getColumnIndex(transform.getX());

            randomMove(rowIndex, columnIndex, data, walk);
        });
    }
}
