package com.myproject.bomberman.core;

public abstract class FxglComponent extends Component {

    private com.almasb.fxgl.entity.component.Component fxglComponent;

    protected com.almasb.fxgl.entity.component.Component getFxglComponent() {
        return fxglComponent;
    }

    public void setFxglComponent(com.almasb.fxgl.entity.component.Component fxglComponent) {
        this.fxglComponent = fxglComponent;
    }
}
