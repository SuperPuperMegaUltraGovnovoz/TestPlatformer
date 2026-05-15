package org.example;

import java.io.IOException;

import static com.raylib.Raylib.*;

public class Main {

    static float screenWidth = 1280;
    static float screenHeight = 720;

    public static boolean winShouldClose = false;

    static int debagMode = 0;

    public static void main(String[] args) {
        //создаём окно
        SetConfigFlags(FLAG_WINDOW_RESIZABLE);
        InitWindow((int) screenWidth, (int) screenHeight, "Demo");

        int[] sc = new int[2]; sc[1] = 1;

        Scene.scenes = sc;
        Scene.numScene = 0;

        try {
            ResourceManager.textureExtraction();
        }catch (IOException e){
            e.printStackTrace();
        }

        Game.init();

        while (!winShouldClose) {
            if(WindowShouldClose()){
                winShouldClose = true;
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
        }
        for(Texture texture : Game.walk.textures){
        UnloadTexture(texture);}
        CloseWindow();
    }
}