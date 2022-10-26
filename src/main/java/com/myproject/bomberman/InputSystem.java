package com.myproject.bomberman;

import com.almasb.fxgl.input.Trigger;

public class InputSystem extends System {

    public void updateInput(Trigger trigger, InputState inputState) {
        String triggerName = trigger.getName();
        getParentWorld().getComponentsBySuperType(InputComponent.class).forEach((input) -> {
            input.processInput(triggerName, inputState);
        });
    }
}
