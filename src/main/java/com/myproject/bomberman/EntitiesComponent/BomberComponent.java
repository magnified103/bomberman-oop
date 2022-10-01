package com.myproject.bomberman.EntitiesComponent;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.*;
import javafx.util.Duration;

public class BomberComponent extends Component {
    private AnimatedTexture texture;
    private AnimationChannel animIdle[] = new AnimationChannel[4], animWalkRight, animWalkLeft, animWalkUp, animWalkDown, animDeadth;
    private final int BOMBERMAN_SPEED = 100;
    public BomberComponent() {
        animIdle[0] = new AnimationChannel(FXGL.image("BombermanMove.png"), 12, 32, 32, Duration.seconds(1), 1, 1);
        animIdle[1] = new AnimationChannel(FXGL.image("BombermanMove.png"), 12, 32, 32, Duration.seconds(1), 4, 4);
        animIdle[2] = new AnimationChannel(FXGL.image("BombermanMove.png"), 12, 32, 32, Duration.seconds(1), 7, 7);
        animIdle[3] = new AnimationChannel(FXGL.image("BombermanMove.png"), 12, 32, 32, Duration.seconds(1), 10, 10);
        animWalkRight = new AnimationChannel(FXGL.image("BombermanMove.png"), 12, 32, 32, Duration.seconds(0.35), 9, 11);
        animWalkLeft = new AnimationChannel(FXGL.image("BombermanMove.png"), 12, 32, 32, Duration.seconds(0.35), 6, 8);
        animWalkUp = new AnimationChannel(FXGL.image("BombermanMove.png"), 12, 32, 32, Duration.seconds(0.35), 3, 5);
        animWalkDown = new AnimationChannel(FXGL.image("BombermanMove.png"), 12, 32, 32, Duration.seconds(0.35), 0, 2);
        animDeadth = new AnimationChannel(FXGL.image("dead.png"),6, 24, 35, Duration.seconds(2), 0, 5);
        texture = new AnimatedTexture(animIdle[0]);
        texture.loopAnimationChannel(animWalkDown);
    }

    @Override
    public void onAdded() {
        //entity.getTransformComponent().setScaleOrigin(new Point2D(16, 21));
        entity.getViewComponent().addChild(texture);
    }

    @Override
    public void onUpdate(double tpf) {

    }
}
