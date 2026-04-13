package org.example;

import com.raylib.Raylib;

public class Player {
    Raylib.Vector2 position;
    Raylib.Vector2 size;

    boolean onFloor;
    boolean CollisionWithRight;
    boolean CollisionWithLeft;
    boolean CollisionWithUp;


    public Player(int x, int y, int width, int height) {
        this.position = new Raylib.Vector2().x(x).y(y);
        this.size = new Raylib.Vector2().x(width).y(height);
        this.onFloor = false;
        this.CollisionWithRight = false;
        this.CollisionWithLeft = false;
        this.CollisionWithUp = false;
    }
}
