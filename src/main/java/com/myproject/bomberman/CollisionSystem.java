package com.myproject.bomberman;

import com.almasb.fxgl.dsl.FXGLForKtKt;
import com.almasb.fxgl.physics.CollisionResult;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class CollisionSystem extends System {

    protected void getCollision(Collidable type1, Collidable type2, List<Entity> return1, List<Entity> return2) {
        List<Entity> entityList = getParentWorld().getEntitiesByType(FxglBoundingBoxComponent.class,
                CollidableComponent.class, FxglTransformComponent.class);
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
                    if (bb1.getFxglComponent().checkCollision(bb2.getFxglComponent(), new CollisionResult())) {
                        return1.add(entity1);
                        return2.add(entity2);
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
    }

    public void handleStaticCollisions(double tpf) {
        List<Entity> entityList1 = new ArrayList<>();
        List<Entity> entityList2 = new ArrayList<>();

//        getCollision(Collidable.STATIC, Collidable.PASSIVE, entityList1, entityList2);
//        getCollision(Collidable.STATIC, Collidable.HOSTILE, entityList1, entityList2);
        getCollision(Collidable.MULTIFORM, Collidable.PASSIVE, entityList1, entityList2);
        getCollision(Collidable.MULTIFORM, Collidable.HOSTILE, entityList1, entityList2);

        for (int i = 0; i < entityList1.size(); i++) {
            Entity entity1 = entityList1.get(i);
            Entity entity2 = entityList2.get(i);
            FxglBoundingBoxComponent bb1 = entity1.getComponentByType(FxglBoundingBoxComponent.class);
            FxglBoundingBoxComponent bb2 = entity2.getComponentByType(FxglBoundingBoxComponent.class);
            FxglTransformComponent transformComponent = entity2.getComponentByType(FxglTransformComponent.class);

            final double LIM = 1e9;
            final double EPS = 1e-2;

            double positiveX = LIM;
            if (bb1.getMinXWorld() <= bb2.getMinXWorld() && bb2.getMinXWorld() <= bb1.getMaxXWorld()) {
                positiveX = bb1.getMaxXWorld() - bb2.getMinXWorld();
            }

            double negativeX = LIM;
            if (bb1.getMinXWorld() <= bb2.getMaxXWorld() && bb2.getMaxXWorld() <= bb1.getMaxXWorld()) {
                negativeX = bb2.getMaxXWorld() - bb1.getMinXWorld();
            }

            double positiveY = LIM;
            if (bb1.getMinYWorld() <= bb2.getMinYWorld() && bb2.getMinYWorld() <= bb1.getMaxYWorld()) {
                positiveY = bb1.getMaxYWorld() - bb2.getMinYWorld();
            }

            double negativeY = LIM;
            if (bb1.getMinYWorld() <= bb2.getMaxYWorld() && bb2.getMaxYWorld() <= bb1.getMaxYWorld()) {
                negativeY = bb2.getMaxYWorld() - bb1.getMinYWorld();
            }

            double minDistance = Math.min(Math.min(Math.min(positiveX, negativeX), positiveY), negativeY);
            if (minDistance < LIM) {
                if (positiveX < LIM) {
                    transformComponent.getFxglComponent().translateX(minDistance + EPS);
                }
                if (negativeX < LIM) {
                    transformComponent.getFxglComponent().translateX(-minDistance - EPS);
                }
                if (positiveY < LIM) {
                    transformComponent.getFxglComponent().translateY(minDistance + EPS);
                }
                if (negativeY < LIM) {
                    transformComponent.getFxglComponent().translateY(-minDistance - EPS);
                }
            }
        }
    }

    public void handleFlameWithBrickCollisions(double tpf) {
        List<Entity> entityList1 = new ArrayList<>();
        List<Entity> entityList2 = new ArrayList<>();
        getCollision(Collidable.MULTIFORM, Collidable.FLAME, entityList1, entityList2);

        for (int i = 0; i < entityList1.size(); i++) {
            Entity brick = entityList1.get(i);
            Entity flame = entityList2.get(i);

            if ((int)flame.getComponentByType(FxglTransformComponent.class).getFxglComponent().getX()/32 == (int)brick.getComponentByType(FxglTransformComponent.class).getFxglComponent().getX()/32
                    || (int)flame.getComponentByType(FxglTransformComponent.class).getFxglComponent().getY()/32 == (int)brick.getComponentByType(FxglTransformComponent.class).getFxglComponent().getY()/32){

                brick.getComponentByType(BrickComponent.class).breakBrick();
                brick.detach(CollidableComponent.class);
                FXGLForKtKt.getGameTimer().runOnceAfter(()->{
                    getParentWorld().getEntitiesByType(WalkInputComponent.class, PlantBombInputComponent.class
                            , FxglTransformComponent.class, FxglViewComponent.class).get(0)
                                    .getComponentByType(WalkInputComponent.class)
                                            .setGRID((int)brick.getComponentByType(FxglTransformComponent.class).getFxglComponent().getY()/32,
                                                    (int)brick.getComponentByType(FxglTransformComponent.class).getFxglComponent().getX()/32,
                                                    0);
                    getParentWorld().removeEntity(brick);
                },Duration.seconds(1));
            }
        }
    }

    public void handleFlameWithItemCollisions(double tpf) {
        List<Entity> entityList1 = new ArrayList<>();
        List<Entity> entityList2 = new ArrayList<>();
        getCollision(Collidable.FLAME, Collidable.ITEM, entityList1, entityList2);

        for (int i = 0; i < entityList1.size(); i++) {
            Entity flame = entityList1.get(i);
            Entity item = entityList2.get(i);
            item.detach(CollidableComponent.class);
            FXGLForKtKt.getGameTimer().runOnceAfter(()->{
                getParentWorld().removeEntity(item);
            },Duration.seconds(flame.getComponentByType(FlameComponent.class).getFlameDuration()));
        }
    }

    public void handleItemCollisions(double tpf) {
        List<Entity> entityList1 = new ArrayList<>();
        List<Entity> entityList2 = new ArrayList<>();
        getCollision(Collidable.PASSIVE, Collidable.ITEM, entityList1, entityList2);

        for (int i = 0; i < entityList1.size(); i++) {
            Entity player = entityList1.get(i);
            Entity item = entityList2.get(i);
            switch (item.getComponentByType(ItemComponent.class).getType()) {
                case SPEED -> player.getComponentByType(WalkInputComponent.class).setSPEED(50);
                case FLAME -> player.getComponentByType(WalkInputComponent.class).addFLAME_SIZE();
                case BOMB -> player.getComponentByType(WalkInputComponent.class).addBOMB_CAPACITY();
            }
            getParentWorld().removeEntity(item);
        }
    }

    public void handleDynamicCollisions(double tpf) {
        List<Entity> entityList1 = new ArrayList<>();
        List<Entity> entityList2 = new ArrayList<>();
        getCollision(Collidable.PASSIVE, Collidable.HOSTILE, entityList1, entityList2);
        List<Entity> toBeRemoved = entityList1.stream().distinct().toList();
        for (Entity entity : toBeRemoved) {
            getParentWorld().removeEntityComponents(entity);
        }
    }

    public void handleTileCollisions(double tpf) {
        List<Entity> entityList = getParentWorld().getEntitiesByType(
                FxglBoundingBoxComponent.class,
                CollidableComponent.class,
                FxglTransformComponent.class
        );
        TerrainComponent terrain = getParentWorld().getSingletonComponent(TerrainComponent.class);
        for (Entity entity : entityList) {
            Collidable type = entity.getComponentByType(CollidableComponent.class).getType();
            FxglTransformComponent transform = entity.getComponentByType(FxglTransformComponent.class);
            if (type != Collidable.PASSIVE && type != Collidable.HOSTILE) {
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
                    if (objType != Collidable.STATIC) {
                        continue;
                    }
                    FxglBoundingBoxComponent bb1 = obj.getComponentByType(FxglBoundingBoxComponent.class);
                    FxglBoundingBoxComponent bb2 = entity.getComponentByType(FxglBoundingBoxComponent.class);

                    if (!bb1.checkCollision(bb2)) {
                        continue;
                    }

                    FxglTransformComponent transformComponent = entity.getComponentByType(FxglTransformComponent.class);

                    final double LIM = 1e9;
                    final double EPS = 1e-2;

                    // right
                    double positiveX = LIM;
                    if ((!terrain.validTile(i, j + 1) || terrain.getTile(i, j + 1) == Tile.GRASS)
                            && bb1.getMinXWorld() <= bb2.getMinXWorld()
                            && bb2.getMinXWorld() <= bb1.getMaxXWorld()
                            && bb1.getMaxXWorld() <= bb2.getMaxXWorld()) {
                        positiveX = bb1.getMaxXWorld() - bb2.getMinXWorld();
                    }

                    // left
                    double negativeX = LIM;
                    if ((!terrain.validTile(i, j - 1) || terrain.getTile(i, j - 1) == Tile.GRASS)
                            && bb1.getMinXWorld() <= bb2.getMaxXWorld()
                            && bb2.getMaxXWorld() <= bb1.getMaxXWorld()
                            && bb2.getMinXWorld() <= bb1.getMinXWorld()) {
                        negativeX = bb2.getMaxXWorld() - bb1.getMinXWorld();
                    }

                    // down
                    double positiveY = LIM;
                    if ((!terrain.validTile(i + 1, j) || terrain.getTile(i + 1, j) == Tile.GRASS)
                            && bb1.getMinYWorld() <= bb2.getMinYWorld()
                            && bb2.getMinYWorld() <= bb1.getMaxYWorld()
                            && bb1.getMaxYWorld() <= bb2.getMaxYWorld()) {
                        positiveY = bb1.getMaxYWorld() - bb2.getMinYWorld();
                    }

                    // up
                    double negativeY = LIM;
                    if ((!terrain.validTile(i - 1, j) || terrain.getTile(i - 1, j) == Tile.GRASS)
                            && bb1.getMinYWorld() <= bb2.getMaxYWorld()
                            && bb2.getMaxYWorld() <= bb1.getMaxYWorld()
                            && bb2.getMinYWorld() <= bb1.getMinYWorld()) {
                        negativeY = bb2.getMaxYWorld() - bb1.getMinYWorld();
                    }

                    double minDistance = Math.min(Math.min(Math.min(positiveX, negativeX), positiveY), negativeY);
                    if (minDistance < LIM) {
                        if (positiveX < LIM) {
                            transformComponent.getFxglComponent().translateX(minDistance + EPS);
                        }
                        if (negativeX < LIM) {
                            transformComponent.getFxglComponent().translateX(-minDistance - EPS);
                        }
                        if (positiveY < LIM) {
                            transformComponent.getFxglComponent().translateY(minDistance + EPS);
                        }
                        if (negativeY < LIM) {
                            transformComponent.getFxglComponent().translateY(-minDistance - EPS);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void update(double tpf) {
        handleDynamicCollisions(tpf);
        handleStaticCollisions(tpf);
        handleFlameWithBrickCollisions(tpf);
        handleFlameWithItemCollisions(tpf);
        handleItemCollisions(tpf);
        handleTileCollisions(tpf);
    }
}
