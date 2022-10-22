package com.myproject.bomberman;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.texture.*;
import javafx.util.Duration;

public class FlameAnimationComponent extends Component{
    private AnimatedTexture mainFrame;

    public FlameAnimationComponent(String assetName) {
        mainFrame = new AnimatedTexture(new AnimationChannel(FXGL.image(assetName),
                7,
                32,
                32,
                Duration.seconds(0.7),
                0,
                6
        ));
        mainFrame.setTranslateX(-16);
        mainFrame.setTranslateY(-16);
        mainFrame.play();
    }

    public AnimatedTexture getMainFrame() {
        return mainFrame;
    }
}
