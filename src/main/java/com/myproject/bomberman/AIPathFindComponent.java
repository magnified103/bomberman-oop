package com.myproject.bomberman;

public class AIPathFindComponent extends AIComponent {

    private AIRandomComponent fallback;
    private int viewRadius;

    public AIPathFindComponent(int viewRadius) {
        fallback = new AIRandomComponent(0.01);
        this.viewRadius = viewRadius;
    }

    public AIRandomComponent getFallback() {
        return fallback;
    }

    public int getViewRadius() {
        return viewRadius;
    }
}
