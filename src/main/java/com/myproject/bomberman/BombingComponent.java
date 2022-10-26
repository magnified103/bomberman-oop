package com.myproject.bomberman;

public class BombingComponent extends InputComponent {

    private int counter;
    private int remaining;
    private String signature;
    private int blastRadius;

    public BombingComponent(int limit, int blastRadius) {
        this.counter = 0;
        this.remaining = limit;
        this.blastRadius = blastRadius;
    }

    public BombingComponent(String signature, int limit, int blastRadius) {
        this(limit, blastRadius);
        this.signature = signature;
    }

    @Override
    public void processInput(String key, InputState state) {
        if (key.equals(signature)) {
            count(state == InputState.BEGIN);
        }
    }

    public int getBlastRadius() {
        return blastRadius;
    }

    public void setBlastRadius(int blastRadius) {
        this.blastRadius = blastRadius;
    }

    public void raiseBlastRadius(int delta) {
        blastRadius += delta;
    }

    public boolean canThrowBomb() {
        return counter > 0 && remaining > 0;
    }

    public void raiseLimitBy(int delta) {
        remaining += delta;
    }

    public void resetInput() {
        counter = 0;
    }

    public void doThrowBomb() {
        if (remaining <= 0) {
            throw new RuntimeException("Attempted to bomb inappropriately.");
        }
        remaining--;
    }

    private void count(boolean state) {
        if (state) {
            counter++;
        }
    }
}
