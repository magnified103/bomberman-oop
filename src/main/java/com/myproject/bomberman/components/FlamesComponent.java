package com.myproject.bomberman.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.util.Duration;

public class FlamesComponent extends Component {
    private AnimatedTexture texture;
    private AnimationChannel animFlameRight, animFlameRightHead, animFlameLeft,
            animFlameLeftHead, animFlameUp, animFlameUpHead, animFlameDown, animFlameDownHead;

    public FlamesComponent(String position) {
        animFlameRight = new AnimationChannel(FXGL.image("flameRight.png"), 7, 32, 32, Duration.seconds(0.75), 0, 6);
        animFlameLeft = new AnimationChannel(FXGL.image("flameLeft.png"), 7, 32, 32, Duration.seconds(0.75), 0, 6);
        animFlameDown = new AnimationChannel(FXGL.image("flameDown.png"), 7, 32, 32, Duration.seconds(0.75), 0, 6);
        animFlameUp = new AnimationChannel(FXGL.image("flameUp.png"), 7, 32, 32, Duration.seconds(0.75), 0, 6);

        animFlameRightHead = new AnimationChannel(FXGL.image("flameRightHead.png"), 7, 32, 32, Duration.seconds(0.75), 0, 6);
        animFlameLeftHead = new AnimationChannel(FXGL.image("flameLeftHead.png"), 7, 32, 32, Duration.seconds(0.75), 0, 6);
        animFlameDownHead = new AnimationChannel(FXGL.image("flameDownHead.png"), 7, 32, 32, Duration.seconds(0.75), 0, 6);
        animFlameUpHead = new AnimationChannel(FXGL.image("flameUpHead.png"), 7, 32, 32, Duration.seconds(0.75), 0, 6);
        texture = new AnimatedTexture(animFlameLeft);
        switch (position) {
            case "left" -> texture.playAnimationChannel(animFlameLeft);
            case "right" -> texture.playAnimationChannel(animFlameRight);
            case "up" -> texture.playAnimationChannel(animFlameUp);
            case "down" -> texture.playAnimationChannel(animFlameDown);
            case "leftHead" -> texture.playAnimationChannel(animFlameLeftHead);
            case "rightHead" -> texture.playAnimationChannel(animFlameRightHead);
            case "upHead" -> texture.playAnimationChannel(animFlameUpHead);
            case "downHead" -> texture.playAnimationChannel(animFlameDownHead);
        }
    }

    @Override
    public void onAdded() {
        //entity.getTransformComponent().setScaleOrigin(new Point2D(16, 21));
        entity.getViewComponent().addChild(texture);
    }
}
