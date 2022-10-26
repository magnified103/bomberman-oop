package com.myproject.bomberman;

import javafx.geometry.Point2D;

public class TransformComponent extends FxglComponent {
    public com.almasb.fxgl.entity.components.TransformComponent getFxglComponent() {
        return (com.almasb.fxgl.entity.components.TransformComponent) super.getFxglComponent();
    }

    public void setPosition(double x, double y) {
        getFxglComponent().setPosition(x, y);
    }

    public double getX() {
        return getFxglComponent().getX();
    }

    public double getY() {
        return getFxglComponent().getY();
    }

    public void translate(Point2D vec) {
        getFxglComponent().translate(vec);
    }
}
