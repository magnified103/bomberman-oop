package com.myproject.bomberman.systems;

import com.myproject.bomberman.core.Entity;
import com.myproject.bomberman.components.*;
import com.myproject.bomberman.core.System;
import com.myproject.bomberman.core.TransformComponent;
import javafx.util.Pair;

import java.util.*;

public class AIPathFindSystem extends System {

    @Override
    public void update(double tpf) {
        TerrainComponent terrain = getParentWorld().getSingletonComponent(TerrainComponent.class);

        List<Entity> passiveEntities = new ArrayList<>();
        List<Entity> hostileEntities = new ArrayList<>();

        getParentWorld().getEntitiesByTypes(
                TransformComponent.class,
                CollidableComponent.class,
                WalkComponent.class
        ).forEach((entity) -> {
            Collidable type = entity.getComponentByType(CollidableComponent.class).getType();
            if (type == Collidable.PASSIVE) {
                passiveEntities.add(entity);
            }
            if (type == Collidable.HOSTILE && entity.has(AIPathFindComponent.class)) {
                hostileEntities.add(entity);
            }
        });

        int[][] dist = new int[terrain.getNumberOfRows()][terrain.getNumberOfColumns()];

        for (Entity entity : hostileEntities) {
            TransformComponent transform = entity.getComponentByType(TransformComponent.class);
            WalkComponent walk = entity.getComponentByType(WalkComponent.class);
            AIPathFindComponent ai = entity.getComponentByType(AIPathFindComponent.class);
            int rowIndex = terrain.getRowIndex(transform.getY());
            int columnIndex = terrain.getColumnIndex(transform.getX());

            for (int i = 0; i < terrain.getNumberOfRows(); i++) {
                for (int j = 0; j < terrain.getNumberOfColumns(); j++) {
                    dist[i][j] = -1;
                }
            }

            Queue<Pair<Integer, Integer>> queue = new ArrayDeque<>();

            for (Entity e : passiveEntities) {
                TransformComponent tf = e.getComponentByType(TransformComponent.class);
                int i = terrain.getRowIndex(tf.getY());
                int j = terrain.getColumnIndex(tf.getX());

                dist[i][j] = 0;
                queue.add(new Pair<>(i, j));
            }

            int radius = ai.getViewRadius();

            while (!queue.isEmpty()) {
                Pair<Integer, Integer> pair = queue.peek();
                queue.remove();
                int i = pair.getKey();
                int j = pair.getValue();

                if (i == rowIndex && j == columnIndex) {
                    break;
                }

                if (dist[i][j] >= radius) {
                    break;
                }

                for (WalkDirection direction : WalkDirection.values()) {
                    int ii = i + direction.getIntegralY();
                    int jj = j + direction.getIntegralX();
                    if (ii >= 0 && ii < terrain.getNumberOfRows() && jj >= 0 && jj < terrain.getNumberOfColumns()) {
                        if (terrain.canStepOn(ii, jj, entity) && dist[ii][jj] == -1) {
                            dist[ii][jj] = dist[i][j] + 1;
                            queue.add(new Pair<>(ii, jj));
                        }
                    }
                }
            }

            if (dist[rowIndex][columnIndex] == 0) {
                // do nothing
                continue;
            }
            boolean foundMove = false;
            for (WalkDirection direction : WalkDirection.values()) {
                int ii = rowIndex + direction.getIntegralY();
                int jj = columnIndex + direction.getIntegralX();
                if (ii >= 0 && ii < terrain.getNumberOfRows() && jj >= 0 && jj < terrain.getNumberOfColumns()) {
                    if (dist[ii][jj] != -1 && dist[ii][jj] + 1 == dist[rowIndex][columnIndex]) {
                        walk.setMove(direction);
                        foundMove = true;
                        break;
                    }
                }
            }

            if (!foundMove) {
                getParentWorld().getSystem(AIRandomSystem.class).randomMove(rowIndex, columnIndex,
                        ai.getFallback(), walk);
            }
        }
    }
}
