package com.myproject.bomberman;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.util.Duration;

public class PortalAnimationComponent extends Component {

    private AnimatedTexture mainFrame;

    public PortalAnimationComponent(String assetName) {
        mainFrame = new AnimatedTexture(new AnimationChannel(FXGL.image(assetName),
                2,
                32,
                32,
                Duration.seconds(0.2),
                0,
                1
        ));
        mainFrame.setTranslateX(-16);
        mainFrame.setTranslateY(-16);
    }

    public AnimatedTexture getMainFrame() {
        return mainFrame;
    }

    public void open() {
        mainFrame.play();
    }
}
