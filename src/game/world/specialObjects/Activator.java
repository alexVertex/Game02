package game.world.specialObjects;

import engine.core.Mathp;
import engine.render.Sprite;
import engine.script.Script;
import engine.script.ScriptMain;
import engine.window.Actor;
import game.general.Event;
import engine.helpers.SpriteHelper;
import engine.helpers.StarterHelper;
import game.players.Player;
import game.world.map.MapChanges;

import java.util.HashMap;

import static engine._SetsStrings.*;
import static engine._TextString.*;

public class Activator extends Actor {

    public static HashMap<String, Activator> activators = new HashMap<>();

    String texture,script;
    private Sprite sprite;
    private float progress;
    private float progressVel;
    private boolean floor;
    private int partX;
    private int lockLevel;
    public static void loadItem(String id, String script, String texture, int partX, boolean floor, int lockLevel) {
        Activator item = new Activator();
        item.texture = P_ACTIVATOR_TEXTURES_PATH+texture;
        item.partX = partX;
        item.floor = floor;
        item.lockLevel = lockLevel;
        item.script = script;
        activators.put(id,item);

    }
    public Activator(){}
    public Activator(String id,String localID, double x, double y) {
        Activator copy = activators.get(id);
        this.texture = copy.texture;
        this.setX(x*DEF_SIZE);
        this.setY(y*DEF_SIZE);
        this.partX = copy.partX;
        this.floor = copy.floor;
        this.lockLevel = copy.lockLevel;
        this.id = id;
        this.localID = localID;
        this.script = copy.script;
    }
    @Override
    public void init() {
        sprite = new Sprite(texture, locX, locY, I_ZORDER_ACTIVATOR, DEF_SIZE, DEF_SIZE,(partX)*F_ACTIVATOR_PART,0.0f,(partX+1)*F_ACTIVATOR_PART,F_ACTIVATOR_PART,true, I_CAMERA_AFFECT_YES);

        sprite.createModel();
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
        if( !activate(StarterHelper.game.getPlayer().getArms())){
            Event.makeEvent(S_EVENT_TOO_COMPLICATED_ACTIVATOR);
        } else {
            MapChanges.changeMapActivator(localID);
            Script use = ScriptMain.getScript(script);
            use.execute(this);
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
        return progress == 0 && !floor;
    }

    public boolean activate(int lockLevelPlayer) {
        if(lockLevelPlayer >= lockLevel) {
            progressVel = F_ACTIVATOR_PROGRESS_ADD;
            return true;
        }
        return false;
    }

    public void logic() {
        if(floor && progress == 0) {
            for(Player player : StarterHelper.game.getPlayers()) {
                double rast = Mathp.rast(getX(), getY(), player.getX(), player.getY());
                if (rast < I_ACTOR_DEF_SIZE) {
                    activate(player.getArms());
                    Script use = ScriptMain.getScript(script);
                    use.execute(this);
                    break;
                }
            }
        }
        if(progressVel > 0){
            progress+=progressVel;
            if(progress > 1){
                progress = 1;
                progressVel = 0;
            }
        }
        if(progressVel != 0){
            int part = (int) (progress/F_ACTIVATOR_PART);
            sprite.topTex = part * F_ACTIVATOR_PART;
            sprite.botTex = (1+part) * F_ACTIVATOR_PART;
            sprite.replaceModelTID();
        }
    }

    public boolean isFloored() {
        return floor;
    }

    @Override
    public void doStuff(String command, String params) {

    }
}
