package com.myproject.bomberman;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.util.Duration;

public class BrickComponent extends Component {
    private AnimationChannel nonBreakBrick, breakBrick;
    private AnimatedTexture mainFrame;

    private int dead = 0;

    public BrickComponent() {
        nonBreakBrick = new AnimationChannel(FXGL.image("Brick.png"),
                1, 32, 32, Duration.seconds(1), 0, 0);
        breakBrick = new AnimationChannel(FXGL.image("brickBreak.png"),
                7, 32, 32, Duration.seconds(1), 0, 6);
        mainFrame = new AnimatedTexture(nonBreakBrick);
        mainFrame.setTranslateX(-16);
        mainFrame.setTranslateY(-16);
    }

    public AnimatedTexture getMainFrame() {
        return mainFrame;
    }

    public void breakBrick() {
        mainFrame.playAnimationChannel(breakBrick);
    }

    public int getDead() {
        return dead;
    }
}
