package com.myproject.bomberman;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.texture.Texture;
import javafx.geometry.Point2D;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.util.Scanner;

public class MapLoader extends System {

    public void load(String path) {
        try {
            Scanner scanner = new Scanner(Path.of(path));
            int levelIndex = scanner.nextInt();
            int numberOfRows = scanner.nextInt();
            int numberOfColumns = scanner.nextInt();
            double cellWidth = 32;
            double cellHeight = 32;
            scanner.nextLine();

            MapComponent map = new MapComponent(numberOfRows, numberOfColumns);
            getParentWorld().setSingletonComponent(map);
            map.setCellWidth(cellWidth);
            map.setCellHeight(cellHeight);

            for (int i = 0; i < numberOfRows; i++) {
                String line = scanner.nextLine();
                for (int j = 0; j < numberOfColumns; j++) {
                    FxglTransformComponent transform = new FxglTransformComponent();
                    FxglBoundingBoxComponent bbox = new FxglBoundingBoxComponent();
                    FxglViewComponent view = new FxglViewComponent();

                    switch (line.charAt(j)) {
                        case '#': {
                            map.setCell(i, j, MapCell.WALL);
                            break;
                        }
                        case '*': {
                            map.setCell(i, j, MapCell.BRICK);
                            break;
                        }
                        case 'x': {
                            map.setCell(i, j, MapCell.UNEXPOSED_PORTAL);
                            break;
                        }
                        case 'p': {
                            Entity entity = getParentWorld().spawnEntity();
                            map.setCellEntity(i, j, entity);
                            WalkAnimationComponent walkAnimation =
                                    new WalkAnimationComponent("BombermanMove.png");

                            entity.addAndAttach(transform);
                            entity.addAndAttach(bbox);
                            entity.addAndAttach(view);
                            entity.addAndAttach(new CollidableComponent(Collidable.PASSIVE));
                            entity.addAndAttach(new WalkInputComponent("W", "S", "A", "D"));
                            entity.addAndAttach(new PlantBombInputComponent("Space"));
                            entity.addAndAttach(walkAnimation);
                            transform.getFxglComponent().setPosition(cellWidth * (j + 0.5),
                                    cellHeight * (i + 0.5));
                            Point2D center = new Point2D(31 * -0.5, 31 * -0.5);
                            bbox.getFxglComponent().addHitBox(new HitBox(center,
                                    BoundingShape.box(31, 31)));
                            view.getFxglComponent().addChild(walkAnimation.getMainFrame());
                            view.getFxglComponent().setZIndex(1);
                        }
                        case '1':
                            break;
                        case '2':
                            break;
                        case 'b':
                            map.setCell(i, j, MapCell.UNEXPOSED_BOMB_ITEM);
                            break;
                        case 'f':
                            map.setCell(i, j, MapCell.UNEXPOSED_FLAME_ITEM);
                            break;
                        case 's':
                            map.setCell(i, j, MapCell.UNEXPOSED_SPEED_ITEM);
                            break;
                    }
                }
            }

            for (int i = 0; i < numberOfRows; i++) {
                for (int j = 0; j < numberOfColumns; j++) {
                    //Dkm no con loi luc no bom boi vi cai grid chua duoc set luc spawn
                    WalkInputComponent playerData = getParentWorld().getEntitiesByType(WalkInputComponent.class).get(0).getComponentByType(WalkInputComponent.class);
                    //Vi la cai grid chi dung chung cua thang player dau tien cho nen toi get(0) cho nhanh
                    //Cai Grid nay la de dung cho check luc bom no no se ko spawn flame ra neu co wall hay brick o tryoc
                    //Hien tai toi moi lam duoc cai wall con cai brick bi loi gi ay, no van spawn sau no mat di
                    //MAI FIXXXXX

                    FxglTransformComponent transform = new FxglTransformComponent();
                    FxglBoundingBoxComponent bbox = new FxglBoundingBoxComponent();
                    FxglViewComponent view = new FxglViewComponent();

                    switch (map.getCell(i, j)) {
                        case WALL -> {
                            Entity entity = getParentWorld().spawnEntity();
                            map.setCellEntity(i, j, entity);
                            entity.addAndAttach(transform);
                            entity.addAndAttach(bbox);
                            entity.addAndAttach(view);
                            entity.addAndAttach(new CollidableComponent(Collidable.STATIC));
                            transform.getFxglComponent().setPosition(cellWidth * (j + 0.5),
                                    cellHeight * (i + 0.5));
                            Point2D center = new Point2D(cellWidth * -0.5, cellHeight * -0.5);
                            bbox.getFxglComponent().addHitBox(new HitBox(center,
                                    BoundingShape.box(cellWidth, cellHeight)));
                            Texture texture = FXGL.texture("Wall.png", cellWidth, cellHeight);
                            texture.setTranslateX(center.getX());
                            texture.setTranslateY(center.getY());
                            view.getFxglComponent().addChild(texture);
                            view.getFxglComponent().setZIndex(2);
                            //wall set xong thi van chay bth
                            playerData.setGRID(i,j,1);

                        }
                        case BRICK, UNEXPOSED_PORTAL, UNEXPOSED_BOMB_ITEM, UNEXPOSED_FLAME_ITEM, UNEXPOSED_SPEED_ITEM -> {

                            Entity entity = getParentWorld().spawnEntity();
                            map.setCellEntity(i, j, entity);
                            entity.addAndAttach(transform);
                            entity.addAndAttach(bbox);
                            entity.addAndAttach(view);
                            entity.addAndAttach(new CollidableComponent(Collidable.STATIC));
                            transform.getFxglComponent().setPosition(cellWidth * (j + 0.5),
                                    cellHeight * (i + 0.5));
                            Point2D center = new Point2D(cellWidth * -0.5, cellHeight * -0.5);
                            bbox.getFxglComponent().addHitBox(new HitBox(center,
                                    BoundingShape.box(cellWidth, cellHeight)));
                            Texture texture = FXGL.texture("brick.png", cellWidth, cellHeight);
                            texture.setTranslateX(center.getX());
                            texture.setTranslateY(center.getY());
                            view.getFxglComponent().addChild(texture);
                            view.getFxglComponent().setZIndex(2);
                            //set doan nay roi nhung van loi dkm deo hieu
                            playerData.setGRID(i,j,1);

                        }
                    }

                    transform = new FxglTransformComponent();
                    view = new FxglViewComponent();
                    Entity entity = getParentWorld().spawnEntity();
                    entity.addAndAttach(transform);
                    entity.addAndAttach(view);
                    transform.getFxglComponent().setPosition(cellWidth * (j + 0.5),
                            cellHeight * (i + 0.5));
                    Point2D center = new Point2D(cellWidth * -0.5, cellHeight * -0.5);
                    Texture texture = FXGL.texture("Grass.png", cellWidth, cellHeight);
                    texture.setTranslateX(center.getX());
                    texture.setTranslateY(center.getY());
                    view.getFxglComponent().addChild(texture);
                    view.getFxglComponent().setZIndex(-1);
                }
            }
        }
        catch (IOException exception) {
            throw new RuntimeException(String.format("File not found: %s", path));
        }
    }
}
