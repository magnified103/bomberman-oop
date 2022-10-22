package com.myproject.bomberman;

import javafx.util.Pair;

import java.util.*;
import java.util.function.BiConsumer;

public class CollisionSystem extends System {

    protected List<Pair<Entity, Entity>> getCollision(Collidable type1, Collidable type2) {
        List<Entity> entityList = getParentWorld().getEntitiesByType(FxglBoundingBoxComponent.class,
                CollidableComponent.class, FxglTransformComponent.class);
        List<Pair<Entity, Entity>> collisionPairs = new ArrayList<>();
        for (int i = 0; i < entityList.size(); i++) {
            for (int j = i + 1; j < entityList.size(); j++) {
                BiConsumer<Entity, Entity> check = (entity1, entity2) -> {
                    CollidableComponent collidable1 = entity1.getComponentByType(CollidableComponent.class);
                    CollidableComponent collidable2 = entity2.getComponentByType(CollidableComponent.class);
                    if (collidable1.getType() != type1 || collidable2.getType() != type2) {
                        return;
                    }
                    FxglBoundingBoxComponent bb1 = entity1.getComponentByType(FxglBoundingBoxComponent.class);
                    FxglBoundingBoxComponent bb2 = entity2.getComponentByType(FxglBoundingBoxComponent.class);
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

    protected List<Pair<Entity, Entity>> getTileCollisions(Collidable type1, Collidable type2) {
        List<Entity> entityList = getParentWorld().getEntitiesByType(
                FxglBoundingBoxComponent.class,
                CollidableComponent.class,
                FxglTransformComponent.class
        );
        TerrainComponent terrain = getParentWorld().getSingletonComponent(TerrainComponent.class);

        List<Pair<Entity, Entity>> collisionPairs = new ArrayList<>();

        for (Entity entity : entityList) {
            Collidable type = entity.getComponentByType(CollidableComponent.class).getType();
            FxglTransformComponent transform = entity.getComponentByType(FxglTransformComponent.class);
            if (type != type1) {
                continue;
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
                    if (!obj.has(CollidableComponent.class, FxglBoundingBoxComponent.class)) {
                        continue;
                    }
                    Collidable objType = obj.getComponentByType(CollidableComponent.class).getType();
                    if (objType != type2) {
                        continue;
                    }
                    FxglBoundingBoxComponent bb1 = obj.getComponentByType(FxglBoundingBoxComponent.class);
                    FxglBoundingBoxComponent bb2 = entity.getComponentByType(FxglBoundingBoxComponent.class);

                    if (bb1.checkCollision(bb2)) {
                        collisionPairs.add(new Pair<>(entity, obj));
                    }
                }
            }
        }

        return collisionPairs;
    }

    private void handleItemCollisions(double tpf) {
        List<Pair<Entity, Entity>> collisionPairs = getTileCollisions(Collidable.PASSIVE, Collidable.ITEM);

        TerrainComponent terrain = getParentWorld().getSingletonComponent(TerrainComponent.class);
        TerrainSystem system = getParentWorld().getSingletonSystem(TerrainSystem.class);

        for (Pair<Entity, Entity> pair : collisionPairs) {
            Entity player = pair.getKey();
            Entity item = pair.getValue();
            if (!player.has(
                    FxglBoundingBoxComponent.class,
                    CollidableComponent.class,
                    FxglTransformComponent.class,
                    WalkInputComponent.class,
                    BombingInputComponent.class,
                    BombingDataComponent.class
            )) {
                continue;
            }
            if (!item.has(CollidableComponent.class, FxglBoundingBoxComponent.class)) {
                continue;
            }
            FxglTransformComponent transform = item.getComponentByType(FxglTransformComponent.class);
            int itemRowIndex = terrain.getRowIndex(transform.getFxglComponent().getY());
            int itemColumnIndex = terrain.getColumnIndex(transform.getFxglComponent().getX());

            switch (item.getComponentByType(ItemComponent.class).getType()) {
                case SPEED -> player.getComponentByType(WalkInputComponent.class).raiseSpeed(50);
                case FLAME -> player.getComponentByType(BombingDataComponent.class).raiseBlastRadius(2);
                case BOMB -> player.getComponentByType(BombingInputComponent.class).raiseLimitBy(1);
            }
            getParentWorld().removeEntityComponents(item);
            system.resetTile(itemRowIndex, itemColumnIndex);
        }
    }

    private void handleFlameCollision(double tpf) {
        List<Pair<Entity, Entity>> collisionPairs = getTileCollisions(Collidable.PASSIVE, Collidable.FLAME);
        collisionPairs.addAll(getTileCollisions(Collidable.HOSTILE, Collidable.FLAME));

        for (Pair<Entity, Entity> pair : collisionPairs) {
            Entity entity = pair.getKey();
            Entity flame = pair.getValue();
            if (!entity.has(
                    FxglBoundingBoxComponent.class,
                    CollidableComponent.class,
                    FxglTransformComponent.class
            )) {
                continue;
            }
            if (!flame.has(CollidableComponent.class, FxglBoundingBoxComponent.class)) {
                continue;
            }

            getParentWorld().getSingletonSystem(TerrainSystem.class).killDynamicEntity(entity);
        }
    }

    private void handleDynamicCollisions(double tpf) {
        List<Pair<Entity, Entity>> collisionPairs = getCollision(Collidable.PASSIVE, Collidable.HOSTILE);
        List<Entity> toBeRemoved = new ArrayList<>();
        for (Pair<Entity, Entity> pair : collisionPairs) {
            toBeRemoved.add(pair.getKey());
        }
        toBeRemoved = toBeRemoved.stream().distinct().toList();
        for (Entity entity : toBeRemoved) {
            getParentWorld().getSingletonSystem(TerrainSystem.class).killDynamicEntity(entity);
        }
    }

    private void handleBombCollisions(double tpf) {
        List<Pair<Entity, Entity>> collisionPairs = getCollision(Collidable.PASSIVE, Collidable.BOMB);
        collisionPairs.addAll(getCollision(Collidable.HOSTILE, Collidable.BOMB));

        Map<BombDataComponent, Set<Entity>> bombCollisions = new HashMap<>();

        for (Pair<Entity, Entity> pair : collisionPairs) {
            Entity entity = pair.getKey();
            Entity bomb = pair.getValue();

            BombDataComponent data = bomb.getComponentByType(BombDataComponent.class);

            if (!bombCollisions.containsKey(data)) {
                bombCollisions.put(data, new HashSet<>());
            }
            bombCollisions.get(data).add(entity);
        }

        List<BombDataComponent> components = getParentWorld().getComponentsByType(BombDataComponent.class);
        for (BombDataComponent component : components) {
            if (!bombCollisions.containsKey(component)) {
                bombCollisions.put(component, new HashSet<>());
            }
        }

        for (Map.Entry<BombDataComponent, Set<Entity>> entry : bombCollisions.entrySet()) {
            BombDataComponent data = entry.getKey();
            Set<Entity> entitySet = entry.getValue();
            if (data.isInitialized()) {
                data.retain(entitySet);
            } else {
                data.setInitialized(true);
                data.setEntitiesCanStepOn(entitySet);
            }
        }
    }

    private void handleStaticCollisions(double tpf) {
        List<Pair<Entity, Entity>> collisionPairs = getTileCollisions(Collidable.PASSIVE, Collidable.STATIC);
        collisionPairs.addAll(getTileCollisions(Collidable.HOSTILE, Collidable.STATIC));
        collisionPairs.addAll(getTileCollisions(Collidable.PASSIVE, Collidable.BOMB));
        collisionPairs.addAll(getTileCollisions(Collidable.HOSTILE, Collidable.BOMB));

        TerrainComponent terrain = getParentWorld().getSingletonComponent(TerrainComponent.class);

        for (Pair<Entity, Entity> pair : collisionPairs) {
            Entity entity = pair.getKey();
            Entity obj = pair.getValue();

            if (!entity.has(
                    FxglBoundingBoxComponent.class,
                    CollidableComponent.class,
                    FxglTransformComponent.class
            )) {
                continue;
            }

            FxglTransformComponent transform = entity.getComponentByType(FxglTransformComponent.class);
            FxglTransformComponent objTransform = obj.getComponentByType(FxglTransformComponent.class);
            int i = terrain.getRowIndex(objTransform.getFxglComponent().getY());
            int j = terrain.getColumnIndex(objTransform.getFxglComponent().getX());

            FxglBoundingBoxComponent bb1 = obj.getComponentByType(FxglBoundingBoxComponent.class);
            FxglBoundingBoxComponent bb2 = entity.getComponentByType(FxglBoundingBoxComponent.class);

            if (!bb1.checkCollision(bb2)) {
                continue;
            }

            if (obj.getComponentByType(CollidableComponent.class).getType() == Collidable.BOMB) {
                if (obj.getComponentByType(BombDataComponent.class).canStepOn(entity)) {
                    continue;
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
        }
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
