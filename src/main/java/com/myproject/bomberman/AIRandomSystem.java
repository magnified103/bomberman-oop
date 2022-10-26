package com.myproject.bomberman;

import java.util.List;

public class AIRandomSystem extends System {

    @Override
    public void update(double tpf) {
        List<Entity> entities = getParentWorld().getEntitiesByType(
                AIRandomComponent.class,
                WalkComponent.class,
                TransformComponent.class
        );
        TerrainComponent terrain = getParentWorld().getSingletonComponent(TerrainComponent.class);

        for (Entity entity : entities) {
            AIRandomComponent data = entity.getComponentByType(AIRandomComponent.class);
            WalkComponent walk = entity.getComponentByType(WalkComponent.class);
            TransformComponent transform = entity.getComponentByType(TransformComponent.class);
            int rowIndex = terrain.getRowIndex(transform.getY());
            int columnIndex = terrain.getColumnIndex(transform.getX());

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
    }
}
