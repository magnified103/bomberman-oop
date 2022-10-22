package com.myproject.bomberman;

import javafx.geometry.Point2D;

import java.util.List;

public class BotRandomWalkSystem extends System {

    @Override
    public void update(double tpf) {
        List<Entity> entities = getParentWorld().getEntitiesByType(
                BotRandomWalkComponent.class,
                FxglTransformComponent.class
        );
        TerrainComponent terrain = getParentWorld().getSingletonComponent(TerrainComponent.class);

        for (Entity entity : entities) {
            BotRandomWalkComponent data = entity.getComponentByType(BotRandomWalkComponent.class);
            FxglTransformComponent transform = entity.getComponentByType(FxglTransformComponent.class);
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
                transform.translate(direction.getDirectionVector().multiply(data.getSpeed() * tpf));
                data.setLastDirection(direction);
                data.setCounter(1);
            } else {
                WalkDirection direction = data.getLastDirection();
                transform.translate(direction.getDirectionVector().multiply(data.getSpeed() * tpf));
            }
        }
    }
}
