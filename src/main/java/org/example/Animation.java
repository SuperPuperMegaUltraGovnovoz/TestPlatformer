package org.example;

public class Animation {

    public static boolean endAnim = false;

    public static int y = 0;
    public static int x = 0;

    public static void animation(){
        Game.player.size.x(x);
        Game.player.size.y(y);

        if(y != 40 * 2){
            y += 1;
        }
        if(x != 30 * 2){
            x += 1;
        }
        if(x == 30 * 2 && y == 40 * 2){endAnim = true;}
    }
}
