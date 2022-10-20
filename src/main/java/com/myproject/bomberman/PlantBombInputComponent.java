package com.myproject.bomberman;

public class PlantBombInputComponent extends Component {

    private int bombCount;
    private int bombCheck;
    //private boolean plantBomb;
    private String signaturePlant;
    public PlantBombInputComponent(String sigPlant) {
        this.signaturePlant = sigPlant;
        this.bombCheck = 0;
        this.bombCount = 0;
    }

    public int getBombCheck() {
        return bombCheck;
    }

    public void setBombCheck(int bombCheck) {
        this.bombCheck = bombCheck;
    }

    public void addBombCheck(boolean bool) {
        if (bool) bombCheck++;
    }

    public String getSignaturePlant() {
        return signaturePlant;
    }

    public void setSignaturePlant(String signaturePlant) {
        this.signaturePlant = signaturePlant;
    }

    public int getBombCount() {
        return bombCount;
    }

    public void minusBombCount() {
        this.bombCount--;
    }

    public void addBombCount() {
        this.bombCount++;
    }
}
