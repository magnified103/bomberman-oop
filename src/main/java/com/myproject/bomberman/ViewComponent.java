package com.myproject.bomberman;

public class ViewComponent extends Component {

    private com.almasb.fxgl.entity.components.ViewComponent fxglComponent;

    public com.almasb.fxgl.entity.components.ViewComponent getFxglComponent() {
        return fxglComponent;
    }

    public void setFxglComponent(com.almasb.fxgl.entity.components.ViewComponent fxglComponent) {
        this.fxglComponent = fxglComponent;
    }
}
