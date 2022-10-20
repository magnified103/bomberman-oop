package com.myproject.bomberman;

public class CollidableComponent extends Component {

    CollidableType type;

    public CollidableComponent(CollidableType type) {
        this.type = type;
    }

    public CollidableType getType() {
        return type;
    }
}
