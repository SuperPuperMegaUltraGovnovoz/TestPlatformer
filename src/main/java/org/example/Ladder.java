package org.example;

import com.raylib.Raylib;

public class Ladder {
    int rotation;

    Raylib.Vector2 position;
    Raylib.Vector2 size;
    Raylib.Rectangle box2;
    Raylib.Vector2 origin;

    public Ladder(int x, int y, int width, int height, int rotation) {
        this.position = new Raylib.Vector2().x(x).y(y);
        this.size = new Raylib.Vector2().x(width).y(height);
        this.box2 = new Raylib.Rectangle().x(position.x()).y(position.y()).width(size.x()).height(size.y());
        this.rotation = rotation;
        this.origin = new Raylib.Vector2().x(0).y(0);
    }
}
