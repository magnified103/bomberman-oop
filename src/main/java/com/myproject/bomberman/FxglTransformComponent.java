package com.myproject.bomberman;

import java.security.PrivateKey;

public class FxglTransformComponent extends FxglComponent {
    private int SPEED = 100;
    private int FLAME_SIZE = 1;
    private int[][] GRID = new int[1000][1000];

    public int getSPEED() {
        return SPEED;
    }

    public void setSPEED(int SPEED) {
        this.SPEED = SPEED;
    }

    public int getFLAME_SIZE() {
        return FLAME_SIZE;
    }

    public void setFLAME_SIZE(int FLAME_SIZE) {
        this.FLAME_SIZE = FLAME_SIZE;
    }

    public int getGRID(int x, int y) {
        return GRID[x][y];
    }

    public void setGRID(int x, int y, int value) {
        GRID[x][y] = value;
    }

    public com.almasb.fxgl.entity.components.TransformComponent getFxglComponent() {
        return (com.almasb.fxgl.entity.components.TransformComponent) super.getFxglComponent();
    }
}
