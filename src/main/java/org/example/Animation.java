package org.example;

public class Animation {

    public static boolean endAnim = false;

    public static int y = 0;
    public static int x = 0;

    public static void animation(){
        Game.player.width = y;
        Game.player.height = x;

        if(y != 30 * 2){
            y += 1;
        }
        if(x != 40 * 2){
            x += 1;
        }
        if(x == 40 * 2 && y == 30 * 2){endAnim = true;}
    }
}
