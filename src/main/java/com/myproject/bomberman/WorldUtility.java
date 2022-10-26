package com.myproject.bomberman;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.FXGLForKtKt;
import com.almasb.fxgl.ui.FontType;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.TreeMap;

public class WorldUtility extends System {

    private TreeMap<Integer, String> levelPathMap;

    public WorldUtility() {
        levelPathMap = new TreeMap<>();
    }

    public void initialize() {
        getParentWorld().setSingletonComponent(new DataComponent());
        DataComponent data = getParentWorld().getSingletonComponent(DataComponent.class);
        getParentWorld().addSystem(new InputSystem());
        getParentWorld().addSystem(new ScoringSystem());
        getParentWorld().addSystem(new TimerSystem());
        getParentWorld().addSystem(new BombDetonationSystem());
        getParentWorld().addSystem(new FlameSystem());
        getParentWorld().addSystem(new AIRandomSystem());
        getParentWorld().addSystem(new BombingSystem());
        getParentWorld().addSystem(new TerrainUtility());
        getParentWorld().addSystem(new WalkSystem());
        getParentWorld().addSystem(new CollisionSystem());
        getParentWorld().addSystem(new PortalSystem());
        getParentWorld().addSystem(new WalkAnimationSystem());
        getParentWorld().addSystem(new BrickOnFireSystem());
        pauseLevel();
        data.setData("gameState", "newGame");
    }

    public void addLevel(String path) {
        try {
            Scanner scanner = new Scanner(Path.of(path));
            int levelIndex = scanner.nextInt();
            scanner.close();
            levelPathMap.put(levelIndex, path);
        } catch (IOException exception) {
            throw new RuntimeException(String.format("I/O exception: %s", exception.getMessage()));
        }
    }

    private void showBlackTitleScreen(String message, double time, Runnable callback) {
        Rectangle background = new Rectangle(FXGL.getAppWidth(), FXGL.getAppHeight(), Color.BLACK);
        Text messageBox = new Text(message);
        messageBox.setFont(FXGLForKtKt.getUIFactoryService().newFont(FontType.MONO, 60.0));
        messageBox.setStrokeWidth(1);
        messageBox.setFill(Color.WHITE);

        FXGL.addUINode(background);
        FXGL.addUINode(messageBox, FXGL.getAppWidth() * 0.5, FXGL.getAppHeight() * 0.5);
        getParentWorld().addComponent(new TimerComponent(time, (timer, tpf) -> {
            getParentWorld().removeComponent(timer);
            FXGL.removeUINode(messageBox);
            FXGL.removeUINode(background);
            callback.run();
        }));
    }

    public int load(String path) {
        try {
            Scanner scanner = new Scanner(Path.of(path));
            int levelIndex = scanner.nextInt();
            int numberOfRows = scanner.nextInt();
            int numberOfColumns = scanner.nextInt();
            double cellWidth = 32;
            double cellHeight = 32;
            scanner.nextLine();

            TerrainComponent terrain = new TerrainComponent(numberOfRows, numberOfColumns);
            TerrainUtility util = getParentWorld().getSystem(TerrainUtility.class);
            getParentWorld().clearAllEntitiesAndComponents();
            getParentWorld().setSingletonComponent(terrain);
            terrain.setTileWidth(cellWidth);
            terrain.setTileHeight(cellHeight);

            for (int i = 0; i < numberOfRows; i++) {
                String line = scanner.nextLine();
                for (int j = 0; j < numberOfColumns; j++) {
                    switch (line.charAt(j)) {
                        case '#': {
                            util.spawnWall(i, j);
                            break;
                        }
                        case '*': {
                            util.spawnBrick(i, j, Tile.BRICK);
                            break;
                        }
                        case 'x': {
                            util.spawnBrick(i, j, Tile.UNEXPOSED_PORTAL);
                            break;
                        }
                        case 'p': {
                            util.spawnBomberman("W", "S", "A", "D", "Space",
                                    cellWidth * (j + 0.5), cellHeight * (i + 0.5));
                            break;
                        }
                        case '1': {
                            util.spawnEnemy(cellWidth * (j + 0.5), cellHeight * (i + 0.5),
                                    "Enemy1.png", new AIRandomComponent(0.01));
                            break;
                        }
                        case '2':
                            util.spawnEnemy(cellWidth * (j + 0.5), cellHeight * (i + 0.5),
                                    "Enemy2.png", new AIRandomComponent(0.1));
                            break;
                        case 'b':
                            util.spawnBrick(i, j, Tile.UNEXPOSED_BOMB_ITEM);
                            break;
                        case 'f':
                            util.spawnBrick(i, j, Tile.UNEXPOSED_FLAME_ITEM);
                            break;
                        case 's':
                            util.spawnBrick(i, j, Tile.UNEXPOSED_SPEED_ITEM);
                            break;
                    }
                    util.spawnGrass(i, j);
                }
            }
            scanner.close();
            return levelIndex;
        }
        catch (IOException exception) {
            throw new RuntimeException(String.format("I/O exception: %s", exception.getMessage()));
        }
    }

