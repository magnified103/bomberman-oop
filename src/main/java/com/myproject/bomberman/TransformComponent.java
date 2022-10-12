package com.myproject.bomberman;

public class TransformComponent extends Component {

    private com.almasb.fxgl.entity.components.TransformComponent fxglComponent;

    public com.almasb.fxgl.entity.components.TransformComponent getFxglComponent() {
        return fxglComponent;
    }

    public void setFxglComponent(com.almasb.fxgl.entity.components.TransformComponent fxglComponent) {
        this.fxglComponent = fxglComponent;
    }
}
