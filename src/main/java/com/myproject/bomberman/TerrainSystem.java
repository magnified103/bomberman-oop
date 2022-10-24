package com.myproject.bomberman;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.texture.Texture;
import javafx.geometry.Point2D;
import javafx.util.Duration;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TerrainSystem extends System {

    public void load(String path) {
        try {
            Scanner scanner = new Scanner(Path.of(path));
            int levelIndex = scanner.nextInt();
            int numberOfRows = scanner.nextInt();
            int numberOfColumns = scanner.nextInt();
            double cellWidth = 32;
            double cellHeight = 32;
            scanner.nextLine();

            TerrainComponent terrain = new TerrainComponent(numberOfRows, numberOfColumns);
            getParentWorld().clearAllEntitiesAndComponents();
            getParentWorld().setSingletonComponent(terrain);
            terrain.setTileWidth(cellWidth);
            terrain.setTileHeight(cellHeight);

            for (int i = 0; i < numberOfRows; i++) {
                String line = scanner.nextLine();
                for (int j = 0; j < numberOfColumns; j++) {
                    switch (line.charAt(j)) {
                        case '#': {
                            spawnWall(i, j);
                            break;
                        }
                        case '*': {
                            spawnBrick(i, j, Tile.BRICK);
                            break;
                        }
                        case 'x': {
                            spawnBrick(i, j, Tile.UNEXPOSED_PORTAL);
                            break;
                        }
                        case 'p': {
                            spawnBomberman("W", "S", "A", "D", "Space",
                                    cellWidth * (j + 0.5), cellHeight * (i + 0.5));
                            break;
                        }
                        case '1': {
                            spawnEnemy(cellWidth * (j + 0.5), cellHeight * (i + 0.5),
                                    "Enemy1.png", new BotRandomWalkComponent(0.01));
                            break;
                        }
                        case '2':
                            spawnEnemy(cellWidth * (j + 0.5), cellHeight * (i + 0.5),
                                    "Enemy2.png", new BotRandomWalkComponent(0.1));
                            break;
                        case 'b':
                            spawnBrick(i, j, Tile.UNEXPOSED_BOMB_ITEM);
                            break;
                        case 'f':
                            spawnBrick(i, j, Tile.UNEXPOSED_FLAME_ITEM);
                            break;
                        case 's':
                            spawnBrick(i, j, Tile.UNEXPOSED_SPEED_ITEM);
                            break;
                    }
                    spawnGrass(i, j);
                }
            }
        }
        catch (IOException exception) {
            throw new RuntimeException(String.format("File not found: %s", path));
        }
    }

    public Entity spawnBomberman(String U, String D, String L, String R, String signature, double x, double y) {
        Entity entity = getParentWorld().spawnEntity();

        entity.addAndAttach(new FxglTransformComponent()).setPosition(x, y);

        Point2D center = new Point2D(31 * -0.5, 31 * -0.5);
        entity.addAndAttach(new FxglBoundingBoxComponent()).addHitBox(
                new HitBox(center, BoundingShape.box(31, 31))
        );

        entity.addAndAttach(new CollidableComponent(Collidable.PASSIVE));
        entity.addAndAttach(new WalkInputComponent(U, D, L, R, 100));
        WalkAnimationComponent walkAnimation =
                entity.addAndAttach(new WalkAnimationComponent("BombermanMove.png"));

        FxglViewComponent view = entity.addAndAttach(new FxglViewComponent());
        view.addChild(walkAnimation.getMainFrame());
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

        entity.addAndAttach(new BombingInputComponent(signature, 1));
        entity.addAndAttach(new BombingDataComponent(1));

        entity.addAndAttach(
                new DeathComponent(1.5, "BombermanDead.png", 6, 0, 5));

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

        BrickAnimationComponent animation = entity.addAndAttach(new BrickAnimationComponent());
        FxglViewComponent view = entity.addAndAttach(new FxglViewComponent());
        view.addChild(animation.getMainFrame());
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

    public Entity spawnBomb(int rowIndex, int columnIndex, double time, int blastRadius, Entity bomber) {
        Entity entity = getParentWorld().spawnEntity();
        TerrainComponent terrain = getParentWorld().getSingletonComponent(TerrainComponent.class);

        if (terrain.getTile(rowIndex, columnIndex) != Tile.GRASS) {
            throw new RuntimeException("Unable to spawn bomb.");
        }
        terrain.setTile(rowIndex, columnIndex, Tile.BOMB);
        terrain.setEntity(rowIndex, columnIndex, entity);

        entity.addAndAttach(new FxglTransformComponent()).setPosition(
                terrain.getTileWidth() * (columnIndex + 0.5),
                terrain.getTileHeight() * (rowIndex + 0.5)
        );

        Point2D center = new Point2D(terrain.getTileWidth() * -0.5, terrain.getTileHeight() * -0.5);
        entity.addAndAttach(new FxglBoundingBoxComponent()).addHitBox(new HitBox(
                center, BoundingShape.box(terrain.getTileWidth(), terrain.getTileHeight())));

        entity.addAndAttach(new CollidableComponent(Collidable.BOMB));

        BombAnimationComponent animation = entity.addAndAttach(new BombAnimationComponent("Bomb.png"));
        FxglViewComponent view = entity.addAndAttach(new FxglViewComponent());
        view.addChild(animation.getMainFrame());
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

        entity.addAndAttach(new FxglTransformComponent()).setPosition(
                terrain.getTileWidth() * (columnIndex + 0.5),
                terrain.getTileHeight() * (rowIndex + 0.5)
        );

        FlameAnimationComponent animation = entity.addAndAttach(new FlameAnimationComponent(assetName));
        FxglViewComponent view = entity.addAndAttach(new FxglViewComponent());
        view.addChild(animation.getMainFrame());
        view.setZIndex(1);

        FlameDataComponent data = entity.addAndAttach(new FlameDataComponent(time, bomber));

        // collisions
        entity.addAndAttach(new CollidableComponent(Collidable.FLAME));
        Point2D center = new Point2D(terrain.getTileWidth() * -0.5, terrain.getTileHeight() * -0.5);
        entity.addAndAttach(new FxglBoundingBoxComponent()).addHitBox(new HitBox(
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

        entity.addAndAttach(new FxglTransformComponent()).setPosition(
                terrain.getTileWidth() * (columnIndex + 0.5),
                terrain.getTileHeight() * (rowIndex + 0.5)
        );

        entity.addAndAttach(new CollidableComponent(Collidable.ITEM));
        Point2D center = new Point2D(terrain.getTileWidth() * -0.5, terrain.getTileHeight() * -0.5);
        entity.addAndAttach(new FxglBoundingBoxComponent()).addHitBox(new HitBox(
                center, BoundingShape.box(terrain.getTileWidth(), terrain.getTileHeight())));

        ItemComponent itemComponent = entity.addAndAttach(new ItemComponent(item));
        FxglViewComponent view = entity.addAndAttach(new FxglViewComponent());
        view.addChild(itemComponent.getMainFrame());
        view.setZIndex(0);

        return entity;
    }

    public Entity spawnEnemy(double x, double y, String assetName, Component botComponent) {
        Entity entity = getParentWorld().spawnEntity();

        // attach bot
        entity.addAndAttach(botComponent);

        entity.addAndAttach(new FxglTransformComponent()).setPosition(x, y);

        Point2D center = new Point2D(31 * -0.5, 31 * -0.5);
        entity.addAndAttach(new FxglBoundingBoxComponent()).addHitBox(
                new HitBox(center, BoundingShape.box(31, 31))
        );

        entity.addAndAttach(new CollidableComponent(Collidable.HOSTILE));

        AnimatedTexture frame = new AnimatedTexture(new AnimationChannel(FXGL.image(assetName),
                5, 32, 32, Duration.seconds(0.5), 0, 2));
        frame.setTranslateX(-16);
        frame.setTranslateY(-16);
        frame.loop();

        FxglViewComponent view = entity.addAndAttach(new FxglViewComponent());
        view.addChild(frame);
        view.setZIndex(1);

        entity.addAndAttach(new DeathComponent(1, assetName, 5, 3, 4));

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
        entity.addAndAttach(new FxglBoundingBoxComponent()).addHitBox(
                new HitBox(center, BoundingShape.box(3, 3))
        );

        entity.addAndAttach(new CollidableComponent(Collidable.PORTAL));
        entity.addAndAttach(new FxglTransformComponent()).setPosition(
                terrain.getTileWidth() * (columnIndex + 0.5),
                terrain.getTileHeight() * (rowIndex + 0.5)
        );

        PortalAnimationComponent animation = entity.addAndAttach(new PortalAnimationComponent("portal.png"));

        FxglViewComponent view = entity.addAndAttach(new FxglViewComponent());
        view.addChild(animation.getMainFrame());
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
            if (component.getClass() != FxglTransformComponent.class
                    && component.getClass() != FxglViewComponent.class
                    && component.getClass() != DeathComponent.class) {
                entity.detach(component);
                if (BotWalkComponent.class.isAssignableFrom(component.getClass())) {
                    getParentWorld().removeComponent(component);
                }
            }
        }

        DeathComponent death = entity.getComponentByType(DeathComponent.class);
        death.resume();
        FxglViewComponent view = entity.getComponentByType(FxglViewComponent.class);
        view.getFxglComponent().clearChildren();
        view.addChild(death.getDeathFrame());
    }
}
