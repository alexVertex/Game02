package engine.window;

import engine.audio.AudioSource;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;

import static engine._SetsStrings.I_ACTOR_DEF_SIZE;

public abstract class Actor implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public static final int DEF_SIZE = I_ACTOR_DEF_SIZE;
    public HashMap<String, Float> floats = new HashMap<>();

    public static HashMap<String, Actor> base = new HashMap<>();
    public static void addActorToBase(String localID,Actor actor){
        actor.localID = localID;
        base.put(localID, actor);
    }
    public static void removeActorFromBase(String localID){
        base.remove(localID);
    }
    public String id, localID;
    public String script;
    public abstract void init();
    public abstract void doStuff(String command, String params);

    public abstract void dispose();

    public double locX;
    public double locY;

    public transient AudioSource voice;
    public double getX(){
        return locX;
    }

    public double getY(){
        return locY;
    }

    protected void setX(double X) {
        locX = X;
    }
    protected void setY(double Y) {
        locY = Y;
    }

    public abstract void hide();

    public abstract void show();

    public abstract void use();
}
