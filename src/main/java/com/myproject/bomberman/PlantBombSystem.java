package com.myproject.bomberman;

import java.util.List;

import com.almasb.fxgl.dsl.FXGLForKtKt;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import javafx.util.Duration;

public class PlantBombSystem extends System {
    @Override
    public void update(double tpf) {
        List<Entity> entityList = getParentWorld().getEntitiesByType(PlantBombInputComponent.class);
        for (Entity entity : entityList) {
            PlantBombInputComponent input = entity.getComponentByType(PlantBombInputComponent.class);
            int bombCapacity =  entity.getComponentByType(FxglTransformComponent.class).getBOMB_CAPACITY();
            if (input.getBombCheck() == 1) {
                Entity bomb = getParentWorld().spawnEntity();
                input.addBombCount();
                FxglTransformComponent transformComponent = new FxglTransformComponent();
                FxglViewComponent viewComponent = new FxglViewComponent();
                PlantBombAnimationComponent plantBombAnimationComponent = new PlantBombAnimationComponent("Bomb.png",entity.getComponentByType(FxglTransformComponent.class).getFLAME_DURATION());
                FxglBoundingBoxComponent bboxComponent = new FxglBoundingBoxComponent();

                bomb.addAndAttach(plantBombAnimationComponent);
                bomb.addAndAttach(transformComponent);
                bomb.addAndAttach(viewComponent);
                bomb.addAndAttach(bboxComponent);

                //Get position of player
                FxglBoundingBoxComponent bbox = entity.getComponentByType(FxglBoundingBoxComponent.class);

                bboxComponent.getFxglComponent().addHitBox(new HitBox(BoundingShape.box(32,32)));
                transformComponent.getFxglComponent().setPosition((int)(bbox.getFxglComponent().getCenterWorld().getX() / 32) * 32
                        ,(int)(bbox.getFxglComponent().getCenterWorld().getY() / 32 ) * 32);
                viewComponent.getFxglComponent().addChild(plantBombAnimationComponent.getMainFrame());
                viewComponent.getFxglComponent().setZIndex(-1);

                if (input.getBombCount() == bombCapacity) input.setBombCheck(2); //set bomb check de bomb chi spawn 1 lan moi khi an
                else input.setBombCheck(0);
                FXGLForKtKt.getGameTimer().runOnceAfter(()->{
                    plantBombAnimationComponent.setActive(true);
                    plantBombAnimationComponent.setBombExplosion();
                    CollidableComponent CollidableComponent = new CollidableComponent(Collidable.FLAME);
                    bomb.addAndAttach(CollidableComponent);
                    // set grid cua bomb thanh flame
                    entity.getComponentByType(FxglTransformComponent.class).setGRID((int)transformComponent.getFxglComponent().getPosition().getX()/32,
                            (int)transformComponent.getFxglComponent().getPosition().getY()/32, 2);
                    //sau 2s moi co the dat
                    FXGLForKtKt.getGameTimer().runOnceAfter(()->{
                        entity.getComponentByType(FxglTransformComponent.class).setGRID((int)transformComponent.getFxglComponent().getPosition().getX()/32,
                                (int)transformComponent.getFxglComponent().getPosition().getY()/32, 0);
                        getParentWorld().removeEntity(bomb);
                        input.minusBombCount();
                        input.setBombCheck(0);
                    }, Duration.seconds(entity.getComponentByType(FxglTransformComponent.class).getFLAME_DURATION()));
                }, Duration.seconds(2));
            }
        }
    }
}
