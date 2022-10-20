package com.myproject.bomberman;

import java.security.PrivateKey;

public class FxglTransformComponent extends FxglComponent {
    private int SPEED = 100;
    private int FLAME_SIZE = 1;
    private int[][] GRID = new int[1000][1000];

    private double FLAME_DURATION = 0.7;

    private int BOMB_CAPACITY = 1;

    public int getSPEED() {
        return SPEED;
    }

    public void setSPEED(int SPEED) {
        this.SPEED += SPEED;
    }

    public int getFLAME_SIZE() {
        return FLAME_SIZE;
    }

    public void addFLAME_SIZE() {
        this.FLAME_SIZE++;
    }

    public int getGRID(int x, int y) {
        return GRID[x][y];
    }

    public void setGRID(int x, int y, int value) {
        GRID[x][y] = value;
    }

    public double getFLAME_DURATION() {
        return FLAME_DURATION;
    }

    public void setFLAME_DURATION(double FLAME_DURATION) {
        this.FLAME_DURATION = FLAME_DURATION;
    }

    public int getBOMB_CAPACITY() {
        return BOMB_CAPACITY;
    }

    public void addBOMB_CAPACITY() {
        this.BOMB_CAPACITY++;
    }

    public com.almasb.fxgl.entity.components.TransformComponent getFxglComponent() {
        return (com.almasb.fxgl.entity.components.TransformComponent) super.getFxglComponent();
    }
}
