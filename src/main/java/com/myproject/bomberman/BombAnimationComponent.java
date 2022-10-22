package com.myproject.bomberman;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.util.Duration;

public class BombAnimationComponent extends Component {

    private AnimatedTexture mainFrame;

    public BombAnimationComponent(String assetName) {
        mainFrame = new AnimatedTexture(new AnimationChannel(FXGL.image(assetName),
                3,
                32,
                32,
                Duration.seconds(0.3),
                0,
                2
        ));
        mainFrame.setTranslateX(-16);
        mainFrame.setTranslateY(-16);
        mainFrame.loop();
    }

    public AnimatedTexture getMainFrame() {
        return mainFrame;
    }


}
