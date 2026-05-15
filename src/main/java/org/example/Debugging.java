package org.example;

import static com.raylib.Colors.*;
import static com.raylib.Colors.SKYBLUE;
import static com.raylib.Raylib.*;

public class Debugging {

    public static void stats(){
        DrawFPS(20, 20);
        DrawText("zoom: " + Game.camera.zoom(), 20, 40, 20, RED);
        DrawText("onFloor " + Game.player.onFloor, 20, 60, 20, RED);
        DrawText("CollisionWithUp " + Game.player.CollisionWithUp, 20, 80, 20, RED);DrawText("CollisionWithR " + Game.player.CollisionWithRight, 20, 100, 20, RED);
        DrawText("CollisionWithL " + Game.player.CollisionWithLeft, 20, 120, 20, RED);
        DrawText("Speed " + ((Game.velocity.x() + Game.velocity.y()) / 2), 20, 140, 20, GREEN);

        if(IsKeyDown(KEY_SPACE)){DrawText("Up", 20, 160, 20, SKYBLUE);}
        if(IsKeyDown(KEY_A)){DrawText("A", 45, 160, 20, SKYBLUE);}
        if(IsKeyDown(KEY_D)){DrawText("D", 65, 160, 20, SKYBLUE);}
        if(IsKeyDown(KEY_S)){DrawText("S", 85, 160, 20, SKYBLUE);}
    }

}
