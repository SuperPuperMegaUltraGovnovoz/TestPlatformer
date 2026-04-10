package org.example;

import static com.raylib.Raylib.*;

public class TickSystem {
    public static final int TPS = 45;
    public static final float delta = 1f/TPS;

    private static float timer;

    public static void Tick(){
        timer += GetFrameTime();

        if(timer >= delta){
            Game.update();
            timer -= delta;
        }
    }
}
