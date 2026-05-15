package org.example;

import com.raylib.Raylib;

import java.util.ArrayList;

public class Animation {

    boolean loop;
    boolean withPlayer;
    int fps;
    ArrayList<Raylib.Texture> textures;

    boolean animPlay;
    float timer;
    int currentFrame;
    float frameDuration;

    public Animation(boolean loop, int fps, boolean withPlayer) {
        this.loop = loop;
        this.fps = fps;
        this.withPlayer = withPlayer;
        this.animPlay = false;
        this.timer = 0;
        this.currentFrame = 0;
        this.frameDuration = 0;
    }

    public void play(){
        this.timer = 0f;
        this.animPlay = true;
        this.currentFrame = 0;
    }

    public void stop(){
        this.animPlay = false;
    }

    public void Animator(){
        this.frameDuration = 1f/this.fps;

        if (!this.animPlay || this.textures.isEmpty()){return;}

        this.timer += TickSystem.delta;
        if(this.timer >= this.frameDuration){
            this.timer -= this.frameDuration;
            if(this.withPlayer){
                Game.player.texture = this.textures.get(this.currentFrame);}
            this.currentFrame++;

            if(this.currentFrame >= this.textures.size()){
                if(this.loop){
                    this.currentFrame = 0;}
                else{stop();}
            }
        }
    }
}
