package com.myproject.bomberman;

import com.almasb.fxgl.physics.CollisionResult;
import com.almasb.fxgl.physics.HitBox;

public class FxglBoundingBoxComponent extends FxglComponent {

    public com.almasb.fxgl.entity.components.BoundingBoxComponent getFxglComponent() {
        return (com.almasb.fxgl.entity.components.BoundingBoxComponent) super.getFxglComponent();
    }

    public void addHitBox(HitBox hitBox) {
        getFxglComponent().addHitBox(hitBox);
    }

    public boolean checkCollision(FxglBoundingBoxComponent component) {
        return getFxglComponent().checkCollision(component.getFxglComponent(), new CollisionResult());
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
