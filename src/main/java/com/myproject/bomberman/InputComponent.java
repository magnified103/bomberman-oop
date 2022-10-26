package com.myproject.bomberman;

public abstract class InputComponent extends Component {

    public abstract void processInput(String key, InputState state);
}
