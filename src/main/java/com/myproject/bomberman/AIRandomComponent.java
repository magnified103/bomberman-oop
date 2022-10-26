package com.myproject.bomberman;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;

public class AIRandomComponent extends AIComponent {

    private static final int THRESHOLD = 23;

    private double probability;
    private Random generator;
    private int lastRowIndex;
    private int lastColumnIndex;
    private int counter;
    private WalkDirection lastDirection;

    // up down left right
    private EnumSet<WalkDirection> directionSet;

    public AIRandomComponent(double probability) {
        this.probability = probability;
        this.generator = new Random();
        this.lastRowIndex = -1;
        this.lastColumnIndex = -1;
        this.counter = 0;
        this.lastDirection = WalkDirection.UP;
        this.directionSet = EnumSet.allOf(WalkDirection.class);
    }

    public boolean coinFlip() {
        return generator.nextFloat() < probability;
    }

    public WalkDirection randomDirection() {
        List<WalkDirection> directions = new ArrayList<>(directionSet);
        if (directions.isEmpty()) {
            return WalkDirection.UP;
        }
        return directions.get(generator.nextInt(directions.size()));
    }

    public void signal(WalkDirection direction, boolean state) {
        if (state) {
            directionSet.add(direction);
        } else {
            directionSet.remove(direction);
        }
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

    public WalkDirection getLastDirection() {
        return lastDirection;
    }

    public void setLastDirection(WalkDirection lastDirection) {
        this.lastDirection = lastDirection;
    }
}
