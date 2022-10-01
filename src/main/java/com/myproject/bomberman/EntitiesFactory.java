package com.myproject.bomberman;

import com.almasb.fxgl.entity.*;
import com.almasb.fxgl.entity.components.*;
import com.almasb.fxgl.physics.*;
import com.myproject.bomberman.EntitiesComponent.BomberComponent;
import javafx.geometry.Point2D;

import static com.almasb.fxgl.dsl.FXGLForKtKt.entityBuilder;

public class EntitiesFactory implements EntityFactory {

    @Spawns("player")
    public Entity newPlayer(SpawnData data) {
        return entityBuilder(data)
                .with(new BomberComponent())
                .with(new CollidableComponent(true))
                .bbox(new HitBox(new Point2D(12,12), BoundingShape.box(20, 20)))
                .buildAndAttach();
    }

    @Spawns("shelter")
    public Entity newShelter(SpawnData data) {
        return entityBuilder(data)
                .viewWithBBox("Shelter.png")
                .with(new CollidableComponent(true))
                .buildAndAttach();
    }

    @Spawns("grass")
    public Entity newGrass(SpawnData data) {
        return entityBuilder(data)
                .viewWithBBox("Grass.png")
                .with(new CollidableComponent(true))
                .buildAndAttach();
    }

    @Spawns("wall")
    public Entity newWall(SpawnData data) {
        return entityBuilder(data)
                .viewWithBBox("Wall.png")
                .with(new CollidableComponent(true))
                .buildAndAttach();
    }

    @Spawns("brick")
    public Entity newBrick(SpawnData data) {
        return entityBuilder(data)
                .viewWithBBox("Brick.png")
                .with(new CollidableComponent(true))
                .buildAndAttach();
    }

    @Spawns("boom")
    public Entity newBoom(SpawnData data) {
        return entityBuilder(data)
                .with(new CollidableComponent(true))
                .buildAndAttach();
    }
}
