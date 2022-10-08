package com.myproject.bomberman.components;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.*;
import javafx.geometry.Point2D;
import javafx.util.Duration;

import static com.myproject.bomberman.BomberData.MOVE_SUPPORT_PIXEL;
import static com.myproject.bomberman.BomberData.SPEED;

public class BomberComponent extends Component {
    private int speedX, speedY, index;
    private AnimatedTexture texture;
    private AnimationChannel animIdle[] = new AnimationChannel[4], animWalkRight, animWalkLeft, animWalkUp, animWalkDown;
    //private final int BOMBERMAN_SPEED = 100;
    public BomberComponent() {
        animIdle[0] = new AnimationChannel(FXGL.image("BombermanMove.png"), 12, 32, 32, Duration.seconds(1), 1, 1);
        animIdle[1] = new AnimationChannel(FXGL.image("BombermanMove.png"), 12, 32, 32, Duration.seconds(1), 4, 4);
        animIdle[2] = new AnimationChannel(FXGL.image("BombermanMove.png"), 12, 32, 32, Duration.seconds(1), 7, 7);
        animIdle[3] = new AnimationChannel(FXGL.image("BombermanMove.png"), 12, 32, 32, Duration.seconds(1), 10, 10);
        animWalkRight = new AnimationChannel(FXGL.image("BombermanMove.png"), 12, 32, 32, Duration.seconds(0.25), 9, 11);
        animWalkLeft = new AnimationChannel(FXGL.image("BombermanMove.png"), 12, 32, 32, Duration.seconds(0.25), 6, 8);
        animWalkUp = new AnimationChannel(FXGL.image("BombermanMove.png"), 12, 32, 32, Duration.seconds(0.25), 3, 5);
        animWalkDown = new AnimationChannel(FXGL.image("BombermanMove.png"), 12, 32, 32, Duration.seconds(0.25), 0, 2);
        texture = new AnimatedTexture(animIdle[0]);
    }

    @Override
    public void onAdded() {
//        entity.getTransformComponent().setScaleOrigin(new Point2D(16, 21));
        entity.getViewComponent().addChild(texture);
    }

    @Override
    public void onUpdate(double tpf) {
        if (index==0) {
            if (speedY != 0) {

                if (texture.getAnimationChannel() == animIdle[0] || texture.getAnimationChannel() == animIdle[1]
                        || texture.getAnimationChannel() == animIdle[2] || texture.getAnimationChannel() == animIdle[3]
                        || texture.getAnimationChannel() == animWalkUp || texture.getAnimationChannel() == animWalkLeft
                        || texture.getAnimationChannel() == animWalkRight
                ) {
                    texture.loopAnimationChannel(animWalkDown);
                }

                speedY = (int) (speedY * 0.8);
                entity.translateY((int) (speedY * tpf));

                if (FXGLMath.abs(speedY) < 1) {
                    speedY = 0;
                    texture.loopAnimationChannel(animIdle[0]);
                }
            }
        }
        if (index==1) {
            if (speedY != 0) {

                if (texture.getAnimationChannel() == animIdle[0] || texture.getAnimationChannel() == animIdle[1]
                        || texture.getAnimationChannel() == animIdle[2] || texture.getAnimationChannel() == animIdle[3]
                        || texture.getAnimationChannel() == animWalkDown || texture.getAnimationChannel() == animWalkLeft
                        || texture.getAnimationChannel() == animWalkRight
                ) {
                    texture.loopAnimationChannel(animWalkUp);
                }

                speedY = (int) (speedY * 0.8);
                entity.translateY((int)(speedY * tpf));
                if (FXGLMath.abs(speedY) < 1) {
                    speedY = 0;
                    texture.loopAnimationChannel(animIdle[1]);
                }
            }
        }
        if (index == 2) {
            if (speedX != 0) {

                if (texture.getAnimationChannel() == animIdle[0] || texture.getAnimationChannel() == animIdle[1]
                        || texture.getAnimationChannel() == animIdle[2] || texture.getAnimationChannel() == animIdle[3]
                        || texture.getAnimationChannel() == animWalkUp || texture.getAnimationChannel() == animWalkDown
                        || texture.getAnimationChannel() == animWalkRight
                ) {
                    texture.loopAnimationChannel(animWalkLeft);
                }

                speedX = (int) (speedX * 0.8);
                entity.translateX((int)(speedX * tpf));

                if (FXGLMath.abs(speedX) < 1) {
                    speedX = 0;
                    texture.loopAnimationChannel(animIdle[2]);
                }
            }
        }
        if (index == 3) {
            if (speedX != 0) {

                if (texture.getAnimationChannel() == animIdle[0] || texture.getAnimationChannel() == animIdle[1]
                        || texture.getAnimationChannel() == animIdle[2] || texture.getAnimationChannel() == animIdle[3]
                        || texture.getAnimationChannel() == animWalkUp || texture.getAnimationChannel() == animWalkDown
                        || texture.getAnimationChannel() == animWalkLeft
                ) {
                    texture.loopAnimationChannel(animWalkRight);
                }

                speedX = (int) (speedX * 0.8);
                entity.translateX((int)(speedX * tpf));

                if (FXGLMath.abs(speedX) < 1) {
                    speedX = 0;
                    texture.loopAnimationChannel(animIdle[3]);
                }
            }
        }
    }

    public void moveDown() {
        speedY = SPEED;
        index = 0;
        getEntity().setScaleX(1);
    }

    public void moveUp() {
        speedY = -SPEED;
        index = 1;
        getEntity().setScaleX(1);
    }

    public void moveLeft() {
        speedX = -SPEED;
        index = 2;
        getEntity().setScaleX(1);
    }

    public void moveRight() {
        speedX = SPEED;
        index = 3;
        getEntity().setScaleX(1);
    }

    public void stop() {
        speedX = 1;
        speedY = 1;
    }

    public void goBack(Entity RivalEntity) {

        double playerX = entity.getBoundingBoxComponent().getMinXWorld();
        double playerRightX = entity.getBoundingBoxComponent().getMinXWorld() + entity.getWidth();
        double rivalX = RivalEntity.getBoundingBoxComponent().getMinXWorld();
        double rivalRightX = RivalEntity.getBoundingBoxComponent().getMinXWorld() + RivalEntity.getWidth();
        double playerY = entity.getBoundingBoxComponent().getMinYWorld();
        double playerBottomY = entity.getBoundingBoxComponent().getMinYWorld() + entity.getHeight();
        double rivalY = RivalEntity.getBoundingBoxComponent().getMinYWorld();
        double rivalBottomY = RivalEntity.getBoundingBoxComponent().getMinYWorld() + RivalEntity.getHeight();;

        if ( playerRightX > rivalX && playerRightX - MOVE_SUPPORT_PIXEL <= rivalX && playerBottomY != rivalY && playerY != rivalBottomY ) entity.translateX(rivalX - playerRightX);
        if ( playerX < rivalRightX && playerX + MOVE_SUPPORT_PIXEL >= rivalRightX && playerBottomY != rivalY && playerY != rivalBottomY )  entity.translateX(rivalRightX - playerX);
        if ( playerBottomY > rivalY  && playerBottomY - MOVE_SUPPORT_PIXEL <= rivalY && playerX != rivalRightX && playerRightX != rivalX ) entity.translateY(rivalY - playerBottomY);
        if ( playerY < rivalBottomY  && playerY + MOVE_SUPPORT_PIXEL >= rivalBottomY && playerX != rivalRightX && playerRightX != rivalX ) entity.translateY(rivalBottomY - playerY);
    }

}
