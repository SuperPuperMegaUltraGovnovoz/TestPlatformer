package org.example;

import com.raylib.Raylib;

public class Object {
    Raylib.Vector2 position;
    Raylib.Vector2 size;

    public Object(int x, int y, int width, int height) {
        this.position = new Raylib.Vector2().x(x).y(y);
        this.size = new Raylib.Vector2().x(width).y(height);
    }
}
