package org.example;

import java.util.ArrayList;

import static com.raylib.Raylib.*;
import static org.example.Game.*;

public class Collision {
    static void disCollision(Player player){
        player.CollisionWithUp = false;
        player.CollisionWithRight = false;
        player.CollisionWithLeft = false;
        player.onFloor = false;
    }


    public static boolean _collision(Rectangle box1, Rectangle box2){
        return CheckCollisionRecs(box1, box2);
    }

    public static void collision(Player player, Object object){
        Rectangle box1 = new Rectangle().x(player.position.x() - player.size.x()).y(player.position.y() - player.size.y() - 0.8f * TickSystem.delta * Game.multVelocity).width(player.size.x() * 2).height(player.size.y());
        if(_collision(box1, object.box2)){
            if((box1.y()) - (object.box2.y() + object.box2.height()) < 1){
            player.CollisionWithUp = true;}
        }

        box1 = new Rectangle().x(player.position.x() - player.size.x()).y(player.position.y() + 0.8f * TickSystem.delta * Game.multVelocity).width(player.size.x() * 2).height(player.size.y());
        if(_collision(box1, object.box2)){
            player.onFloor = true;
        }

        box1 = new Rectangle().x(player.position.x() + 0.8f * TickSystem.delta * Game.multVelocity).y(player.position.y() - player.size.y()).width(player.size.x()).height(player.size.y() * 2);
        if(_collision(box1, object.box2)){
            player.CollisionWithRight = true;
        }

        box1 = new Rectangle().x(player.position.x() - player.size.x() - 0.8f * TickSystem.delta * Game.multVelocity).y(player.position.y() - player.size.y()).width(player.size.x()).height(player.size.y() * 2);
        if(_collision(box1, object.box2)){
            player.CollisionWithLeft = true;
        }
    }

    public static void pushOut(Player player, Object obj) {
        Rectangle playerRect = new Rectangle()
                .x(player.position.x() - player.size.x())
                .y(player.position.y() - player.size.y())
                .width(player.size.x() * 2)
                .height(player.size.y() * 2);

        if (!CheckCollisionRecs(playerRect, obj.box2)) return;

        // Вычисляем перекрытия по всем сторонам
        float overlapLeft   = (playerRect.x() + playerRect.width()) - obj.box2.x();
        float overlapRight  = (obj.box2.x() + obj.box2.width()) - playerRect.x();
        float overlapTop    = (playerRect.y() + playerRect.height()) - obj.box2.y();
        float overlapBottom = (obj.box2.y() + obj.box2.height()) - playerRect.y();

        // Находим минимальное
        float minX = Math.min(overlapLeft, overlapRight);
        float minY = Math.min(overlapTop, overlapBottom);

        if (minX < minY) {
            // Выталкиваем по горизонтали
            if (overlapLeft < overlapRight) {
                player.position.x(player.position.x() - overlapLeft);
            } else {
                player.position.x(player.position.x() + overlapRight);
            }
            Game.camera.target().x(player.position.x());
        } else {
            // Выталкиваем по вертикали
            if (overlapTop < overlapBottom) {
                player.position.y(player.position.y() - overlapTop);
            } else {
                player.position.y(player.position.y() + overlapBottom);
            }
            Game.camera.target().y(player.position.y());
        }
    }

    public static void checkCollision(){
        Vector2 pos = new Vector2().x((int)Math.floor(player.position.x() / GRID_SIZE)).y((int)Math.floor(player.position.y() / GRID_SIZE));

        for(int dx = -1; dx <= 1; dx++){
            for(int dy = -1; dy <= 1; dy++){
                String key = ((int)pos.x() + dx) + ", " + ((int)pos.y() + dy);
                ArrayList<Object> objects = grid.get(key);
                if(objects == null) continue;
                for(Object object1 : objects){
                    Collision.collision(player, object1);
                    Collision.pushOut(player, object1);
                }
            }
        }
    }
}
