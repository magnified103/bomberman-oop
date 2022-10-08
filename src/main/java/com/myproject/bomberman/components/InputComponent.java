package com.myproject.bomberman.components;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.input.Trigger;

/**
 * A component that transforming input events into entity's actions.
 */
public abstract class InputComponent extends Component {

    /**
     * Converts input triggers to entity's behaviors.
     * @param trigger Input trigger.
     * @param inputState State of the event (begin, hold or end).
     */
    public abstract void processInput(Trigger trigger, InputState inputState);
}
