package com.myproject.bomberman;

import com.myproject.bomberman.ecs.Component;

public class InputComponent extends Component {

    boolean moveUp;
    boolean moveDown;
    boolean moveLeft;
    boolean moveRight;
    String signatureUp;
    String signatureDown;
    String signatureLeft;
    String signatureRight;

    public InputComponent(String sigUp, String sigDown, String sigLeft, String sigRight) {
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
//        System.err.printf("Move up = %b!\n", moveUp);
    }

    public boolean isMoveDown() {
        return moveDown;
    }

    public void setMoveDown(boolean moveDown) {
        this.moveDown = moveDown;
//        System.err.printf("Move down = %b!\n", moveDown);
    }

    public boolean isMoveLeft() {
        return moveLeft;
    }

    public void setMoveLeft(boolean moveLeft) {
        this.moveLeft = moveLeft;
//        System.err.printf("Move left = %b!\n", moveLeft);
    }

    public boolean isMoveRight() {
        return moveRight;
    }

    public void setMoveRight(boolean moveRight) {
        this.moveRight = moveRight;
//        System.err.printf("Move right = %b!\n", moveRight);
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
