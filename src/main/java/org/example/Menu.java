package org.example;

import static com.raylib.Colors.*;
import static com.raylib.Raylib.*;

public class Menu {

    public static void menu(){
        BeginDrawing();
        ClearBackground(RAYWHITE);
        DrawRectangle((int)Main.screenWidth / 2, (int)Main.screenHeight / 2, 60, 60, VIOLET);
        EndDrawing();
    }

}
