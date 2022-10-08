package com.myproject.bomberman.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.util.Duration;

public class FlameCoreComponent extends Component {
    private AnimatedTexture texture;
    private AnimationChannel animFlameCore;

    public FlameCoreComponent() {
        animFlameCore = new AnimationChannel(FXGL.image("flameCore.png"), 7, 32, 32, Duration.seconds(0.75), 0, 6);
        texture = new AnimatedTexture(animFlameCore);
        texture.playAnimationChannel(animFlameCore);
    }

    @Override
    public void onAdded() {
        //entity.getTransformComponent().setScaleOrigin(new Point2D(16, 21));
        entity.getViewComponent().addChild(texture);
    }

}
