package com.myproject.bomberman;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.texture.Texture;
import javafx.geometry.Point2D;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

public class LoaderSystem extends System {

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

            SpawnerSystem spawner = getParentWorld().getSingletonSystem(SpawnerSystem.class);

            for (int i = 0; i < numberOfRows; i++) {
                String line = scanner.nextLine();
                for (int j = 0; j < numberOfColumns; j++) {
                    switch (line.charAt(j)) {
                        case '#': {
                            spawner.spawnWall(i, j);
                            break;
                        }
                        case '*': {
                            spawner.spawnBrick(i, j, Tile.BRICK);
                            break;
                        }
                        case 'x': {
                            spawner.spawnBrick(i, j, Tile.UNEXPOSED_PORTAL);
                            break;
                        }
                        case 'p': {
                            spawner.spawnBomberman("W", "S", "A", "D",
                                    cellWidth * (j + 0.5), cellHeight * (i + 0.5));
                        }
                        case '1':
                            break;
                        case '2':
                            break;
                        case 'b':
                            spawner.spawnBrick(i, j, Tile.UNEXPOSED_BOMB_ITEM);
                            break;
                        case 'f':
                            spawner.spawnBrick(i, j, Tile.UNEXPOSED_FLAME_ITEM);
                            break;
                        case 's':
                            spawner.spawnBrick(i, j, Tile.UNEXPOSED_SPEED_ITEM);
                            break;
                    }
                    spawner.spawnGrass(i, j);
                }
            }
        }
        catch (IOException exception) {
            throw new RuntimeException(String.format("File not found: %s", path));
        }
    }
}
