package org.example;

import com.raylib.Raylib;

import java.util.ArrayList;
import java.util.HashMap;

import static com.raylib.Colors.*;
import static com.raylib.Raylib.*;
import static org.example.Main.debagMode;

public class Game {

    static Texture texture;
    static Rectangle world = new Rectangle().x(300).y(300).width(40).height(40);
    static ArrayList<Object> object = new ArrayList<>(5);
    static Ladder lader = new Ladder((int)world.x() + 1000, (int)world.y() + 50, 2000, 40 * 2, 45);
    static Camera2D camera = new Camera2D();
    static Player player = new Player((int) camera.target().x() + 50, (int) camera.target().y() - 500 * 2, 40, 80, texture, true);
    static int tgFPS = 60;
    static final float multVelocity = 500;
    static final Vector2 velocity = new Vector2().x(0).y(0);
    static private final float vSpeed = 0.8f;
    static Animation walk = new Animation(true, 15, true);
    static public HashMap<String, ArrayList<Object>> grid = new HashMap<>();
    static final int GRID_SIZE = 150;

    public static void init(){
        object.add(new Object((int)world.x() + 330, (int)world.y() - 100, 40 * 6, 20 * 2));
        object.add(new Object((int)world.x() - 500, (int)world.y() + 50, 2000, 40 * 2));
        object.add(new Object((int)world.x() + 125, (int)world.y() - 50, 40 * 6, 20 * 2));
        object.add(new Object((int)world.x() + 125 + 330, (int)world.y() - 50, 40 * 6, 20 * 2));
        object.add(new Object((int)world.x() + 330 , (int)world.y() - 320, 40 * 6, 20 * 2));

        camera.target(new Vector2().x(world.x() + 60).y(world.y() - 60));
        camera.offset(new Vector2().x(Main.screenWidth /2).y(Main.screenHeight /2));
        camera.rotation(0.0f);
        camera.zoom(0.5f);

        walk.textures = new ArrayList<> (2);
        walk.textures.add(LoadTexture(ResourceManager.getTexturePath("PlayerStay.png")));
        walk.textures.add(LoadTexture(ResourceManager.getTexturePath("PlayerWalk1.png")));
        walk.textures.add(LoadTexture(ResourceManager.getTexturePath("PlayerWalk2.png")));
        walk.textures.add(LoadTexture(ResourceManager.getTexturePath("PlayerWalk3.png")));

        player.texture = walk.textures.get(0);
        createGrid();
    }

    public static void createGrid(){
        grid.clear();
        for(Object object1 : object){
            int SgridX = (int)Math.floor(object1.position.x() / GRID_SIZE);
            int SgridY = (int)Math.floor(object1.position.y() / GRID_SIZE);
            int EgridX = (int)Math.floor((object1.position.x() + object1.size.x()) / GRID_SIZE);
            int EgridY = (int)Math.floor((object1.position.y() + object1.size.y()) / GRID_SIZE);
            for(int X = SgridX; X <= EgridX; X++) {
                for (int Y = SgridY; Y <= EgridY; Y++) {
                    String key = X + ", " + Y;
                    grid.computeIfAbsent(key, k -> new ArrayList<>()).add(object1);
                }
            }
        }
    }

    public static void update() {
        //обнуление коллизий
        Collision.disCollision(player);
        //фпс
        SetTargetFPS(tgFPS);
        if (IsKeyDown(KEY_ONE)) {
            tgFPS = 0;
        }
        if (IsKeyDown(KEY_TWO)) {
            tgFPS = 60;
        }
        if (IsKeyDown(KEY_THREE)) {
            tgFPS = 30;
        }

        //столкновение
        Collision.checkCollision();

        //гравитация
        if (!player.onFloor) {
            velocity.y(velocity.y() - 0.0025f * TickSystem.delta * multVelocity);
            velocity.y(Math.max(velocity.y(), -0.8f));
            camera.target().y(camera.target().y() - velocity.y() * TickSystem.delta * multVelocity);
        }

        //взаимодействие с коллизией
        if (velocity.y() >= 0 && player.CollisionWithUp) {
            velocity.y(0f);
        }



        if (IsKeyDown(KEY_SPACE)) {
            velocity.y(vSpeed);
            camera.target().y(camera.target().y() - velocity.y() * TickSystem.delta * multVelocity);
        }else if(player.onFloor && !IsKeyDown(KEY_SPACE)){
            velocity.y(0);}


        if (IsKeyDown(KEY_S)) {
            if (player.onFloor) {
                velocity.y(0);
            } else {
                velocity.y(-vSpeed);
            }
            camera.target().y(camera.target().y() - velocity.y() * TickSystem.delta * multVelocity);
        }

        if (IsKeyDown(KEY_A) && !IsKeyDown(KEY_D)) {
            if (player.CollisionWithLeft) {
                velocity.x(0);
                walk.stop();
            } else {
                velocity.x(vSpeed);
                if(!walk.animPlay){
                    walk.play();}
                walk.Animator();
            }
            player.facingRight = false;
            camera.target().x(camera.target().x() - velocity.x() * TickSystem.delta * multVelocity);
        }


        if (IsKeyDown(KEY_D) && !IsKeyDown(KEY_A)) {
            if (player.CollisionWithRight) {
                velocity.x(0);
                walk.stop();
            } else {
                velocity.x(vSpeed);
                if(!walk.animPlay){
                walk.play();}
                walk.Animator();
            }
            player.facingRight = true;
            camera.target().x(camera.target().x() + velocity.x() * TickSystem.delta * multVelocity);
        }

        if (!IsKeyDown(KEY_A) && !IsKeyDown(KEY_D)) {
            walk.stop();
            player.texture = walk.textures.get(0);
        }

        player.position.x(camera.target().x());
        player.position.y(camera.target().y());
    }

    public static void render(){
        Raylib.Vector2 origin;
        Raylib.Rectangle dest;
        Raylib.Rectangle source;

        source = new Raylib.Rectangle().x(0).y(0).width(player.facingRight ? 36 : -36).height(88);
        dest = new Raylib.Rectangle().x(player.position.x() - player.size.x()).y(player.position.y() - player.size.y()).width(player.size.x() * 2f).height(player.size.y() * 2f);
        origin = new Raylib.Vector2().x(0).y(0);

        //изменение зума
        camera.offset(new Vector2().x(Main.screenWidth /2).y(Main.screenHeight /2));
        camera.zoom((camera.zoom()) + (GetMouseWheelMove() * 0.03f));
        camera.zoom(Math.max(camera.zoom(), 0.02f));

        BeginDrawing();
        ClearBackground(RAYWHITE);
        BeginMode2D(camera);

        //отрисовка
        for (Object object1 : object) {
            DrawRectangle((int) object1.position.x(), (int) object1.position.y(), (int) object1.size.x(), (int) object1.size.y(), VIOLET);
        }

        DrawRectanglePro(lader.box2, lader.origin, lader.rotation, VIOLET);

        DrawTexturePro(player.texture, source, dest, origin, 0f, WHITE);

        EndMode2D();
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
