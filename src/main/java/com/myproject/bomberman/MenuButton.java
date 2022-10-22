package com.myproject.bomberman;

import com.almasb.fxgl.dsl.FXGL;
import javafx.beans.binding.Bindings;
import javafx.scene.Parent;
import javafx.scene.paint.Color;

public class MenuButton extends Parent {
    MenuButton(String name, double fontSize, Runnable action) {
        var text = FXGL.getUIFactoryService().newText(name, Color.rgb(248,104,72), fontSize);


        text.setStrokeWidth(1);
        text.strokeProperty().bind(text.fillProperty());


        text.fillProperty().bind(
                Bindings.when(hoverProperty())
                        .then(Color.rgb(248,197,95))
                        .otherwise(Color.rgb(248,104,72))
        );
        setOnMouseClicked(e -> action.run());
        setPickOnBounds(true);
        getChildren().add(text);
    }
}
