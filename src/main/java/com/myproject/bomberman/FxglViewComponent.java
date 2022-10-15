package com.myproject.bomberman;

public class FxglViewComponent extends FxglComponent {

    @Override
    public com.almasb.fxgl.entity.components.ViewComponent getFxglComponent() {
        return (com.almasb.fxgl.entity.components.ViewComponent) super.getFxglComponent();
    }
}
