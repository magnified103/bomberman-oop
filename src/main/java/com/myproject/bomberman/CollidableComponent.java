package com.myproject.bomberman;

public class CollidableComponent extends Component {

    Collidable type;

    public CollidableComponent(Collidable type) {
        this.type = type;
    }

    public Collidable getType() {
        return type;
    }
}
