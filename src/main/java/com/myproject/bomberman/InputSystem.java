package com.myproject.bomberman;

import com.almasb.fxgl.input.Trigger;
import com.myproject.bomberman.ecs.System;

import java.util.List;

public class InputSystem extends System {

    public void updateInput(Trigger trigger, InputState inputState) {
        List<InputComponent> components = getParentWorld().getComponentsByType(InputComponent.class);
        String triggerName = trigger.getName();
        for (InputComponent component : components) {
            if (triggerName.equals(component.getSignatureUp())) {
                component.setMoveUp(inputState != InputState.END);
            }
            if (triggerName.equals(component.getSignatureDown())) {
                component.setMoveDown(inputState != InputState.END);
            }
            if (triggerName.equals(component.getSignatureLeft())) {
                component.setMoveLeft(inputState != InputState.END);
            }
            if (triggerName.equals(component.getSignatureRight())) {
                component.setMoveRight(inputState != InputState.END);
            }
        }
    }
}
