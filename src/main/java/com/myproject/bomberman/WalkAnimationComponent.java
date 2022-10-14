package com.myproject.bomberman;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.util.Duration;

public class WalkAnimationComponent extends Component {

    private AnimationChannel moveUp;
    private AnimationChannel moveDown;
    private AnimationChannel moveLeft;
    private AnimationChannel moveRight;
    private AnimatedTexture mainFrame;
    private boolean stopped;

    public WalkAnimationComponent(String assetName) {
        moveUp = new AnimationChannel(FXGL.image(assetName),
                12, 32, 32, Duration.seconds(0.25), 3, 5);
        moveDown = new AnimationChannel(FXGL.image(assetName),
                12, 32, 32, Duration.seconds(0.25), 0, 2);
        moveLeft = new AnimationChannel(FXGL.image(assetName),
                12, 32, 32, Duration.seconds(0.25), 6, 8);
        moveRight = new AnimationChannel(FXGL.image(assetName),
                12, 32, 32, Duration.seconds(0.25), 9, 11);
        mainFrame = new AnimatedTexture(moveDown); // default to down
        stopped = true;
    }

    public AnimatedTexture getMainFrame() {
        return mainFrame;
    }

    public boolean isMoveUp() {
        return mainFrame.getAnimationChannel() == moveUp && !stopped;
    }

    public boolean isMoveDown() {
        return mainFrame.getAnimationChannel() == moveDown && !stopped;
    }

    public boolean isMoveLeft() {
        return mainFrame.getAnimationChannel() == moveLeft && !stopped;
    }

    public boolean isMoveRight() {
        return mainFrame.getAnimationChannel() == moveRight && !stopped;
    }

    public boolean isStopped() {
        return stopped;
    }

    public void doMoveUp() {
        mainFrame.loopAnimationChannel(moveUp);
        stopped = false;
    }

    public void doMoveDown() {
        mainFrame.loopAnimationChannel(moveDown);
        stopped = false;
    }

    public void doMoveLeft() {
        mainFrame.loopAnimationChannel(moveLeft);
        stopped = false;
    }

    public void doMoveRight() {
        mainFrame.loopAnimationChannel(moveRight);
        stopped = false;
    }

    public void stop() {
        mainFrame.stop();
        stopped = true;
    }
}
