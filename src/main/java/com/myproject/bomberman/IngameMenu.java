package com.myproject.bomberman;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGLForKtKt;
import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class IngameMenu extends FXGLMenu {
    public IngameMenu() {
        super(MenuType.GAME_MENU);
        Shape shape = new Rectangle(10, 10, Color.GRAY);
        shape.setOpacity(0.5);

        ImageView background = new ImageView();
        background.setImage(new Image("assets/textures/ingameBackground.png",getAppWidth(),getAppHeight(),false,false));
        background.setOpacity(0.3);

        // Drop Shadow of Text in menu
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.BLACK);
        dropShadow.setHeight(7);
        dropShadow.setWidth(7);
        dropShadow.setOffsetX(6);
        dropShadow.setOffsetY(8);
        dropShadow.setSpread(8);

        var title = FXGLForKtKt.getUIFactoryService().newText("PAUSED",Color.rgb(204,150,34), 60);
        title.setTranslateX(getAppWidth() / 2.0 - 200);
        title.setTranslateY(getAppHeight() / 2.0 - 100);
        title.setEffect(dropShadow);

        MenuButton first = new MenuButton("RETURN", 30, () -> fireResume());
        first.setEffect(dropShadow);

        MenuButton second = new MenuButton("MAIN MENU", 30, () -> fireExitToMainMenu());
        second.setEffect(dropShadow);

        MenuButton third = new MenuButton("EXIT", 30, () -> fireExit());
        third.setEffect(dropShadow);

        var menuBox = new VBox(first, second, third);

        menuBox.setAlignment(Pos.CENTER);
        menuBox.setTranslateX(getAppWidth() / 2.0 - 140);
        menuBox.setTranslateY(getAppHeight() / 2.0);
        menuBox.setSpacing(20);

        getContentRoot().getChildren().addAll(shape, background, title, menuBox);
    }
}
