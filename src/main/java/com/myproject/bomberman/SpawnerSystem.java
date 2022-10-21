package com.myproject.bomberman;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.texture.Texture;
import javafx.geometry.Point2D;

public class SpawnerSystem extends System {

    public Entity spawnBomberman(String U, String D, String L, String R, double x, double y) {
        Entity entity = getParentWorld().spawnEntity();

        entity.addAndAttach(new FxglTransformComponent()).setPosition(x, y);

        Point2D center = new Point2D(31 * -0.5, 31 * -0.5);
        entity.addAndAttach(new FxglBoundingBoxComponent()).addHitBox(
                new HitBox(center, BoundingShape.box(31, 31))
        );

        entity.addAndAttach(new CollidableComponent(Collidable.PASSIVE));
        entity.addAndAttach(new WalkInputComponent(U, D, L, R));
        WalkAnimationComponent walkAnimation =
                entity.addAndAttach(new WalkAnimationComponent("BombermanMove.png"));

        FxglViewComponent view = entity.addAndAttach(new FxglViewComponent());
        view.addChild(walkAnimation.getMainFrame());
        view.setZIndex(1);

        return entity;
    }

    public Entity spawnWall(int rowIndex, int columnIndex) {
        Entity entity = getParentWorld().spawnEntity();
        TerrainComponent terrain = getParentWorld().getSingletonComponent(TerrainComponent.class);

        if (terrain.getTile(rowIndex, columnIndex) != Tile.GRASS) {
            throw new RuntimeException("Unable to spawn wall.");
        }
        terrain.setTile(rowIndex, columnIndex, Tile.WALL);
        terrain.setEntity(rowIndex, columnIndex, entity);

        entity.addAndAttach(new FxglTransformComponent()).setPosition(
                terrain.getTileWidth() * (columnIndex + 0.5),
                terrain.getTileHeight() * (rowIndex + 0.5)
        );

        Point2D center = new Point2D(terrain.getTileWidth() * -0.5, terrain.getTileHeight() * -0.5);
        entity.addAndAttach(new FxglBoundingBoxComponent()).addHitBox(new HitBox(
                center, BoundingShape.box(terrain.getTileWidth(), terrain.getTileHeight())));

        entity.addAndAttach(new CollidableComponent(Collidable.STATIC));

        Texture texture = FXGL.texture("Wall.png", terrain.getTileWidth(), terrain.getTileHeight());
        texture.setTranslateX(center.getX());
        texture.setTranslateY(center.getY());
        FxglViewComponent view = entity.addAndAttach(new FxglViewComponent());
        view.addChild(texture);
        view.setZIndex(2);

        return entity;
    }

    public Entity spawnBrick(int rowIndex, int columnIndex, Tile type) {
        Entity entity = getParentWorld().spawnEntity();
        TerrainComponent terrain = getParentWorld().getSingletonComponent(TerrainComponent.class);

        if (terrain.getTile(rowIndex, columnIndex) != Tile.GRASS) {
            throw new RuntimeException("Unable to spawn brick.");
        }
        terrain.setTile(rowIndex, columnIndex, type);
        terrain.setEntity(rowIndex, columnIndex, entity);

        entity.addAndAttach(new FxglTransformComponent()).setPosition(
                terrain.getTileWidth() * (columnIndex + 0.5),
                terrain.getTileHeight() * (rowIndex + 0.5)
        );

        Point2D center = new Point2D(terrain.getTileWidth() * -0.5, terrain.getTileHeight() * -0.5);
        entity.addAndAttach(new FxglBoundingBoxComponent()).addHitBox(new HitBox(
                center, BoundingShape.box(terrain.getTileWidth(), terrain.getTileHeight())));

        entity.addAndAttach(new CollidableComponent(Collidable.STATIC));

        Texture texture = FXGL.texture("brick.png", terrain.getTileWidth(), terrain.getTileHeight());
        texture.setTranslateX(center.getX());
        texture.setTranslateY(center.getY());
        FxglViewComponent view = entity.addAndAttach(new FxglViewComponent());
        view.addChild(texture);
        view.setZIndex(2);

        return entity;
    }

    public Entity spawnGrass(int rowIndex, int columnIndex) {
        Entity entity = getParentWorld().spawnEntity();
        TerrainComponent terrain = getParentWorld().getSingletonComponent(TerrainComponent.class);

        entity.addAndAttach(new FxglTransformComponent()).setPosition(
                terrain.getTileWidth() * (columnIndex + 0.5),
                terrain.getTileHeight() * (rowIndex + 0.5)
        );

        Point2D center = new Point2D(terrain.getTileWidth() * -0.5, terrain.getTileHeight() * -0.5);

        Texture texture = FXGL.texture("Grass.png", terrain.getTileWidth(), terrain.getTileHeight());
        texture.setTranslateX(center.getX());
        texture.setTranslateY(center.getY());
        FxglViewComponent view = entity.addAndAttach(new FxglViewComponent());
        view.addChild(texture);
        view.setZIndex(-1);

        return entity;
    }

//    public Entity spawnBomb(int rowIndex, int columnIndex) {
//
//    }

    public Entity resetTile(int rowIndex, int columnIndex) {
        TerrainComponent terrain = getParentWorld().getSingletonComponent(TerrainComponent.class);

        Entity entity = terrain.getEntity(rowIndex, columnIndex);
        terrain.setTile(rowIndex, columnIndex, Tile.GRASS);
        terrain.setEntity(rowIndex, columnIndex, null);

        return entity;
    }
}
