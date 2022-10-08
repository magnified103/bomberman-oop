package com.myproject.bomberman.components;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.input.Trigger;

public class BomberInputComponent extends InputComponent {

    String signatureUp;
    String signatureDown;
    String signatureLeft;
    String signatureRight;

    public BomberInputComponent(String up, String down, String left, String right) {
        this.signatureUp = up;
        this.signatureDown = down;
        this.signatureLeft = left;
        this.signatureRight = right;
    }

    public void processInput(Trigger trigger, InputState inputState) {
        Entity entity = getEntity();
        if (entity == null || inputState == InputState.END) {
            return;
        }
        String triggerName = trigger.getName();
        if (triggerName.equals(signatureUp)) {
            entity.translateY(-5);
        }
        if (triggerName.equals(signatureDown)) {
            entity.translateY(5);
        }
        if (triggerName.equals(signatureLeft)) {
            entity.translateX(-5);
        }
        if (triggerName.equals(signatureRight)) {
            entity.translateX(5);
        }
    }
}
