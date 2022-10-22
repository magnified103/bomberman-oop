package com.myproject.bomberman;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.util.Duration;

public class DeathComponent extends TimerComponent {

    private AnimatedTexture deathFrame;

    public DeathComponent(double time, String assetName, int framesPerRow, int startFrame, int endFrame) {
        super(time);
        deathFrame = new AnimatedTexture(new AnimationChannel(
                FXGL.image(assetName),
                framesPerRow,
                32,
                32,
                Duration.seconds(time),
                startFrame,
                endFrame
        ));
        deathFrame.setTranslateX(-16);
        deathFrame.setTranslateY(-16);
        deathFrame.play();
        stop();
    }

    public AnimatedTexture getDeathFrame() {
        return deathFrame;
    }
}
