package com.myproject.bomberman.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.util.Duration;

public class BalloonComponent extends Component {

    boolean Dead = false, isTurn = true;
    private AnimationChannel anim, animDead;
    private AnimatedTexture texture;
    public BalloonComponent() {
        anim = new AnimationChannel(FXGL.image("balloon.png"),5,32,32, Duration.seconds(0.5),0,2);
        animDead = new AnimationChannel(FXGL.image("balloon.png"),5,32,32, Duration.seconds(1),3,4);
        texture = new AnimatedTexture(anim);
        texture.loopAnimationChannel(anim);
    }

    @Override
    public void onAdded() {
        //entity.getTransformComponent().setScaleOrigin(new Point2D(16, 21));
        entity.getViewComponent().addChild(texture);
    }

    @Override
    public void onUpdate(double tpf) {
        if (Dead && isTurn) {
            texture.playAnimationChannel(animDead);
            isTurn = false;
        }
    }

    public void setDead(boolean aDead) {
        Dead = aDead;
    }

    public boolean isDead() {
        return Dead;
    }
}
