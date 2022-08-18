package game.world.specialObjects;

import engine.window.Actor;

import java.util.HashMap;

import static engine._SetsStrings.*;

public class SoundSource extends Actor {
    public static HashMap<String, SoundSource> items = new HashMap<>();


    public String sound, script;
    public double voiceRast;
    public static void loadItem(String id,String script, String sound, double voiceRast){
        SoundSource item = new SoundSource();
        item.sound = P_AMBIENT_SOUND_PATH+sound;
        item.voiceRast = voiceRast;
        item.script = script;
        items.put(id,item);
    }
    
    public SoundSource(){

    }
    public SoundSource(String id,String localID, double x, double y){
        SoundSource copy = items.get(id);
        this.setX(x);
        this.setY(y);
        this.sound = copy.sound;
        this.voiceRast = copy.voiceRast;
        this.id = id;
        this.localID = localID;
        this.script = copy.script;
    }
    
    @Override
    public void init() {
        Actor.addActorToBase(localID,this);

    }

    @Override
    public void dispose() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void show() {

    }

    @Override
    public void use() {

    }

    public void stop() {
        if(voice != null) {
            voice.stop();
        }
    }

    @Override
    public void doStuff(String command, String params) {

    }
}
