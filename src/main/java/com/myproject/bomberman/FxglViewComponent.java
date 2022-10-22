package com.myproject.bomberman;

import javafx.scene.Node;

public class FxglViewComponent extends FxglComponent {

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
