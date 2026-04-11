package org.example;

import static com.raylib.Colors.*;
import static com.raylib.Colors.GREEN;
import static com.raylib.Colors.RED;
import static com.raylib.Raylib.*;

public class Game {

    static Rectangle world = new Rectangle().x(300).y(300).width(40).height(40);
    static Object[] object = new Object[3];
    static Camera2D camera = new Camera2D();
    static Player player = new Player((int) camera.target().x() + 50, (int) camera.target().y() - 400 * 2, 30 * 2, 40 * 2);
    static int tgFPS = 60;
    static float velosityX = 0f;
    static float velosityX1 = 0f;
    static float velosityY = 0f;
    static float velosityY1 = 0f;
    static final float multVelosity = 500;

    public static void init(){
        object[0] = new Object((int)world.x() + 330, (int)world.y() - 50 * 2, 40 * 3 * 2, 20 * 1 * 2);
        object[1] = new Object((int)world.x(), (int)world.y() + 50, 40 * 3 * 2, 40 * 2);
        object[2] = new Object((int)world.x() + 125 * 2, (int)world.y() - 50, 40 * 3 * 2, 20 * 1 * 2);

        camera.target(new Vector2().x(world.x() + 60).y(world.y() - 60));
        camera.offset(new Vector2().x(Main.screenWidth1/2).y(Main.screenHeight1/2));
        camera.rotation(0.0f);
        camera.zoom(0.5f);
    }

    public static void update() {
        //обнуление колизий
        Collision.disCollision(player);
        //фпс
        SetTargetFPS(tgFPS);
        if (IsKeyDown(KEY_ONE)) {
            tgFPS = -1;
        }
        if (IsKeyDown(KEY_TWO)) {
            tgFPS = 60;
        }
        if (IsKeyDown(KEY_THREE)) {
            tgFPS = 30;
        }

        //столкновение
        for (int i = 0; i < object.length; i++) {
            Collision.collision(player, object[i]);
        }

        //гравитация
        if (!player.onFloor) {
            velosityY = velosityY - 0.001f * TickSystem.delta * multVelosity;
            velosityY = Math.max(velosityY, -0.4f);
            camera.target().y(camera.target().y() + (-velosityY) * TickSystem.delta * multVelosity);
        }
        //взаимодействие с колизией
        if (player.CollisionWithUp) {
            if (velosityY >= 0) {
                velosityY = 0f;
            }
        }

        if (player.onFloor) {
            if (IsKeyDown(KEY_SPACE)) {
                velosityY = 0.5f;
                camera.target().y(camera.target().y() + (-velosityY) * TickSystem.delta * multVelosity);
            }
        }

        if (IsKeyDown(KEY_S)) {
            if (player.onFloor) {
                velosityY1 = 0;
            } else {
                velosityY1 = 0.2f;
            }
            camera.target().y(camera.target().y() + velosityY1 * TickSystem.delta * multVelosity);
        }

        if (IsKeyDown(KEY_A)) {
            if (player.CollisionWithLeft) {
                velosityX = 0;
            } else {
                velosityX = 0.2f;
            }
            camera.target().x(camera.target().x() + (-velosityX) * TickSystem.delta * multVelosity);
        }


        if (IsKeyDown(KEY_D)) {
            if (player.CollisionWithRight) {
                velosityX1 = 0;
            } else {
                velosityX1 = 0.2f;
            }
            camera.target().x(camera.target().x() + velosityX1 * TickSystem.delta * multVelosity);
        }
    }

    public static void render(){

        //применение координат и изменение зума
        player.x = (int) camera.target().x();
        player.y = (int) camera.target().y();
        camera.zoom((camera.zoom()) + (GetMouseWheelMove() * 0.03f));
        camera.zoom(Math.max(camera.zoom(), 0.02f));
        if(IsKeyDown(KEY_R)){Animation.y = 0; Animation.x = 0; Animation.endAnim = false;}
        if(!Animation.endAnim){Animation.animation();}

        BeginDrawing();
        ClearBackground(RAYWHITE);
        BeginMode2D(camera);
        //отрисовка
        for (int i = 0; i < object.length; i++) {
            DrawRectangle(object[i].x, object[i].y, object[i].width, object[i].height, VIOLET);
            DrawRectangleLines(object[i].x, object[i].y, object[i].width, object[i].height, player.onFloor || player.CollisionWithUp ? GREEN : RED);
        }

        DrawEllipse(player.x, player.y, player.width, player.height, RED);

        EndMode2D();
        //разного рода статистики и показатели
        DrawFPS(20, 20);
        DrawText("zoom: " + camera.zoom(), 20, 40, 20, RED);
        DrawText("onFloor " + player.onFloor, 20, 60, 20, RED);
        DrawText("CollisionWithUp " + player.CollisionWithUp, 20, 80, 20, RED);DrawText("CollisionWithR " + player.CollisionWithRight, 20, 100, 20, RED);
        DrawText("CollisionWithL " + player.CollisionWithLeft, 20, 120, 20, RED);
        DrawText("Speed " + ((velosityY + velosityY1 + velosityX1 + velosityX) / 4), 20, 140, 20, GREEN);
        EndDrawing();
    }
}
