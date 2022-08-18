package game.world.specialObjects;

import engine.core.Mathp;
import engine.render.Sprite;
import engine.window.Actor;
import game.general.Event;
import engine.helpers.SpriteHelper;
import engine.helpers.StarterHelper;
import game.players.Player;

import java.util.HashMap;

import static engine._SetsStrings.*;
import static engine._TextString.*;

public class Door extends Actor {
    public static HashMap<String, Door> doors = new HashMap<>();
    String texture, script;
    private Sprite sprite;
    private boolean closed;
    private float progress;
    private float progressVel;
    private int lockLevel;

    public static void loadItem(String id,String script, String texture, int lockLevel) {
        Door item = new Door();
        item.texture = P_DOORS_TEXTURES_PATH+texture;
        item.lockLevel = lockLevel;
        item.script = script;
        doors.put(id,item);
    }
    int part = 0;
    public Door(){}
    public Door(String id,String localID, double x, double y, boolean isClosed) {
        Door copy = doors.get(id);
        this.texture = copy.texture;
        part = isClosed ? 0 : 3;
        progress = isClosed ? 0 : 1;
        closed = isClosed;
        script = copy.script;
        this.setX(x*DEF_SIZE);
        this.setY(y*DEF_SIZE);
        this.lockLevel = copy.lockLevel;
        this.id = id;
        this.localID = localID;
        this.texture = texture;
    }

    public void logic(){
        if(progressVel > 0){
            progress+=progressVel;
            if(progress > 1){
                progress = 1;
                progressVel = 0;
            }
            closed = false;
        }
        if(progressVel < 0){
            progress+=progressVel;
            if(progress < 0){
                progress = 0;
                progressVel = 0;
            }
            closed = true;
        }
        if(progressVel != 0){
            int part = (int) (progress/F_DOOR_PART);
            sprite.topTex = part * F_DOOR_PART;
            sprite.botTex = (1+part) * F_DOOR_PART;
            sprite.replaceModelTID();
        }

    }
    @Override
    public void init() {
        sprite = new Sprite(texture, locX,locY, I_ZORDER_DOOR, DEF_SIZE, DEF_SIZE,0,F_DOOR_PART*part,1,F_DOOR_PART*(part+1),true, I_CAMERA_AFFECT_YES);
        putOnMap();
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
        if(!openClose(StarterHelper.game.getPlayer().getArms())){
            if(lockLevel > StarterHelper.game.getPlayer().getArms()) {
                Event.makeEvent(S_EVENT_TOO_COMPLICATED_CHEST);
            }
        }
    }

    public void putOnMap() {
        SpriteHelper.addSprite(sprite);
        Actor.addActorToBase(localID,this);

    }
    public void removeFromMap() {
        SpriteHelper.removeSprite(sprite);
    }

    public boolean getState() {
        return closed;
    }

    public boolean openClose(int lockLevelPlayer) {
        if(progressVel == 0 && lockLevelPlayer >= lockLevel){
            lockLevel = 0;
            if(progress == 0){
                progressVel+=F_DOOR_PROGRESS_ADD;
                StarterHelper.game.setPass(getX()/DEF_SIZE,getY()/DEF_SIZE,I_PASS_FREE);
            } else {
                if(canClose()) {
                    progressVel -= F_DOOR_PROGRESS_ADD;
                    StarterHelper.game.setPass(getX() / DEF_SIZE, getY() / DEF_SIZE, I_PASS_WALL);
                }
            }
            return true;
        }
        return false;
    }

    public boolean canClose(){
        for(Player el : StarterHelper.game.getPlayers()){
            if(Mathp.rast(el.getX(),el.getY(),getX(),getY()) < 32){
                return false;
            }
        }
        return true;
    }

    @Override
    public void doStuff(String command, String params) {

    }
}
