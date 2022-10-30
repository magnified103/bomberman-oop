package com.myproject.bomberman.components;

import com.myproject.bomberman.core.Component;

public abstract class InputComponent extends Component {

    public abstract void processInput(String key, InputState state);
}
