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

            if (data.overflow() || data.coinFlip()) {
                Point2D directionVector = data.randomDirectionVector();
                transform.translate(directionVector.multiply(data.getSpeed() * tpf));
                data.setLastDirectionVector(directionVector);
                data.setCounter(1);
            } else {
                Point2D directionVector = data.getLastDirectionVector();
                transform.translate(directionVector.multiply(data.getSpeed() * tpf));
            }
        }
    }
}
