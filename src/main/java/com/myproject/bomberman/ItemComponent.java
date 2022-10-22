package com.myproject.bomberman;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.util.Duration;

public class ItemComponent extends Component {
    private AnimatedTexture mainFrame;
    private Item type;

    public ItemComponent(Item type) {
        this.type = type;
        mainFrame = new AnimatedTexture(new AnimationChannel(FXGL.image(switch (type) {
            case BOMB -> "itemBomb.png";
            case FLAME -> "itemFlame.png";
            case SPEED -> "itemSpeed.png";
        }), 1, 32, 32, Duration.seconds(1), 0, 0));
        mainFrame.setTranslateX(-16);
        mainFrame.setTranslateY(-16);
    }

    public Item getType() {
        return type;
    }

    public AnimatedTexture getMainFrame() {
        return mainFrame;
    }
}
