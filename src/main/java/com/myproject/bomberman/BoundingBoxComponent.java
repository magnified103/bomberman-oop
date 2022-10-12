package com.myproject.bomberman;

public class BoundingBoxComponent extends Component {

    private com.almasb.fxgl.entity.components.BoundingBoxComponent fxglComponent;

    public com.almasb.fxgl.entity.components.BoundingBoxComponent getFxglComponent() {
        return fxglComponent;
    }

    public void setFxglComponent(
            com.almasb.fxgl.entity.components.BoundingBoxComponent fxglComponent) {
        this.fxglComponent = fxglComponent;
    }
}
