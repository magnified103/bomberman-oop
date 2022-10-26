package com.myproject.bomberman;

import javafx.geometry.Point2D;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public class WalkComponent extends InputComponent {

    private EnumSet<WalkDirection> moveSet;
    private Map<String, WalkDirection> signatureMap;
    private double speed;

    public WalkComponent(double speed) {
        this.speed = speed;
        this.moveSet = EnumSet.noneOf(WalkDirection.class);
        this.signatureMap = new HashMap<>();
    }

    public WalkComponent(String signatureUp, String signatureDown,
                         String signatureLeft, String signatureRight, int speed) {
        this(speed);
        signatureMap.put(signatureUp, WalkDirection.UP);
        signatureMap.put(signatureDown, WalkDirection.DOWN);
        signatureMap.put(signatureLeft, WalkDirection.LEFT);
        signatureMap.put(signatureRight, WalkDirection.RIGHT);
    }

    void addSignature(String signature, WalkDirection direction) {
        signatureMap.put(signature, direction);
    }

    @Override
    public void processInput(String key, InputState state) {
        if (signatureMap.containsKey(key)) {
            moveSet.clear();
            if (state != InputState.END) {
                moveSet.add(signatureMap.get(key));
            } else {
                moveSet.remove(signatureMap.get(key));
            }
        }
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void raiseSpeed(double delta) {
        speed += delta;
    }

    public void clearMoves() {
        moveSet.clear();
    }

    public Point2D getMoveVector() {
        Point2D vector = Point2D.ZERO;
        for (WalkDirection direction : moveSet) {
            vector = vector.add(direction.getDirectionVector());
        }
        return vector.multiply(speed);
    }

    public boolean isMoveUp() {
        return moveSet.contains(WalkDirection.UP);
    }

    public void setMoveUp(boolean move) {
        if (move) {
            moveSet.add(WalkDirection.UP);
        } else {
            moveSet.remove(WalkDirection.UP);
        }
    }

    public boolean isMoveDown() {
        return moveSet.contains(WalkDirection.DOWN);
    }

    public void setMoveDown(boolean move) {
        if (move) {
            moveSet.add(WalkDirection.DOWN);
        } else {
            moveSet.remove(WalkDirection.DOWN);
        }
    }

    public boolean isMoveLeft() {
        return moveSet.contains(WalkDirection.LEFT);
    }

    public void setMoveLeft(boolean move) {
        if (move) {
            moveSet.add(WalkDirection.LEFT);
        } else {
            moveSet.remove(WalkDirection.LEFT);
        }
    }

    public boolean isMoveRight() {
        return moveSet.contains(WalkDirection.RIGHT);
    }

    public void setMoveRight(boolean move) {
        if (move) {
            moveSet.add(WalkDirection.RIGHT);
        } else {
            moveSet.remove(WalkDirection.RIGHT);
        }
    }

    public void setMove(WalkDirection... directions) {
        moveSet.clear();
        moveSet.addAll(Arrays.asList(directions));
    }
}
