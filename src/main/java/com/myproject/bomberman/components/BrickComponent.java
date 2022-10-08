package com.myproject.bomberman.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.util.Duration;

public class BrickComponent extends Component {
    private AnimatedTexture texture;
    private AnimationChannel animIdle, animBreak;

    private boolean Break = false, isTurn = true;

    public BrickComponent() {
        animIdle = new AnimationChannel(FXGL.image("Brick.png"), 1, 32, 32, Duration.seconds(1), 0, 0);
        animBreak = new AnimationChannel(FXGL.image("brickBreak.png"), 7, 32, 32, Duration.seconds(0.5), 0, 6);
        texture = new AnimatedTexture(animIdle);
    }

    @Override
    public void onAdded() {
        //entity.getTransformComponent().setScaleOrigin(new Point2D(16, 21));
        entity.getViewComponent().addChild(texture);
    }
    @Override
    public void onUpdate(double tpf) {
        if (Break && isTurn) {
            texture.playAnimationChannel(animBreak);
            isTurn = false;
        }
    }

    public void setBreak(boolean aBreak) {
        Break = aBreak;
    }

    public boolean isBreak() {
        return Break;
    }
}
