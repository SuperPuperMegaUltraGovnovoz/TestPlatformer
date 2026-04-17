package org.example;

import java.util.ArrayList;

import static com.raylib.Colors.*;
import static com.raylib.Colors.GREEN;
import static com.raylib.Colors.RED;
import static com.raylib.Raylib.*;

public class Game {

    static Rectangle world = new Rectangle().x(300).y(300).width(40).height(40);
    static ArrayList<Object> object = new ArrayList<>(5);
    static Camera2D camera = new Camera2D();
    static Player player = new Player((int) camera.target().x() + 50, (int) camera.target().y() - 500 * 2, 30 * 2, 40 * 2);
    static int tgFPS = 60;
    static final float multVelosity = 500;
    static final Vector2 velosity = new Vector2().x(0).y(0);

    public static void init(){
        object.add(new Object((int)world.x() + 330, (int)world.y() - 100, 40 * 6, 20 * 2));
        object.add(new Object((int)world.x() - 500, (int)world.y() + 50, 2000, 40 * 2));
        object.add(new Object((int)world.x() + 125, (int)world.y() - 50, 40 * 6, 20 * 2));
        object.add(new Object((int)world.x() + 125 + 330, (int)world.y() - 50, 40 * 6, 20 * 2));
        object.add(new Object((int)world.x() + 330, (int)world.y() - 320, 40 * 6, 20 * 2));

        camera.target(new Vector2().x(world.x() + 60).y(world.y() - 60));
        camera.offset(new Vector2().x(Main.screenWidth /2).y(Main.screenHeight /2));
        camera.rotation(0.0f);
        camera.zoom(0.5f);
    }

    public static void update() {
        //обнуление коллизий
        Collision.disCollision(player);
//        фпс
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
        for (Object value : object) {
            Collision.collisionU(player, value);
            Collision.collisionD(player, value);
            Collision.collisionR(player, value);
            Collision.collisionL(player, value);
        }

        //гравитация
        if (!player.onFloor) {
            velosity.y(velosity.y() - 0.0015f * TickSystem.delta * multVelosity);
            velosity.y(Math.max(velosity.y(), -0.4f));
            camera.target().y(camera.target().y() - velosity.y() * TickSystem.delta * multVelosity);
        }
        //взаимодействие с коллизией

        if (velosity.y() >= 0 && player.CollisionWithUp) {
            velosity.y(0f);
        }



        if (IsKeyDown(KEY_SPACE) && player.onFloor) {
            velosity.y( 0.6f);
            camera.target().y(camera.target().y() - velosity.y() * TickSystem.delta * multVelosity);
        }


        if (IsKeyDown(KEY_S)) {
            if (player.onFloor) {
                velosity.y(0);
            } else {
                velosity.y(-0.4f);
            }
            camera.target().y(camera.target().y() - velosity.y() * TickSystem.delta * multVelosity);
        }

        if (IsKeyDown(KEY_A)) {
            if (player.CollisionWithLeft) {
                velosity.x(0);
            } else {
                velosity.x(0.6f);
            }
            camera.target().x(camera.target().x() - velosity.x() * TickSystem.delta * multVelosity);
        }


        if (IsKeyDown(KEY_D)) {
            if (player.CollisionWithRight) {
                velosity.x(0);
            } else {
                velosity.x(0.6f);
            }
            camera.target().x(camera.target().x() + velosity.x() * TickSystem.delta * multVelosity);
        }

        player.position.x(camera.target().x());
        player.position.y(camera.target().y());
    }

    public static void render(){

        //изменение зума
        camera.zoom((camera.zoom()) + (GetMouseWheelMove() * 0.03f));
        camera.zoom(Math.max(camera.zoom(), 0.02f));
        if(IsKeyDown(KEY_R)){Animation.y = 0; Animation.x = 0; Animation.endAnim = false;}
        if(!Animation.endAnim){Animation.animation();}

        BeginDrawing();
        ClearBackground(RAYWHITE);
        BeginMode2D(camera);
        //отрисовка
        for (Object object1 : object) {
            DrawRectangle((int) object1.position.x(), (int) object1.position.y(), (int) object1.size.x(), (int) object1.size.y(), VIOLET);
            DrawRectangleLines((int) object1.position.x(), (int) object1.position.y(), (int) object1.size.x(), (int) object1.size.y(), player.onFloor || player.CollisionWithUp ? GREEN : RED);
        }

        DrawEllipse((int)player.position.x(), (int)player.position.y(), player.size.x(), player.size.y(), RED);

        EndMode2D();
        //разного рода статистики и показатели
        DrawFPS(20, 20);
        DrawText("zoom: " + camera.zoom(), 20, 40, 20, RED);
        DrawText("onFloor " + player.onFloor, 20, 60, 20, RED);
        DrawText("CollisionWithUp " + player.CollisionWithUp, 20, 80, 20, RED);DrawText("CollisionWithR " + player.CollisionWithRight, 20, 100, 20, RED);
        DrawText("CollisionWithL " + player.CollisionWithLeft, 20, 120, 20, RED);
        DrawText("Speed " + ((velosity.x() + velosity.y()) / 2), 20, 140, 20, GREEN);

        if(IsKeyDown(KEY_SPACE)){DrawText("Up", 20, 160, 20, SKYBLUE);}
        if(IsKeyDown(KEY_A)){DrawText("A", 40, 160, 20, SKYBLUE);}
        if(IsKeyDown(KEY_D)){DrawText("D", 60, 160, 20, SKYBLUE);}
        if(IsKeyDown(KEY_S)){DrawText("S", 80, 160, 20, SKYBLUE);}
        EndDrawing();
    }
}
