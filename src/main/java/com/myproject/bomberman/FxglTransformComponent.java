package com.myproject.bomberman;

public class FxglTransformComponent extends FxglComponent {

    public com.almasb.fxgl.entity.components.TransformComponent getFxglComponent() {
        return (com.almasb.fxgl.entity.components.TransformComponent) super.getFxglComponent();
    }

    void setPosition(double x, double y) {
        getFxglComponent().setPosition(x, y);
    }
}
