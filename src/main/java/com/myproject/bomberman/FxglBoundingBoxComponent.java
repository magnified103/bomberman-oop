package com.myproject.bomberman;

public class FxglBoundingBoxComponent extends FxglComponent {

    public com.almasb.fxgl.entity.components.BoundingBoxComponent getFxglComponent() {
        return (com.almasb.fxgl.entity.components.BoundingBoxComponent) super.getFxglComponent();
    }
}
