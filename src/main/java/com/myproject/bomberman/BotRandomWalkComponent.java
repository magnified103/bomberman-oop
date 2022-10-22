package com.myproject.bomberman;

import javafx.geometry.Point2D;

import java.util.Random;

public class BotRandomWalkComponent extends Component {

    // up down left right
    private static final Point2D DIRECTION_VECTORS[] = {
            new Point2D(0, -1),
            new Point2D(0, 1),
            new Point2D(-1, 0),
            new Point2D(1, 0)
    };
    private static final int THRESHOLD = 23;

    private double probability;
    private Random generator;
    private int lastRowIndex;
    private int lastColumnIndex;
    private int counter;
    private Point2D lastDirectionVector;
    private int speed = 100;

    public BotRandomWalkComponent(double probability) {
        this.probability = probability;
        this.generator = new Random();
        this.lastRowIndex = -1;
        this.lastColumnIndex = -1;
        this.counter = 0;
        this.lastDirectionVector = DIRECTION_VECTORS[0];
    }

    public boolean coinFlip() {
        return generator.nextFloat() < probability;
    }

    public Point2D randomDirectionVector() {
        return DIRECTION_VECTORS[generator.nextInt(DIRECTION_VECTORS.length)];
    }

    public int getLastRowIndex() {
        return lastRowIndex;
    }

    public void setLastRowIndex(int lastRowIndex) {
        this.lastRowIndex = lastRowIndex;
    }

    public int getLastColumnIndex() {
        return lastColumnIndex;
    }

    public void setLastColumnIndex(int lastColumnIndex) {
        this.lastColumnIndex = lastColumnIndex;
    }

    public void compare(int rowIndex, int columnIndex) {
        if (lastRowIndex == rowIndex && lastColumnIndex == columnIndex) {
            counter++;
        } else {
            counter = 1;
        }
    }

    public void setIndex(int rowIndex, int columnIndex) {
        lastRowIndex = rowIndex;
        lastColumnIndex = columnIndex;
    }

    public boolean overflow() {
        return counter > THRESHOLD;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public Point2D getLastDirectionVector() {
        return lastDirectionVector;
    }

    public void setLastDirectionVector(Point2D lastDirectionVector) {
        this.lastDirectionVector = lastDirectionVector;
    }

    public int getSpeed() {
        return speed;
    }
}
