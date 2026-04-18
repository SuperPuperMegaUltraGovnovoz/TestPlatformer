package org.example;

import com.raylib.Raylib.Rectangle;
import java.awt.*;

import static com.raylib.Raylib.*;

public class Button {
    Rectangle buttonRect;
    String nameButton;

    public Button(Rectangle buttonRect, String name) {
        this.buttonRect = buttonRect;
        this.nameButton = name;
    }

    public Button(Rectangle buttonRect) {
        this.buttonRect = buttonRect;
    }

    public static boolean isPressed(Rectangle button){
        Vector2 mouse = new Vector2().x(GetMouseX()).y(GetMouseY());
        return CheckCollisionPointRec(mouse, button);
    }
}
