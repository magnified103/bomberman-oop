package com.myproject.bomberman;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.texture.Texture;
import javafx.geometry.Point2D;
import javafx.util.Duration;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TerrainUtility extends System {

    public Entity spawnBomberman(String U, String D, String L, String R, String signature, double x, double y) {
        Entity entity = getParentWorld().spawnEntity();

        entity.addAndAttach(new TransformComponent()).setPosition(x, y);

        Point2D center = new Point2D(31 * -0.5, 31 * -0.5);
        entity.addAndAttach(new BoundingBoxComponent()).addHitBox(
                new HitBox(center, BoundingShape.box(31, 31))
        );

        entity.addAndAttach(new CollidableComponent(Collidable.PASSIVE));
        entity.addAndAttach(new WalkComponent(U, D, L, R, 100));

        ViewComponent view = entity.addAndAttach(new ViewComponent());
        view.addAnimation("up", new AnimationChannel(FXGL.image("BombermanMove.png"),
                12, 32, 32, Duration.seconds(0.25), 3, 5));
        view.addAnimation("down", new AnimationChannel(FXGL.image("BombermanMove.png"),
                12, 32, 32, Duration.seconds(0.25), 0, 2));
        view.addAnimation("left", new AnimationChannel(FXGL.image("BombermanMove.png"),
                12, 32, 32, Duration.seconds(0.25), 6, 8));
        view.addAnimation("right", new AnimationChannel(FXGL.image("BombermanMove.png"),
                12, 32, 32, Duration.seconds(0.25), 9, 11));
        view.addAnimation("dead", new AnimationChannel(FXGL.image("BombermanDead.png"),
                6, 32, 32, Duration.seconds(1.5), 0, 5));
        view.initializeAnimation("up");
        view.setAnimationTranslateX(-16);
        view.setAnimationTranslateY(-16);
        view.setZIndex(1);

        // scrolling background
        TerrainComponent terrain = getParentWorld().getSingletonComponent(TerrainComponent.class);
        FXGL.getGameScene().getViewport().bindToEntity(entity.getFxglEntity(),
                FXGL.getAppWidth() * 0.5, FXGL.getAppHeight() * 0.5);
        FXGL.getGameScene().getViewport().setBounds(
                0,
                0,
                (int) (terrain.getNumberOfColumns() * terrain.getTileWidth()),
                FXGL.getAppHeight()
        );

        entity.addAndAttach(new BombingComponent(signature, 1, 1));

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

        entity.addAndAttach(new TransformComponent()).setPosition(
                terrain.getTileWidth() * (columnIndex + 0.5),
                terrain.getTileHeight() * (rowIndex + 0.5)
        );

        Point2D center = new Point2D(terrain.getTileWidth() * -0.5, terrain.getTileHeight() * -0.5);
        entity.addAndAttach(new BoundingBoxComponent()).addHitBox(new HitBox(
                center, BoundingShape.box(terrain.getTileWidth(), terrain.getTileHeight())));

        entity.addAndAttach(new CollidableComponent(Collidable.STATIC));

        Texture texture = FXGL.texture("Wall.png", terrain.getTileWidth(), terrain.getTileHeight());
        texture.setTranslateX(center.getX());
        texture.setTranslateY(center.getY());
        ViewComponent view = entity.addAndAttach(new ViewComponent());
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

        entity.addAndAttach(new TransformComponent()).setPosition(
                terrain.getTileWidth() * (columnIndex + 0.5),
                terrain.getTileHeight() * (rowIndex + 0.5)
        );

        Point2D center = new Point2D(terrain.getTileWidth() * -0.5, terrain.getTileHeight() * -0.5);
        entity.addAndAttach(new BoundingBoxComponent()).addHitBox(new HitBox(
                center, BoundingShape.box(terrain.getTileWidth(), terrain.getTileHeight())));

        entity.addAndAttach(new CollidableComponent(Collidable.STATIC));

//        BrickAnimationComponent animation = entity.addAndAttach(new BrickAnimationComponent());
        ViewComponent view = entity.addAndAttach(new ViewComponent());
        view.addAnimation("normal", new AnimationChannel(FXGL.image("Brick.png"),
                1, 32, 32, Duration.seconds(1), 0, 0));
        view.addAnimation("burning", new AnimationChannel(FXGL.image("brickBreak.png"),
                7, 32, 32, Duration.seconds(1), 0, 6));
        view.initializeAnimation("normal");
        view.setAnimationTranslate(-16, -16);
        view.setZIndex(2);

        return entity;
    }

    public Entity spawnGrass(int rowIndex, int columnIndex) {
        Entity entity = getParentWorld().spawnEntity();
        TerrainComponent terrain = getParentWorld().getSingletonComponent(TerrainComponent.class);

        entity.addAndAttach(new TransformComponent()).setPosition(
                terrain.getTileWidth() * (columnIndex + 0.5),
                terrain.getTileHeight() * (rowIndex + 0.5)
        );

        Point2D center = new Point2D(terrain.getTileWidth() * -0.5, terrain.getTileHeight() * -0.5);

        Texture texture = FXGL.texture("Grass.png", terrain.getTileWidth(), terrain.getTileHeight());
        texture.setTranslateX(center.getX());
        texture.setTranslateY(center.getY());
        ViewComponent view = entity.addAndAttach(new ViewComponent());
        view.addChild(texture);
        view.setZIndex(-1);

        return entity;
    }

    public Entity spawnBomb(int rowIndex, int columnIndex, double time, int blastRadius, Entity bomber) {
        Entity entity = getParentWorld().spawnEntity();
        TerrainComponent terrain = getParentWorld().getSingletonComponent(TerrainComponent.class);

        if (terrain.getTile(rowIndex, columnIndex) != Tile.GRASS) {
            throw new RuntimeException("Unable to spawn bomb.");
        }
        terrain.setTile(rowIndex, columnIndex, Tile.BOMB);
        terrain.setEntity(rowIndex, columnIndex, entity);

        entity.addAndAttach(new TransformComponent()).setPosition(
                terrain.getTileWidth() * (columnIndex + 0.5),
                terrain.getTileHeight() * (rowIndex + 0.5)
        );

        Point2D center = new Point2D(terrain.getTileWidth() * -0.5, terrain.getTileHeight() * -0.5);
        entity.addAndAttach(new BoundingBoxComponent()).addHitBox(new HitBox(
                center, BoundingShape.box(terrain.getTileWidth(), terrain.getTileHeight())));

        entity.addAndAttach(new CollidableComponent(Collidable.BOMB));

        ViewComponent view = entity.addAndAttach(new ViewComponent());
        view.addAnimation("charging", new AnimationChannel(FXGL.image("Bomb.png"),
                3,
                32,
                32,
                Duration.seconds(0.3),
                0,
                2
        ));
        view.initializeAnimation("charging");
        view.setAnimationTranslate(-16, -16);
        view.loop();
        view.setZIndex(2);

        BombDataComponent data = entity.addAndAttach(new BombDataComponent(time, blastRadius, bomber));

        return entity;
    }

    public Entity spawnFlame(int rowIndex, int columnIndex, double time, Entity bomber, String assetName) {
        Entity entity = getParentWorld().spawnEntity();
        TerrainComponent terrain = getParentWorld().getSingletonComponent(TerrainComponent.class);

        if (terrain.getTile(rowIndex, columnIndex) != Tile.GRASS) {
            throw new RuntimeException("Unable to spawn flame.");
        }
        terrain.setTile(rowIndex, columnIndex, Tile.FLAME);
        terrain.setEntity(rowIndex, columnIndex, entity);

        entity.addAndAttach(new TransformComponent()).setPosition(
                terrain.getTileWidth() * (columnIndex + 0.5),
                terrain.getTileHeight() * (rowIndex + 0.5)
        );

        ViewComponent view = entity.addAndAttach(new ViewComponent());
        view.addAnimation("fire", new AnimationChannel(FXGL.image(assetName),
                7,
                32,
                32,
                Duration.seconds(0.7),
                0,
                6
        ));
        view.initializeAnimation("fire");
        view.setAnimationTranslate(-16, -16);
        view.play();
        view.setZIndex(1);

        FlameDataComponent data = entity.addAndAttach(new FlameDataComponent(time, bomber));

        // collisions
        entity.addAndAttach(new CollidableComponent(Collidable.FLAME));
        Point2D center = new Point2D(terrain.getTileWidth() * -0.5, terrain.getTileHeight() * -0.5);
        entity.addAndAttach(new BoundingBoxComponent()).addHitBox(new HitBox(
                center, BoundingShape.box(terrain.getTileWidth(), terrain.getTileHeight())));

        return entity;
    }

    public Entity spawnItem(int rowIndex, int columnIndex, Item item) {
        Entity entity = getParentWorld().spawnEntity();
        TerrainComponent terrain = getParentWorld().getSingletonComponent(TerrainComponent.class);

        if (terrain.getTile(rowIndex, columnIndex) != Tile.GRASS) {
            throw new RuntimeException("Unable to spawn item.");
        }
        terrain.setTile(rowIndex, columnIndex, switch (item) {
            case BOMB -> Tile.BOMB_ITEM;
            case FLAME -> Tile.FLAME_ITEM;
            case SPEED -> Tile.SPEED_ITEM;
        });
        terrain.setEntity(rowIndex, columnIndex, entity);

        entity.addAndAttach(new TransformComponent()).setPosition(
                terrain.getTileWidth() * (columnIndex + 0.5),
                terrain.getTileHeight() * (rowIndex + 0.5)
        );

        entity.addAndAttach(new CollidableComponent(Collidable.ITEM));
        Point2D center = new Point2D(terrain.getTileWidth() * -0.5, terrain.getTileHeight() * -0.5);
        entity.addAndAttach(new BoundingBoxComponent()).addHitBox(new HitBox(
                center, BoundingShape.box(terrain.getTileWidth(), terrain.getTileHeight())));

        ViewComponent view = entity.addAndAttach(new ViewComponent());
        view.addAnimation("texture", new AnimationChannel(FXGL.image(switch (item) {
            case BOMB -> "itemBomb.png";
            case FLAME -> "itemFlame.png";
            case SPEED -> "itemSpeed.png";
        }), 1, 32, 32, Duration.seconds(1), 0, 0));
        view.initializeAnimation("texture");
        view.setAnimationTranslate(-16, -16);
        view.setZIndex(0);

        return entity;
    }

    public Entity spawnEnemy(double x, double y, String assetName, AIComponent botComponent) {
        Entity entity = getParentWorld().spawnEntity();

        // attach bot
        entity.addAndAttach(botComponent);

        entity.addAndAttach(new WalkComponent(100));

        entity.addAndAttach(new TransformComponent()).setPosition(x, y);

        Point2D center = new Point2D(31 * -0.5, 31 * -0.5);
        entity.addAndAttach(new BoundingBoxComponent()).addHitBox(
                new HitBox(center, BoundingShape.box(31, 31))
        );

        entity.addAndAttach(new CollidableComponent(Collidable.HOSTILE));

        ViewComponent view = entity.addAndAttach(new ViewComponent());
        view.addAnimation("walk", new AnimationChannel(FXGL.image(assetName),
                5, 32, 32, Duration.seconds(0.5), 0, 2));
        view.addAnimation("dead", new AnimationChannel(FXGL.image(assetName),
                5, 32, 32, Duration.seconds(1), 3, 4));
        view.initializeAnimation("walk");
        view.setAnimationTranslate(-16, -16);
        view.loop();
        view.setZIndex(1);

        return entity;
    }

    public Entity spawnPortal(int rowIndex, int columnIndex) {
        Entity entity = getParentWorld().spawnEntity();
        TerrainComponent terrain = getParentWorld().getSingletonComponent(TerrainComponent.class);

        if (terrain.getTile(rowIndex, columnIndex) != Tile.GRASS) {
            throw new RuntimeException("Unable to spawn portal.");
        }
        terrain.setTile(rowIndex, columnIndex, Tile.PORTAL);
        terrain.setEntity(rowIndex, columnIndex, entity);

        Point2D center = new Point2D(3 * -0.5, 3 * -0.5);
        entity.addAndAttach(new BoundingBoxComponent()).addHitBox(
                new HitBox(center, BoundingShape.box(3, 3))
        );

        entity.addAndAttach(new CollidableComponent(Collidable.PORTAL));
        entity.addAndAttach(new TransformComponent()).setPosition(
                terrain.getTileWidth() * (columnIndex + 0.5),
                terrain.getTileHeight() * (rowIndex + 0.5)
        );

        ViewComponent view = entity.addAndAttach(new ViewComponent());
        view.addAnimation("open", new AnimationChannel(FXGL.image("portal.png"),
                2,
                32,
                32,
                Duration.seconds(0.2),
                0,
                1
        ));
        view.initializeAnimation("open");
        view.setAnimationTranslate(-16, -16);
        view.setZIndex(0);

        return entity;
    }

    public Entity resetTile(int rowIndex, int columnIndex) {
        TerrainComponent terrain = getParentWorld().getSingletonComponent(TerrainComponent.class);

        Entity entity = terrain.getEntity(rowIndex, columnIndex);
        terrain.setTile(rowIndex, columnIndex, Tile.GRASS);
        terrain.setEntity(rowIndex, columnIndex, null);

        return entity;
    }

    public void killDynamicEntity(Entity entity) {
        List<Component> componentList = new ArrayList<>(entity.getComponentList());
        for (Component component : componentList) {
            if (component.getClass() != TransformComponent.class
                    && component.getClass() != ViewComponent.class) {
                entity.detach(component);
                // smart removal
                if (component.getLinkage().isEmpty()) {
                    getParentWorld().removeComponent(component);
                }
            }
        }

        entity.addAndAttach(new TimerComponent(2, (timer, tpf) -> {
            getParentWorld().removeEntityComponents(entity);
            // if no players alive
            if (getParentWorld().getComponentsByType(CollidableComponent.class).stream()
                    .noneMatch((component) -> (component.getType() == Collidable.PASSIVE))) {
                getParentWorld().getSystem(WorldUtility.class).pauseLevel();
                getParentWorld().getSingletonComponent(DataComponent.class)
                        .setData("gameState", "dead");
            }
        }));

        entity.getComponentByType(ViewComponent.class).play("dead");
    }
}
