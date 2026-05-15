package org.example;

import static com.raylib.Colors.*;
import static com.raylib.Raylib.*;
import static org.example.Main.debagMode;

public class Menu {

    public static Rectangle btStart = new Rectangle();
    public static Rectangle btExit = new Rectangle();
    public static Button[] buttons = new Button[2];

    public static void menu(){
        btStart.x(Main.screenWidth / 2 - btStart.width() / 2).y(Main.screenHeight / 2 - btStart.height() / 2).width(60).height(60);
        buttons[0] = new Button(btStart, "Start");
        btExit.x(Main.screenWidth / 6 - btExit.width() / 2).y(Main.screenHeight / 1.1f - btExit.height() / 2).width(60).height(60);
        buttons[1] = new Button(btExit, "Exit");

        if(IsMouseButtonPressed(MOUSE_BUTTON_LEFT)){
            for(Button buttonStart : buttons) {
                if (Button.isPressed(buttonStart.buttonRect)) {
                    if(buttonStart.nameButton == "Start"){Scene.numScene = 1;}
                    if(buttonStart.nameButton == "Exit"){Main.winShouldClose = true;}
                }
            }
        }

        BeginDrawing();
        ClearBackground(RAYWHITE);
        for(Button button : buttons) {
            DrawRectangle((int) button.buttonRect.x(), (int) button.buttonRect.y(), (int) button.buttonRect.width(), (int) button.buttonRect.height(), VIOLET);
        }
        if (debagMode == 1){
            Debugging.stats();
        }
        if(IsKeyPressed(KEY_F3)){
            if(debagMode == 0){
                debagMode = 1;}else{debagMode = 0;}
        }
        EndDrawing();
    }
}
