package com.myproject.bomberman.components;

import com.myproject.bomberman.core.Component;

public class CollidableComponent extends Component {

    Collidable type;

    public CollidableComponent(Collidable type) {
        this.type = type;
    }

    public Collidable getType() {
        return type;
    }
}
