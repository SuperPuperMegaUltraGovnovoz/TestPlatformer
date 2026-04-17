package org.example;

import static com.raylib.Raylib.*;

public class Main {

    static float screenWidth1 = 1280;
    static float screenHeight1 = 720;

    public static void main(String[] args) {
        //создаём окно
        SetConfigFlags(FLAG_WINDOW_RESIZABLE);
        InitWindow((int)screenWidth1, (int)screenHeight1, "Demo");

        int[] sc = new int[2]; sc[0] = 0; sc[1] = 1;

        Scene scenes = new Scene(sc);
        Scene scene = new Scene(0);

        Game.init();

        while (!WindowShouldClose()) {
            Scene.errScene();
            TickSystem.Tick();
            Game.render();
        }
        CloseWindow();
    }
}