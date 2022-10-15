package com.myproject.bomberman;

public class PlantBombInputComponent extends Component {

    private int bombLimit = 1;
    private int bombCheck;
    //private boolean plantBomb;
    private String signaturePlant;
    public PlantBombInputComponent(String sigPlant) {
        this.signaturePlant = sigPlant;
        this.bombCheck = 0;
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
}
