package com.myproject.bomberman;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.KeyTrigger;
import com.almasb.fxgl.input.Trigger;
import com.almasb.fxgl.input.TriggerListener;
import com.almasb.fxgl.physics.CollisionHandler;
import com.myproject.bomberman.components.*;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

import java.util.Vector;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.myproject.bomberman.BomberData.*;
import static com.myproject.bomberman.BomberData.MOVE_SUPPORT_PIXEL;
import static java.lang.Math.abs;

public class BombermanApp extends GameApplication {
    Entity player, bomb, wall, brick1, brick2;
    boolean bombCheck = true;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(800);
        settings.setHeight(600);
        settings.setTitle("Bomberman");
        settings.setDeveloperMenuEnabled(true);
    }

    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new BombermanFactory());
        wall = spawn("wall", 64, 64);
        grid[(int) (wall.getX() / 32)][(int) (wall.getY() / 32)] = 1;
        player = spawn("player", 0, 0);
        spawn("balloon",96,32);
        brick1 = spawn("brick", 96,96);
        grid[(int) (brick1.getX() / 32)][(int) (brick1.getY() / 32)] = 1;
        brick2 = spawn("brick", 32,96);
        grid[(int) (brick2.getX() / 32)][(int) (brick2.getY() / 32)] = 1;
    }

    @Override
    protected void initPhysics() {
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(BomberData.EntityType.PLAYER, BomberData.EntityType.WALL) {
            // order of types is the same as passed into the constructor
            @Override
            protected void onCollision(Entity player, Entity wall) {
                // go back if they are inside each other
                player.getComponent(BomberComponent.class).goBack(wall);
            }
        });
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(BomberData.EntityType.BOOM, BomberData.EntityType.PLAYER) {
            @Override
            protected void onCollision(Entity bomb, Entity player) {
//                player.getComponent(BomberComponent.class).goBack(bomb);
                double playerX = player.getBoundingBoxComponent().getMinXWorld();
                double playerRightX = player.getBoundingBoxComponent().getMinXWorld() + player.getWidth();
                double rivalX = bomb.getBoundingBoxComponent().getMinXWorld();
                double rivalRightX = bomb.getBoundingBoxComponent().getMinXWorld() + bomb.getWidth();
                double playerY = player.getBoundingBoxComponent().getMinYWorld();
                double playerBottomY = player.getBoundingBoxComponent().getMinYWorld() + player.getHeight();
                double rivalY = bomb.getBoundingBoxComponent().getMinYWorld();
                double rivalBottomY = bomb.getBoundingBoxComponent().getMinYWorld() + bomb.getHeight();;

                if ( playerRightX > rivalX && playerRightX - 2 <= rivalX && playerBottomY != rivalY && playerY != rivalBottomY ) player.translateX(rivalX - playerRightX);
                if ( playerX < rivalRightX && playerX + 2 >= rivalRightX && playerBottomY != rivalY && playerY != rivalBottomY )  player.translateX(rivalRightX - playerX);
                if ( playerBottomY > rivalY  && playerBottomY - 2 <= rivalY && playerX != rivalRightX && playerRightX != rivalX ) player.translateY(rivalY - playerBottomY);
                if ( playerY < rivalBottomY  && playerY + 2 >= rivalBottomY && playerX != rivalRightX && playerRightX != rivalX ) player.translateY(rivalBottomY - playerY);
            }
        });
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(BomberData.EntityType.BRICK, BomberData.EntityType.PLAYER) {
            @Override
            protected void onCollision(Entity brick, Entity player) {
                player.getComponent(BomberComponent.class).goBack(brick);
            }
        });
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(BomberData.EntityType.BRICK, BomberData.EntityType.FLAME) {
            @Override
            protected void onCollision(Entity brick, Entity flame) {
                if ( bomb.getX()/32 - brick.getX()/32 <= FLAME_SIZE && bomb.getY()/32 - brick.getY()/32 <= FLAME_SIZE
                    && (bomb.getX() == brick.getX() || bomb.getY() == brick.getY())){
                    brick.getComponent(BrickComponent.class).setBreak(true);
                    runOnce(() -> {
                        grid[(int) (brick.getX() / 32)][(int) (brick.getY() / 32)] = 0;
                        brick.removeFromWorld();
                    }, Duration.seconds(0.5));
                }
            }
        });
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(BomberData.EntityType.ENEMY, BomberData.EntityType.FLAME) {
            @Override
            protected void onCollision(Entity enemy, Entity flame) {
                if ( abs(bomb.getX() - enemy.getX()) <= FLAME_SIZE * 32 && abs(bomb.getY() - enemy.getY()) <= FLAME_SIZE * 32
                        && (bomb.getX() == enemy.getX() || bomb.getY() == enemy.getY())){
//                    System.out.println(enemy.getComponents());
                    enemy.getComponent(BalloonComponent.class).setDead(true);
                    runOnce(enemy::removeFromWorld, Duration.seconds(1));
                }
            }
        });
    }

    @Override
    protected void initInput() {
        Input input = FXGL.getInput();
        input.addTriggerListener(new TriggerListener() {
            @Override
            protected void onActionBegin(Trigger trigger) {
                if (trigger.isKey()) {
                    if (((KeyTrigger) trigger).getKey() == KeyCode.SPACE && bombCheck) {
                        bomb = spawn("boom", (int) ((player.getBoundingBoxComponent().getMinXWorld() + player.getWidth() / 2) / 32) * 32,
                                (int) ((player.getBoundingBoxComponent().getMinYWorld() + player.getHeight() / 2) / 32) * 32);
                        grid[(int) bomb.getX() / 32][(int) bomb.getY() / 32] = 2;
                        bomb.addComponent(new BombComponent());
                        Vector<Entity> E = new Vector<>();
                        bombCheck = false;
                        runOnce(() -> {
                            bomb.addComponent(new FlameCoreComponent());
                            for (int i = 0; i < FLAME_SIZE; i++) {
                                if ((int) (bomb.getX() / 32) - (i + 1) >= 0
                                        && grid[(int) (bomb.getX() / 32) - (i + 1)][(int) bomb.getY() / 32] != 1
                                        && grid[(int) (bomb.getX() / 32) - i][(int) bomb.getY() / 32] == 2) {
                                    if (i + 1 == FLAME_SIZE) {
                                        E.add(spawn("flameLeftHead", bomb.getX() - (i + 1) * 32, bomb.getY()));
                                    } else E.add(spawn("flameLeft", bomb.getX() - (i + 1) * 32, bomb.getY()));
                                    grid[(int) (bomb.getX() / 32) - (i + 1)][(int) bomb.getY() / 32] = 2;
                                }
                                if ( (int) (bomb.getX() / 32) + (i + 1) >= 0
                                        && grid[(int) (bomb.getX() / 32) + (i + 1)][(int) bomb.getY() / 32] != 1
                                        && grid[(int) (bomb.getX() / 32) + i][(int) bomb.getY() / 32] == 2) {
                                    if (i + 1 == FLAME_SIZE) {
                                        E.add(spawn("flameRightHead", bomb.getX() + (i + 1) * 32, bomb.getY()));
                                    } else E.add(spawn("flameRight", bomb.getX() + (i + 1) * 32, bomb.getY()));
                                    grid[(int) (bomb.getX() / 32) + (i + 1)][(int) bomb.getY() / 32] = 2;
                                }
                                if ((int)(bomb.getY() / 32) + (i + 1) >= 0
                                        && grid[(int) bomb.getX() / 32][(int) (bomb.getY() / 32) + (i + 1)] != 1
                                        && grid[(int) (bomb.getX() / 32)][(int) (bomb.getY() / 32) + i] == 2) {
                                    if (i + 1 == FLAME_SIZE) {
                                        E.add(spawn("flameDownHead", bomb.getX(), bomb.getY() + (i + 1) * 32));
                                    } else E.add(spawn("flameDown", bomb.getX(), bomb.getY() + (i + 1) * 32));
                                    grid[(int) bomb.getX() / 32][(int) bomb.getY() / 32 + (i + 1)] = 2;
                                }
                                if ((int)(bomb.getY() / 32) - (i + 1) >= 0
                                        && grid[(int) bomb.getX() / 32][(int) (bomb.getY() / 32) - (i + 1)] != 1
                                        && grid[(int) (bomb.getX() / 32)][(int) bomb.getY() / 32 - i] == 2) {
                                    if (i + 1 == FLAME_SIZE) {
                                        E.add(spawn("flameUpHead", bomb.getX(), bomb.getY() - (i + 1) * 32));
                                    } else E.add(spawn("flameUp", bomb.getX(), bomb.getY() - (i + 1) * 32));
                                    grid[(int) bomb.getX() / 32][(int) bomb.getY() / 32 - (i + 1)] = 2;
                                }
                            }
                            //System.out.println("hehehe");
                            runOnce(() -> {
                                for (Entity value : E) {
                                    grid[(int) (value.getX() / 32)][(int) (value.getY() / 32)] = 0;
                                    value.removeFromWorld();
                                }
                                grid[(int) bomb.getX() / 32][(int) bomb.getY() / 32] = 0;
                                bomb.removeFromWorld();
                                bombCheck = true;
                            }, Duration.seconds(0.75));
                            //Bomb.removeFromWorld();
                        }, Duration.seconds(2.5));
                    }
                }
            }

            @Override
            protected void onAction(Trigger trigger) {
                if (trigger.isKey()) {
                    if (((KeyTrigger) trigger).getKey() == KeyCode.D) {
                        player.getComponent(BomberComponent.class).moveRight();
                    }
                    if (((KeyTrigger) trigger).getKey() == KeyCode.W) {
                        player.getComponent(BomberComponent.class).moveUp();
                    }
                    if (((KeyTrigger) trigger).getKey() == KeyCode.S) {
                        player.getComponent(BomberComponent.class).moveDown();
                    }
                    if (((KeyTrigger) trigger).getKey() == KeyCode.A) {
                        player.getComponent(BomberComponent.class).moveLeft();
                    }
                }
            }

            @Override
            protected void onActionEnd(Trigger trigger) {
                player.getComponent(BomberComponent.class).stop();
            }
        });
    }

}