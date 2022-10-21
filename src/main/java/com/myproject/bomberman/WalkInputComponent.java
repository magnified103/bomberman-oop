package com.myproject.bomberman;

public class WalkInputComponent extends Component {

    private boolean moveUp;
    private boolean moveDown;
    private boolean moveLeft;
    private boolean moveRight;
    private String signatureUp;
    private String signatureDown;
    private String signatureLeft;
    private String signatureRight;
    private int SPEED = 100;
    private int FLAME_SIZE = 1;
    private int[][] GRID = new int[100][100];

    private double FLAME_DURATION = 0.7;

    private int BOMB_CAPACITY = 1;

    public int getSPEED() {
        return SPEED;
    }

    public void setSPEED(int SPEED) {
        this.SPEED += SPEED;
    }

    public int getFLAME_SIZE() {
        return FLAME_SIZE;
    }

    public void addFLAME_SIZE() {
        this.FLAME_SIZE++;
    }

    public int getGRID(int x, int y) {
        return GRID[x][y];
    }

    public void setGRID(int x, int y, int value) {
        GRID[x][y] = value;
    }

    public double getFLAME_DURATION() {
        return FLAME_DURATION;
    }

    public void setFLAME_DURATION(double FLAME_DURATION) {
        this.FLAME_DURATION = FLAME_DURATION;
    }

    public int getBOMB_CAPACITY() {
        return BOMB_CAPACITY;
    }

    public void addBOMB_CAPACITY() {
        this.BOMB_CAPACITY++;
    }
    public WalkInputComponent(String sigUp, String sigDown, String sigLeft, String sigRight) {
        this.signatureUp = sigUp;
        this.signatureDown = sigDown;
        this.signatureLeft = sigLeft;
        this.signatureRight = sigRight;
        this.moveUp = this.moveDown = this.moveLeft = this.moveRight = false;
    }

    public boolean isMoveUp() {
        return moveUp;
    }

    public void setMoveUp(boolean moveUp) {
        this.moveUp = moveUp;
//        java.lang.System.err.printf("Move up = %b!\n", moveUp);
    }

    public boolean isMoveDown() {
        return moveDown;
    }

    public void setMoveDown(boolean moveDown) {
        this.moveDown = moveDown;
//        java.lang.System.err.printf("Move down = %b!\n", moveDown);
    }

    public boolean isMoveLeft() {
        return moveLeft;
    }

    public void setMoveLeft(boolean moveLeft) {
        this.moveLeft = moveLeft;
//        java.lang.System.err.printf("Move left = %b!\n", moveLeft);
    }

    public boolean isMoveRight() {
        return moveRight;
    }

    public void setMoveRight(boolean moveRight) {
        this.moveRight = moveRight;
//        java.lang.System.err.printf("Move right = %b!\n", moveRight);
    }

    public String getSignatureUp() {
        return signatureUp;
    }

    public String getSignatureDown() {
        return signatureDown;
    }

    public String getSignatureLeft() {
        return signatureLeft;
    }

    public String getSignatureRight() {
        return signatureRight;
    }
}
