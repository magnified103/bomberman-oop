package com.myproject.bomberman;

import com.almasb.fxgl.dsl.FXGLForKtKt;
import javafx.util.Pair;

import java.util.*;
import java.util.function.BiConsumer;

public class CollisionSystem extends System {

    protected static class CollisionList extends ArrayList<Pair<Entity, Entity>> {

        public void forEach(BiConsumer<Entity, Entity> action) {
            super.forEach((pair) -> {
                action.accept(pair.getKey(), pair.getValue());
            });
        }
    }

    protected CollisionList getCollisions(Collidable type1, Collidable type2) {
        List<Entity> entityList = getParentWorld().getEntitiesByType(BoundingBoxComponent.class,
                CollidableComponent.class, TransformComponent.class);
        CollisionList collisionPairs = new CollisionList();
        for (int i = 0; i < entityList.size(); i++) {
            for (int j = i + 1; j < entityList.size(); j++) {
                BiConsumer<Entity, Entity> check = (entity1, entity2) -> {
                    CollidableComponent collidable1 = entity1.getComponentByType(CollidableComponent.class);
                    CollidableComponent collidable2 = entity2.getComponentByType(CollidableComponent.class);
                    if (collidable1.getType() != type1 || collidable2.getType() != type2) {
                        return;
                    }
                    BoundingBoxComponent bb1 = entity1.getComponentByType(BoundingBoxComponent.class);
                    BoundingBoxComponent bb2 = entity2.getComponentByType(BoundingBoxComponent.class);
                    if (bb1.checkCollision(bb2)) {
                        collisionPairs.add(new Pair<>(entity1, entity2));
                    }
                };
                Entity entity1 = entityList.get(i);
                Entity entity2 = entityList.get(j);
                check.accept(entity1, entity2);
                if (type1 != type2) {
                    check.accept(entity2, entity1);
                }
            }
        }

        return collisionPairs;
    }

    protected CollisionList getTileCollisions(Collidable type1, Collidable type2) {
        TerrainComponent terrain = getParentWorld().getSingletonComponent(TerrainComponent.class);

        CollisionList collisionPairs = new CollisionList();

        getParentWorld().getEntitiesByType(
                BoundingBoxComponent.class,
                CollidableComponent.class,
                TransformComponent.class
        ).forEach((entity) -> {
            Collidable type = entity.getComponentByType(CollidableComponent.class).getType();
            TransformComponent transform = entity.getComponentByType(TransformComponent.class);
            if (type != type1) {
                return;
            }
            int entityRowIndex = terrain.getRowIndex(transform.getFxglComponent().getY());
            int entityColumnIndex = terrain.getColumnIndex(transform.getFxglComponent().getX());

            // search for neighbor tiles
            for (int i = entityRowIndex - 1; i <= entityRowIndex + 1; i++) {
                for (int j = entityColumnIndex - 1; j <= entityColumnIndex + 1; j++) {
                    if (!terrain.validTile(i, j) || terrain.getTile(i, j) == Tile.GRASS) {
                        continue;
                    }
                    Entity obj = terrain.getEntity(i, j);
                    if (!obj.has(CollidableComponent.class, BoundingBoxComponent.class)) {
                        continue;
                    }
                    Collidable objType = obj.getComponentByType(CollidableComponent.class).getType();
                    if (objType != type2) {
                        continue;
                    }
                    BoundingBoxComponent bb1 = obj.getComponentByType(BoundingBoxComponent.class);
                    BoundingBoxComponent bb2 = entity.getComponentByType(BoundingBoxComponent.class);

                    if (bb1.checkCollision(bb2)) {
                        collisionPairs.add(new Pair<>(entity, obj));
                    }
                }
            }
        });

        return collisionPairs;
    }

