package com.myproject.bomberman;

public class BombDataComponent extends TimerComponent {

    private int blastRadius;
    private Entity bomber;

    public BombDataComponent(double time, int blastRadius, Entity bomber) {
        super(time);
        this.blastRadius = blastRadius;
        this.bomber = bomber;
    }

    public int getBlastRadius() {
        return blastRadius;
    }

    public Entity getBomber() {
        return bomber;
    }
}
