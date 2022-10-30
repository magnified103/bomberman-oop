package com.myproject.bomberman.systems;

import com.almasb.fxgl.input.Trigger;
import com.myproject.bomberman.components.InputState;
import com.myproject.bomberman.components.InputComponent;
import com.myproject.bomberman.core.System;

public class InputSystem extends System {

    public void updateInput(Trigger trigger, InputState inputState) {
        String triggerName = trigger.getName();
        getParentWorld().getComponentsBySuperType(InputComponent.class).forEach((input) -> {
            input.processInput(triggerName, inputState);
        });
    }
}
