package com.myproject.bomberman.components;

import javafx.geometry.Point2D;

public enum WalkDirection {
    UP(new Point2D(0, -1)),
    DOWN(new Point2D(0, 1)),
    LEFT(new Point2D(-1, 0)),
    RIGHT(new Point2D(1, 0));

    private Point2D vector;

    WalkDirection(Point2D vector) {
        this.vector = vector;
    }

    public Point2D getDirectionVector() {
        return vector;
    }

    public int getIntegralX() {
        return (int) vector.getX();
    }

    public int getIntegralY() {
        return (int) vector.getY();
    }
}
