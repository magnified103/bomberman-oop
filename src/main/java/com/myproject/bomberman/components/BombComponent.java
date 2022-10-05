package com.myproject.bomberman.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.util.Duration;

public class BombComponent extends Component {
    private AnimatedTexture texture;
    private AnimationChannel animBomb;

    public BombComponent() {
        animBomb = new AnimationChannel(FXGL.image("Boom.png"), 3, 32, 32, Duration.seconds(0.5), 0, 2);
        texture = new AnimatedTexture(animBomb);
        texture.loopAnimationChannel(animBomb);
    }

    @Override
    public void onAdded() {
        //entity.getTransformComponent().setScaleOrigin(new Point2D(16, 21));
        entity.getViewComponent().addChild(texture);
    }
    @Override
    public void onUpdate(double tpf) {
        //texture.loopAnimationChannel(animBoom);
    }
}
