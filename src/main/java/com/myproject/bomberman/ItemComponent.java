package com.myproject.bomberman;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.util.Duration;

public class ItemComponent extends Component {
    private AnimatedTexture mainFrame;
    private AnimationChannel itemAnim;
    private ItemType type;

    public ItemComponent(ItemType type) {
        this.type = type;
        switch (type) {
            case BOMB -> itemAnim = new AnimationChannel(FXGL.image("itemBomb.png"),
                    1, 32, 32, Duration.seconds(1), 0, 0);
            case FLAME -> itemAnim = new AnimationChannel(FXGL.image("itemFlame.png"),
                    1, 32, 32, Duration.seconds(1), 0, 0);
            case SPEED -> itemAnim = new AnimationChannel(FXGL.image("itemSpeed.png"),
                    1, 32, 32, Duration.seconds(1), 0, 0);
        }
        mainFrame = new AnimatedTexture(itemAnim);
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    public AnimatedTexture getMainFrame() {
        return mainFrame;
    }

    public void setMainFrame(AnimatedTexture MainFrame) {
        this.mainFrame = MainFrame;
    }
}
