package com.myproject.bomberman;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.*;
import com.almasb.fxgl.entity.components.*;
import com.almasb.fxgl.physics.*;
import com.myproject.bomberman.components.*;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BombermanFactory implements EntityFactory {

    @Spawns("player")
    public Entity newPlayer(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(BomberData.EntityType.PLAYER)
                .with(new BomberComponent())
                .with(new CollidableComponent(true))
                .bbox(new HitBox(new Point2D(6,6), BoundingShape.box(20, 22)))
                .build();
    }

    @Spawns("balloon")
    public Entity newBalloon(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(BomberData.EntityType.ENEMY)
                .with(new BalloonComponent())
                .with(new CollidableComponent(true))
                .bbox(new HitBox(BoundingShape.box(32,32)))
                .build();
    }

    @Spawns("shelter")
    public Entity newShelter(SpawnData data) {
        return FXGL.entityBuilder(data)
                .viewWithBBox("Shelter.png")
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("grass")
    public Entity newGrass(SpawnData data) {
        return FXGL.entityBuilder(data)
                .viewWithBBox("Grass.png")
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("wall")
    public Entity newWall(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(BomberData.EntityType.WALL)
                .viewWithBBox("Wall.png")
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("brick")
    public Entity newBrick(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(BomberData.EntityType.BRICK)
                .with(new BrickComponent())
                .bbox(BoundingShape.box(32,32))
                .zIndex(-1)
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("boom")
    public Entity newBoom(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(BomberData.EntityType.BOOM)
                .with(new CollidableComponent(true))
                .bbox(BoundingShape.circle(14))
                .zIndex(-1)
                .build();
    }
//    Flame start
    @Spawns("flameRight")
    public Entity newFlameRight(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(BomberData.EntityType.FLAME)
                .with(new CollidableComponent(true))
                .with(new FlamesComponent("right"))
                .bbox(BoundingShape.box(32,32))
                .zIndex(-1)
                .build();
    }
    @Spawns("flameLeft")
    public Entity newFlameLeft(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(BomberData.EntityType.FLAME)
                .with(new CollidableComponent(true))
                .with(new FlamesComponent("left"))
                .bbox(BoundingShape.box(32,32))
                .zIndex(-1)
                .build();
    }
    @Spawns("flameUp")
    public Entity newFlameUp(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(BomberData.EntityType.FLAME)
                .with(new CollidableComponent(true))
                .with(new FlamesComponent("up"))
                .bbox(BoundingShape.box(32,32))
                .zIndex(-1)
                .build();
    }
    @Spawns("flameDown")
    public Entity newFlameDown(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(BomberData.EntityType.FLAME)
                .with(new CollidableComponent(true))
                .with(new FlamesComponent("down"))
                .bbox(BoundingShape.box(32,32))
                .zIndex(-1)
                .build();
    }
    @Spawns("flameRightHead")
    public Entity newFlameRightHead(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(BomberData.EntityType.FLAME)
                .with(new CollidableComponent(true))
                .with(new FlamesComponent("rightHead"))
                .bbox(BoundingShape.box(32,32))
                .zIndex(-1)
                .build();
    }
    @Spawns("flameLeftHead")
    public Entity newFlameLeftHead(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(BomberData.EntityType.FLAME)
                .with(new CollidableComponent(true))
                .with(new FlamesComponent("leftHead"))
                .bbox(BoundingShape.box(32,32))
                .zIndex(-1)
                .build();
    }
    @Spawns("flameUpHead")
    public Entity newFlameUpHead(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(BomberData.EntityType.FLAME)
                .with(new CollidableComponent(true))
                .with(new FlamesComponent("upHead"))
                .bbox(BoundingShape.box(32,32))
                .zIndex(-1)
                .build();
    }
    @Spawns("flameDownHead")
    public Entity newFlameDownHead(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(BomberData.EntityType.FLAME)
                .with(new CollidableComponent(true))
                .with(new FlamesComponent("downHead"))
                .bbox(BoundingShape.box(32,32))
                .zIndex(-1)
                .build();
    }
//    Flame End
}
