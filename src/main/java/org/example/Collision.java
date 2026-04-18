package org.example;

import static com.raylib.Raylib.*;

public class Collision {
    static void disCollision(Player player){
        player.CollisionWithUp = false;
        player.CollisionWithRight = false;
        player.CollisionWithLeft = false;
        player.onFloor = false;
    }

    public static boolean collision(Rectangle box1, Rectangle box2){
        return CheckCollisionRecs(box1, box2);
    }

    public static void collisionU(Player player, Object object){
        Rectangle box1 = new Rectangle().x(player.position.x() - player.size.x()).y(player.position.y() - player.size.y() - 0.6f * TickSystem.delta * Game.multVelosity).width(player.size.x() * 2).height(player.size.y());
        if(collision(box1, object.box2)){
            player.CollisionWithUp = true;
        }
    }

    public static void collisionD(Player player, Object object){
        Rectangle box1 = new Rectangle().x(player.position.x() - player.size.x()).y(player.position.y() + 0.4f * TickSystem.delta * Game.multVelosity).width(player.size.x() * 2).height(player.size.y());
        if(collision(box1, object.box2)){
            player.onFloor = true;
        }
    }

    public static void collisionR(Player player, Object object){
        Rectangle box1 = new Rectangle().x(player.position.x() + 0.6f * TickSystem.delta * Game.multVelosity).y(player.position.y() - player.size.y() - 0.4f * TickSystem.delta * Game.multVelosity).width(player.size.x()).height(player.size.y() * 2);
        if(collision(box1, object.box2)){
            player.CollisionWithRight = true;
        }
    }

    public static void collisionL(Player player, Object object){
        Rectangle box1 = new Rectangle().x(player.position.x() - player.size.x() - 0.6f * TickSystem.delta * Game.multVelosity).y(player.position.y() - player.size.y() - 0.4f * TickSystem.delta * Game.multVelosity).width(player.size.x()).height(player.size.y() * 2);
        if(collision(box1, object.box2)){
            player.CollisionWithLeft = true;
        }
    }
}