    public void pauseLevel() {
        getParentWorld().getSystem(InputSystem.class).pause();
        getParentWorld().getSystem(ScoringSystem.class).pause();
//        getParentWorld().getSystem(TimerSystem.class).pause();
        getParentWorld().getSystem(BombDetonationSystem.class).pause();
        getParentWorld().getSystem(FlameSystem.class).pause();
        getParentWorld().getSystem(AIRandomSystem.class).pause();
        getParentWorld().getSystem(BombingSystem.class).pause();
        getParentWorld().getSystem(TerrainUtility.class).pause();
        getParentWorld().getSystem(WalkSystem.class).pause();
        getParentWorld().getSystem(CollisionSystem.class).pause();
        getParentWorld().getSystem(PortalSystem.class).pause();
        getParentWorld().getSystem(WalkAnimationSystem.class).pause();
        getParentWorld().getSystem(BrickOnFireSystem.class).pause();
        getParentWorld().getSystem(ScoringSystem.class).unload();
    }

    public void resumeLevel() {
        getParentWorld().getSystem(InputSystem.class).resume();
        getParentWorld().getSystem(ScoringSystem.class).resume();
//        getParentWorld().getSystem(TimerSystem.class).resume();
        getParentWorld().getSystem(BombDetonationSystem.class).resume();
        getParentWorld().getSystem(FlameSystem.class).resume();
        getParentWorld().getSystem(AIRandomSystem.class).resume();
        getParentWorld().getSystem(BombingSystem.class).resume();
        getParentWorld().getSystem(TerrainUtility.class).resume();
        getParentWorld().getSystem(WalkSystem.class).resume();
        getParentWorld().getSystem(CollisionSystem.class).resume();
        getParentWorld().getSystem(PortalSystem.class).resume();
        getParentWorld().getSystem(WalkAnimationSystem.class).resume();
        getParentWorld().getSystem(BrickOnFireSystem.class).resume();
        getParentWorld().getSystem(ScoringSystem.class).load();
    }

    @Override
    public void update(double tpf) {
        DataComponent data = getParentWorld().getSingletonComponent(DataComponent.class);
        if (data.getData("gameState") == "pending") {
            return;
        }
        if (data.getData("gameState") == "dead"
                || data.getData("gameState") == "newGame"
                || data.getData("gameState") == "levelCompleted") {
            if (levelPathMap.isEmpty()) {
                data.setData("gameState", "gameCompleted");
            } else {
                Integer level;
                if (data.getData("gameState") == "levelCompleted") {
                    level = (Integer) data.getData("currentLevel");
                    level = levelPathMap.higherKey(level);
                } else {
                    level = levelPathMap.firstKey();
                }
                if (level == null) {
                    data.setData("gameState", "gameCompleted");
                } else {
                    data.setData("gameState", "pending");
                    data.setData("currentLevel", level);
                    Integer finalLevel = level;
                    showBlackTitleScreen(String.format("Level %d", level), 4, () -> {
                        load(levelPathMap.get(finalLevel));
                        resumeLevel();
                        data.setData("gameState", "running");
                    });
                }
            }
        }
        if (data.getData("gameState") == "running") {
            return;
        }
        if (data.getData("gameState") == "gameCompleted") {
            data.setData("gameState", "pending");
            showBlackTitleScreen("You win!", 4, () -> {
                FXGL.getWindowService().gotoMainMenu();
            });
        }
    }
}
