package com.myproject.bomberman;

public class TransformComponent extends Component {
    private int SPEED = 100;
    private com.almasb.fxgl.entity.components.TransformComponent fxglComponent;

    public int getSPEED() {
        return SPEED;
    }

    public void setSPEED(int SPEED) {
        this.SPEED = SPEED;
    }

    public com.almasb.fxgl.entity.components.TransformComponent getFxglComponent() {
        return fxglComponent;
    }

    public void setFxglComponent(com.almasb.fxgl.entity.components.TransformComponent fxglComponent) {
        this.fxglComponent = fxglComponent;
    }
}
