package com.myproject.bomberman;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.*;

public class BombermanFactory implements EntityFactory {

//    @Spawns("bomber")
//    public Entity newBomber(SpawnData data) {
//
//    }

//    @Spawns("bomb")
//    public Entity spawnBomb(SpawnData data) {
//
//    }

//    @Spawns("brick")
//    public Entity spawnBrick(SpawnData data) {
//
//    }

//    @Spawns("enemy")
//    public Entity newEnemy(SpawnData data) {
//
//    }

    @Spawns("grass")
    public Entity spawnGrass(SpawnData data) {
        return FXGL.entityBuilder(data)
                .view(FXGL.texture("Grass.png", 40, 40))
                .zIndex(-1)
                .build();
    }

//    @Spawns("portal")
//    public Entity spawnPortal(SpawnData data) {
//
//    }

//    @Spawns("wall")
//    public Entity spawnWall(SpawnData data) {
//
//    }
}
