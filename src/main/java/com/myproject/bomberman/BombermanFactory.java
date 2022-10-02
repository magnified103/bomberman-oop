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
                .with(new BomberComponent())
                .with(new CollidableComponent(true))
                .bbox(new HitBox(new Point2D(8,8), BoundingShape.box(24, 24)))
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
                .viewWithBBox("Wall.png")
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("brick")
    public Entity newBrick(SpawnData data) {
        return FXGL.entityBuilder(data)
                .viewWithBBox("Brick.png")
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("boom")
    public Entity newBoom(SpawnData data) {
        return FXGL.entityBuilder(data)
                .view(new Rectangle(32,32, Color.BLACK))
                .with(new CollidableComponent(true))
                .zIndex(-1)
                .build();
    }
}
