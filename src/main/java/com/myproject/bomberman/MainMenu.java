package com.myproject.bomberman;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.FXGLForKtKt;
import com.almasb.fxgl.input.view.KeyView;
import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class MainMenu extends FXGLMenu {
    public MainMenu() {
        super(MenuType.MAIN_MENU);
        ImageView background = new ImageView();
        ImageView decor1 = new ImageView();
        ImageView decor2 = new ImageView();
        background.setImage(new Image("assets/textures/menuBackground.png",1200,600,false,false));
        background.setOpacity(1);

        decor1.setImage(new Image("assets/textures/decorMenu.png",100,150,false,false));
        decor2.setImage(new Image("assets/textures/decorMenu2.png",100,150,false,false));

        decor1.setTranslateX(550);
        decor1.setTranslateY(420);
        decor2.setTranslateX(120);
        decor2.setTranslateY(200);

        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.BLACK);
        dropShadow.setHeight(8);
        dropShadow.setWidth(8);
        dropShadow.setOffsetX(8);
        dropShadow.setOffsetY(10);
        dropShadow.setSpread(10);

        ImageView title = new ImageView();
        title.setImage(new Image("assets/textures/menuTitle.png",600,300,false,false));
        title.setTranslateX(getAppWidth() / 2.0 - title.getImage().getWidth() / 2.0);
        title.setTranslateY(getAppHeight() / 2.0 - title.getImage().getHeight() + 10);
//            var title = getUIFactoryService().newText(getSettings().getTitle(), Color.rgb(248, 185, 54), 130);

//            centerTextBind(title, getAppWidth() / 2.0, 300);

        MenuButton first = new MenuButton("NEW GAME", 40, () -> {
            newGame();
            FXGL.getAudioPlayer().stopAllMusic();
            FXGL.loopBGM("bgm.mp3");
        });
        first.setEffect(dropShadow);

        MenuButton second = new MenuButton("CONTROL", 40, () -> instruct());
        second.setEffect(dropShadow);

        MenuButton third = new MenuButton("EXIT", 40, () -> fireExit());
        third.setEffect(dropShadow);

        var menuBox = new VBox(first, second, third);
        menuBox.setAlignment(Pos.CENTER);

        menuBox.setTranslateX(getAppWidth() / 2.0 - 175);
        menuBox.setTranslateY(getAppHeight() / 2.0 + 40);
        menuBox.setSpacing(20);
        getContentRoot().getChildren().addAll(background, title, menuBox, decor1, decor2);
    }

    private void instruct() {
        GridPane pane = new GridPane();

        pane.addRow(0, FXGLForKtKt.getUIFactoryService().newText(" Movement      "),
                new HBox(new KeyView(KeyCode.W), new KeyView(KeyCode.S), new KeyView(KeyCode.A), new KeyView(KeyCode.D)));
        pane.addRow(1, FXGLForKtKt.getUIFactoryService().newText(" Placed Bomb      "),
                new KeyView(KeyCode.SPACE));

        FXGLForKtKt.getDialogService().showBox("How to Play", pane, FXGLForKtKt.getUIFactoryService().newButton("OK"));
    }
    public void newGame() {
        fireNewGame();
    }
}
