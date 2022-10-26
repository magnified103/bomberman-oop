package com.myproject.bomberman;

import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.scene.Node;

import java.util.HashMap;
import java.util.Map;

public class ViewComponent extends FxglComponent {

    private Map<String, AnimationChannel> animationMap;
    private AnimatedTexture mainFrame;
    private String currentAnimationName;
    private boolean animationStopped;

    public ViewComponent() {
        animationMap = new HashMap<>();
    }

    public void initializeAnimation(String animationName) {
        if (!animationMap.containsKey(animationName)) {
            throw new RuntimeException(String.format("Animation name \"%s\" not found.", animationName));
        }
        mainFrame = new AnimatedTexture(animationMap.get(animationName));
        getFxglComponent().addChild(mainFrame);
        currentAnimationName = animationName;
        animationStopped = true;
    }

    public void addAnimation(String animationName, AnimationChannel channel) {
        if (animationMap.containsKey(animationName)) {
            throw new RuntimeException(String.format("Animation name \"%s\" already exists.", animationName));
        }
        animationMap.put(animationName, channel);
    }

    public boolean isAnimationStopped() {
        return animationStopped;
    }

    public String getCurrentAnimationName() {
        return currentAnimationName;
    }

    public boolean hasAnimation(String name) {
        return animationMap.containsKey(name);
    }

    public void setAnimationTranslateX(double value) {
        mainFrame.setTranslateX(value);
    }

    public void setAnimationTranslateY(double value) {
        mainFrame.setTranslateY(value);
    }

    public void setAnimationTranslate(double valueX, double valueY) {
        mainFrame.setTranslateX(valueX);
        mainFrame.setTranslateY(valueY);
    }

    public void play() {
        mainFrame.play();
        animationStopped = false;
    }

    public void play(String animationName) {
        if (!animationMap.containsKey(animationName)) {
            throw new RuntimeException(String.format("Animation name \"%s\" not found.", animationName));
        }
        mainFrame.playAnimationChannel(animationMap.get(animationName));
        currentAnimationName = animationName;
        animationStopped = false;
    }

    public void loop() {
        mainFrame.loop();
        animationStopped = false;
    }

    public void loop(String animationName) {
        if (!animationMap.containsKey(animationName)) {
            throw new RuntimeException(String.format("Animation name \"%s\" not found.", animationName));
        }
        mainFrame.loopAnimationChannel(animationMap.get(animationName));
        currentAnimationName = animationName;
        animationStopped = false;
    }

    public void stop() {
        mainFrame.stop();
        animationStopped = true;
    }

    @Override
    public com.almasb.fxgl.entity.components.ViewComponent getFxglComponent() {
        return (com.almasb.fxgl.entity.components.ViewComponent) super.getFxglComponent();
    }

    public void addChild(Node node) {
        getFxglComponent().addChild(node);
    }

    public void setZIndex(int value) {
        getFxglComponent().setZIndex(value);
    }
}
