package com.myproject.bomberman;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.util.Duration;

public class PlantBombAnimationComponent extends Component {
    private AnimationChannel unActiveBomb;
    private AnimatedTexture mainFrame;
    private boolean cantPlant;

    public PlantBombAnimationComponent(String assetName) {
        unActiveBomb = new AnimationChannel(FXGL.image(assetName),
                3, 32, 32, Duration.seconds(0.3), 0, 2);
        mainFrame = new AnimatedTexture(unActiveBomb);
        mainFrame.loopAnimationChannel(unActiveBomb);
    }

    public AnimatedTexture getMainFrame() {
        return mainFrame;
    }

    public void setMainFrame(AnimatedTexture mainFrame) {
        this.mainFrame = mainFrame;
    }
}
