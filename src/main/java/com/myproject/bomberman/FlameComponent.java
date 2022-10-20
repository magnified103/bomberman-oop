package com.myproject.bomberman;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.texture.*;
import javafx.util.Duration;

public class FlameComponent extends Component{
    private AnimatedTexture mainFrame;
    private AnimationChannel animFlame;
    private double flameDuration;

    public FlameComponent(String type, double flameDuration) {
        this.flameDuration = flameDuration;
        switch (type) {
            case "left" ->
                    animFlame = new AnimationChannel(FXGL.image("flameLeft.png"), 7, 32, 32, Duration.seconds(flameDuration), 0, 6);
            case "right" ->
                    animFlame = new AnimationChannel(FXGL.image("flameRight.png"), 7, 32, 32, Duration.seconds(flameDuration), 0, 6);
            case "up" ->
                    animFlame = new AnimationChannel(FXGL.image("flameUp.png"), 7, 32, 32, Duration.seconds(flameDuration), 0, 6);
            case "down" ->
                    animFlame = new AnimationChannel(FXGL.image("flameDown.png"), 7, 32, 32, Duration.seconds(flameDuration), 0, 6);
            case "leftHead" ->
                    animFlame = new AnimationChannel(FXGL.image("flameLeftHead.png"), 7, 32, 32, Duration.seconds(flameDuration), 0, 6);
            case "rightHead" ->
                    animFlame = new AnimationChannel(FXGL.image("flameRightHead.png"), 7, 32, 32, Duration.seconds(flameDuration), 0, 6);
            case "upHead" ->
                    animFlame = new AnimationChannel(FXGL.image("flameUpHead.png"), 7, 32, 32, Duration.seconds(flameDuration), 0, 6);
            case "downHead" ->
                    animFlame = new AnimationChannel(FXGL.image("flameDownHead.png"), 7, 32, 32, Duration.seconds(flameDuration), 0, 6);
        }
        mainFrame = new AnimatedTexture(animFlame);
        mainFrame.loopAnimationChannel(animFlame);
    }


    public AnimatedTexture getMainFrame() {
        return mainFrame;
    }

    public void setMainFrame(AnimatedTexture mainFrame) {
        this.mainFrame = mainFrame;
    }

    public double getFlameDuration() {
        return flameDuration;
    }

    public void setFlameDuration(double flameDuration) {
        this.flameDuration = flameDuration;
    }
}
