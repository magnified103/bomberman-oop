package com.myproject.bomberman;

public class BombingDataComponent extends Component {

    private int blastRadius;

    public BombingDataComponent(int blastRadius) {
        this.blastRadius = blastRadius;
    }

    public int getBlastRadius() {
        return blastRadius;
    }

    public void setBlastRadius(int blastRadius) {
        this.blastRadius = blastRadius;
    }
}
