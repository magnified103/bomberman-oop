package com.myproject.bomberman;

import com.almasb.fxgl.input.Trigger;

import java.util.List;

public class InputSystem extends System {

    public void updateInput(Trigger trigger, InputState inputState) {
        List<WalkInputComponent> components = getParentWorld().getComponentsByType(WalkInputComponent.class);
        List<PlantBombInputComponent> plantComponents = getParentWorld().getComponentsByType(PlantBombInputComponent.class);
        String triggerName = trigger.getName();
        for (WalkInputComponent component : components) {
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
        for (PlantBombInputComponent component : plantComponents) {
            if (triggerName.equals(component.getSignaturePlant())) {
//                component.setPlantBomb(inputState == InputState.HOLD);
                if (inputState == InputState.BEGIN) {
                    java.lang.System.out.println(1);
                    Entity bomb = getParentWorld().spawnEntity();
                    TransformComponent transformComponent = new TransformComponent();
                    ViewComponent viewComponent = new ViewComponent();
                    PlantBombAnimationComponent plantBombAnimationComponent = new PlantBombAnimationComponent("Bomb.png");

                    bomb.attachComponent(plantBombAnimationComponent);
                    bomb.attachComponent(transformComponent);
                    bomb.attachComponent(viewComponent);

                    transformComponent.getFxglComponent().setPosition(0,0);
                    viewComponent.getFxglComponent().addChild(plantBombAnimationComponent.getMainFrame());
                }
            }
        }
    }
}
