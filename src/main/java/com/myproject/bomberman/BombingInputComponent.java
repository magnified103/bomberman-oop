package com.myproject.bomberman;

public class BombingInputComponent extends Component {

    private int counter;
    private int remaining;
    private String signature;

    public BombingInputComponent(String signature, int limit) {
        this.signature = signature;
        this.counter = 0;
        this.remaining = limit;
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

    public void count(boolean state) {
        if (state) {
            counter++;
        }
    }

    public String getSignature() {
        return signature;
    }
}
