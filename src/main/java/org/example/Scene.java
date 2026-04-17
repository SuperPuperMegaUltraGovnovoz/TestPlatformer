package org.example;

import java.util.Arrays;

public class Scene {
    private static int numScene = 0;
    private static int[] scenes = new int[2];

    public static void errScene(){
        boolean err = Arrays.stream(scenes).anyMatch(element -> element == numScene);
        if(err == false){
            numScene = 0;
            System.out.println("Error Scene");
        }
    }

    public Scene(int numScene) {
        this.numScene = numScene;
    }

    public Scene(int[] scenes) {
        this.scenes = scenes;
    }

}