    private void handleItemCollisions(double tpf) {
        TerrainComponent terrain = getParentWorld().getSingletonComponent(TerrainComponent.class);
        TerrainUtility system = getParentWorld().getSystem(TerrainUtility.class);

        getTileCollisions(Collidable.PASSIVE, Collidable.ITEM).forEach((player, item) -> {
            if (!player.has(
                    BoundingBoxComponent.class,
                    CollidableComponent.class,
                    TransformComponent.class,
                    WalkComponent.class,
                    BombingComponent.class
            )) {
                return;
            }
            if (!item.has(CollidableComponent.class, BoundingBoxComponent.class)) {
                return;
            }
            TransformComponent transform = item.getComponentByType(TransformComponent.class);
            int itemRowIndex = terrain.getRowIndex(transform.getFxglComponent().getY());
            int itemColumnIndex = terrain.getColumnIndex(transform.getFxglComponent().getX());

            switch (terrain.getTile(itemRowIndex, itemColumnIndex)) {
                case SPEED_ITEM -> player.getComponentByType(WalkComponent.class).raiseSpeed(50);
                case FLAME_ITEM -> player.getComponentByType(BombingComponent.class).raiseBlastRadius(1);
                case BOMB_ITEM -> player.getComponentByType(BombingComponent.class).raiseLimitBy(1);
            }
            getParentWorld().removeEntityComponents(item);
            system.resetTile(itemRowIndex, itemColumnIndex);
        });
    }

    private void handleFlameCollision(double tpf) {
        CollisionList collisionPairs = getTileCollisions(Collidable.PASSIVE, Collidable.FLAME);
        collisionPairs.addAll(getTileCollisions(Collidable.HOSTILE, Collidable.FLAME));

        collisionPairs.forEach((entity, flame) -> {
            if (!entity.has(
                    BoundingBoxComponent.class,
                    CollidableComponent.class,
                    TransformComponent.class
            )) {
                return;
            }
            if (!flame.has(CollidableComponent.class, BoundingBoxComponent.class)) {
                return;
            }
            if (entity.has(AIRandomComponent.class)) FXGLForKtKt.inc("Score",+100);
            getParentWorld().getSystem(TerrainUtility.class).killDynamicEntity(entity);
        });
    }

    private void handleDynamicCollisions(double tpf) {
        List<Pair<Entity, Entity>> collisionPairs = getCollisions(Collidable.PASSIVE, Collidable.HOSTILE);
        List<Entity> toBeRemoved = new ArrayList<>();
        for (Pair<Entity, Entity> pair : collisionPairs) {
            toBeRemoved.add(pair.getKey());
        }
        toBeRemoved = toBeRemoved.stream().distinct().toList();
        for (Entity entity : toBeRemoved) {
            getParentWorld().getSystem(TerrainUtility.class).killDynamicEntity(entity);
        }
    }

    private void handleBombCollisions(double tpf) {
        CollisionList collisionPairs = getTileCollisions(Collidable.PASSIVE, Collidable.BOMB);
        collisionPairs.addAll(getTileCollisions(Collidable.HOSTILE, Collidable.BOMB));

        Map<BombDataComponent, Set<Entity>> bombCollisions = new HashMap<>();

        collisionPairs.forEach((entity, bomb) -> {
            BombDataComponent data = bomb.getComponentByType(BombDataComponent.class);

            if (!bombCollisions.containsKey(data)) {
                bombCollisions.put(data, new HashSet<>());
            }
            bombCollisions.get(data).add(entity);
        });

        getParentWorld().getComponentsByType(BombDataComponent.class).forEach((component) -> {
            if (!bombCollisions.containsKey(component)) {
                bombCollisions.put(component, new HashSet<>());
            }
        });

        bombCollisions.forEach((data, entitySet) -> {
            if (data.isInitialized()) {
                data.retain(entitySet);
            } else {
                data.setInitialized(true);
                data.setEntitiesCanStepOn(entitySet);
            }
        });
    }

