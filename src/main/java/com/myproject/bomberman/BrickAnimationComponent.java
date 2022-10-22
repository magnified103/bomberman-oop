package com.myproject.bomberman;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.util.Duration;

public class BrickAnimationComponent extends Component {
    private AnimationChannel normal;
    private AnimationChannel burning;
    private AnimatedTexture mainFrame;

    public BrickAnimationComponent() {
        normal = new AnimationChannel(FXGL.image("Brick.png"),
                1, 32, 32, Duration.seconds(1), 0, 0);
        burning = new AnimationChannel(FXGL.image("brickBreak.png"),
                7, 32, 32, Duration.seconds(1), 0, 6);
        mainFrame = new AnimatedTexture(normal);
        mainFrame.setTranslateX(-16);
        mainFrame.setTranslateY(-16);
    }

    public AnimatedTexture getMainFrame() {
        return mainFrame;
    }

    public void fire() {
        mainFrame.playAnimationChannel(burning);
    }
}
