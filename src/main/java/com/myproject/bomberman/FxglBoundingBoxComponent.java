package com.myproject.bomberman;

public class FxglBoundingBoxComponent extends FxglComponent {

    public com.almasb.fxgl.entity.components.BoundingBoxComponent getFxglComponent() {
        return (com.almasb.fxgl.entity.components.BoundingBoxComponent) super.getFxglComponent();
    }

    public double getMinXWorld() {
        return getFxglComponent().getMinXWorld();
    }

    public double getMaxXWorld() {
        return getFxglComponent().getMaxXWorld();
    }

    public double getMinYWorld() {
        return getFxglComponent().getMinYWorld();
    }

    public double getMaxYWorld() {
        return getFxglComponent().getMaxYWorld();
    }
}