    private void handleStaticCollisions(double tpf) {
        CollisionList collisionPairs = getTileCollisions(Collidable.PASSIVE, Collidable.STATIC);
        collisionPairs.addAll(getTileCollisions(Collidable.HOSTILE, Collidable.STATIC));
        collisionPairs.addAll(getTileCollisions(Collidable.PASSIVE, Collidable.BOMB));
        collisionPairs.addAll(getTileCollisions(Collidable.HOSTILE, Collidable.BOMB));

        TerrainComponent terrain = getParentWorld().getSingletonComponent(TerrainComponent.class);

        collisionPairs.forEach((entity, obj) -> {
            if (!entity.has(
                    BoundingBoxComponent.class,
                    CollidableComponent.class,
                    TransformComponent.class
            )) {
                return;
            }

            TransformComponent transform = entity.getComponentByType(TransformComponent.class);
            TransformComponent objTransform = obj.getComponentByType(TransformComponent.class);
            int i = terrain.getRowIndex(objTransform.getFxglComponent().getY());
            int j = terrain.getColumnIndex(objTransform.getFxglComponent().getX());

            BoundingBoxComponent bb1 = obj.getComponentByType(BoundingBoxComponent.class);
            BoundingBoxComponent bb2 = entity.getComponentByType(BoundingBoxComponent.class);

            if (!bb1.checkCollision(bb2)) {
                return;
            }

            if (obj.getComponentByType(CollidableComponent.class).getType() == Collidable.BOMB) {
                if (obj.getComponentByType(BombDataComponent.class).canStepOn(entity)) {
                    return;
                }
            }

            final double LIM = 1e9;
            final double EPS = 1e-2;

            // right
            double positiveX = LIM;
            if ((!terrain.validTile(i, j + 1) || terrain.canStepOn(i, j + 1, entity))
                    && bb1.getMinXWorld() <= bb2.getMinXWorld()
                    && bb2.getMinXWorld() <= bb1.getMaxXWorld()
                    && bb1.getMaxXWorld() <= bb2.getMaxXWorld()) {
                positiveX = bb1.getMaxXWorld() - bb2.getMinXWorld();
            }

            // left
            double negativeX = LIM;
            if ((!terrain.validTile(i, j - 1) || terrain.canStepOn(i, j - 1, entity))
                    && bb1.getMinXWorld() <= bb2.getMaxXWorld()
                    && bb2.getMaxXWorld() <= bb1.getMaxXWorld()
                    && bb2.getMinXWorld() <= bb1.getMinXWorld()) {
                negativeX = bb2.getMaxXWorld() - bb1.getMinXWorld();
            }

            // down
            double positiveY = LIM;
            if ((!terrain.validTile(i + 1, j) || terrain.canStepOn(i + 1, j, entity))
                    && bb1.getMinYWorld() <= bb2.getMinYWorld()
                    && bb2.getMinYWorld() <= bb1.getMaxYWorld()
                    && bb1.getMaxYWorld() <= bb2.getMaxYWorld()) {
                positiveY = bb1.getMaxYWorld() - bb2.getMinYWorld();
            }

            // up
            double negativeY = LIM;
            if ((!terrain.validTile(i - 1, j) || terrain.canStepOn(i - 1, j, entity))
                    && bb1.getMinYWorld() <= bb2.getMaxYWorld()
                    && bb2.getMaxYWorld() <= bb1.getMaxYWorld()
                    && bb2.getMinYWorld() <= bb1.getMinYWorld()) {
                negativeY = bb2.getMaxYWorld() - bb1.getMinYWorld();
            }

            double minDistance = Math.min(Math.min(Math.min(positiveX, negativeX), positiveY), negativeY);
            if (minDistance < LIM) {
                if (positiveX < LIM) {
                    transform.getFxglComponent().translateX(minDistance + EPS);
                }
                if (negativeX < LIM) {
                    transform.getFxglComponent().translateX(-minDistance - EPS);
                }
                if (positiveY < LIM) {
                    transform.getFxglComponent().translateY(minDistance + EPS);
                }
                if (negativeY < LIM) {
                    transform.getFxglComponent().translateY(-minDistance - EPS);
                }
            }
        });
    }

    @Override
    public void update(double tpf) {
        handleDynamicCollisions(tpf);
        handleItemCollisions(tpf);
        handleFlameCollision(tpf);
        handleBombCollisions(tpf);
        handleStaticCollisions(tpf);
    }
}
