package com.myproject.bomberman;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.Trigger;
import com.almasb.fxgl.input.TriggerListener;

import java.util.Map;

public class BombermanApp extends GameApplication {

    World world;

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(800);
        settings.setHeight(600);
        settings.setTitle("Bomberman");
        settings.setDeveloperMenuEnabled(true);
        settings.setMainMenuEnabled(true);
        settings.setFontUI("HachicroUndertaleBattleFontRegular-L3zlg.ttf");
        settings.setFontGame("MinecraftBold-nMK1.ttf");
        settings.setFontMono("PixgamerRegular-PKxO2.ttf");

        settings.setAppIcon("icon.png");
        settings.setSceneFactory(new SceneFactory() {
            @Override
            public FXGLMenu newMainMenu() {
                FXGL.loopBGM("menuBgm.mp3");
                return new MainMenu();
            }
            @Override
            public FXGLMenu newGameMenu() {
                return new IngameMenu();
            }
        });
        settings.setTicksPerSecond(60);
    }

    @Override
    protected void initGame() {
        world = new World();
        world.addSystem(new WorldUtility());
        world.getSystem(WorldUtility.class).addLevel("./Level1.txt");
        world.getSystem(WorldUtility.class).addLevel("./Level2.txt");
        world.getSystem(WorldUtility.class).initialize();
    }
    @Override
    protected void initInput() {
        Input input = FXGL.getInput();

        input.addTriggerListener(new TriggerListener() {
            @Override
            protected void onAction(Trigger trigger) {
                world.getSystem(InputSystem.class).updateInput(trigger, InputState.HOLD);
            }

            @Override
            protected void onActionBegin(Trigger trigger) {
                world.getSystem(InputSystem.class).updateInput(trigger, InputState.BEGIN);
            }

            @Override
            protected void onActionEnd(Trigger trigger) {
                world.getSystem(InputSystem.class).updateInput(trigger, InputState.END);
            }
        });
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("Time",0);
        vars.put("Score", 0);
    }

    @Override
    protected void onUpdate(double tpf) {
        super.onUpdate(tpf);
        world.update(tpf);
    }


    public static void main(String[] args) {
        launch(args);
    }

}