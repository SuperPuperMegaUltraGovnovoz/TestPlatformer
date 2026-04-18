package org.example;

import static com.raylib.Raylib.*;

public class Main {

    static float screenWidth = 1280;
    static float screenHeight = 720;

    public static boolean winShildClose = false;

    public static void main(String[] args) {
        //создаём окно
        SetConfigFlags(FLAG_WINDOW_RESIZABLE);
        InitWindow((int) screenWidth, (int) screenHeight, "Demo");

        int[] sc = new int[2]; sc[0] = 0; sc[1] = 1;

        Scene.numScene = 0;
        Scene scenes = new Scene(sc);

        Game.init();

        while (!winShildClose) {
            if(WindowShouldClose()){
                winShildClose = true;
            }
            screenHeight = GetScreenHeight();
            screenWidth = GetScreenWidth();
            Scene.errScene();
            if(Scene.numScene == 0){
                Menu.menu();
            }
            if(Scene.numScene == 1) {
                TickSystem.Tick();
                Game.render();
            }
            if(IsKeyPressed(KEY_UP)){
                Scene.numScene += 1;
            }
            if(IsKeyPressed(KEY_DOWN)){
                Scene.numScene -= 1;
            }
        }
        CloseWindow();
    }
}