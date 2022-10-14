package com.myproject.bomberman;

public class PlantBombInputComponent extends Component {

    private int bombLimit = 1;
    private boolean plantBomb;
    private String signaturePlant;
    public PlantBombInputComponent(String sigPlant) {
        this.signaturePlant = sigPlant;
        this.plantBomb = false;
    }

    public boolean isPlantBomb() {
        return plantBomb;
    }

    public void setPlantBomb(boolean plantBomb) {
        this.plantBomb = plantBomb;
    }

    public String getSignaturePlant() {
        return signaturePlant;
    }

    public void setSignaturePlant(String signaturePlant) {
        this.signaturePlant = signaturePlant;
    }
}
