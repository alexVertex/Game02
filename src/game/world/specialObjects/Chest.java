package game.world.specialObjects;

import engine.core.Mathp;
import engine.render.Sprite;
import engine.window.Actor;
import game.general.Event;
import engine.helpers.SpriteHelper;
import engine.helpers.StarterHelper;
import game.players.Player;
import game.players.PlayerLoader;
import game.world.map.MapChanges;

import java.util.HashMap;
import java.util.List;

import static engine._SetsStrings.*;
import static engine._TextString.*;
import static game.world.specialObjects.Item.TILES_TOTAL;

public class Chest extends Actor {

    public static HashMap<String, Chest> chests = new HashMap<>();
    public boolean corpse = false;
    private int gold;
    private List<Item> content;
    String texture,script;
    private Sprite sprite;
    private float progress;
    private float progressVel;
    private int partX;
    private int lockLevel;

    public Chest(Player player) {
        super();
        content = PlayerLoader.getLoot(player.loot);
        gold = (int) (Mathp.random()*(player.goldMax-player.goldMin+1)+player.goldMin);
        texture = P_DATA_CHARS_TEXTURES_PATH+player.texture;
        partX = 3;
        corpse = true;
        localID = player.localID+"lootChest";
        this.setX(player.getX());
        this.setY(player.getY());
        sprite = new Sprite(texture, getX(), getY(), I_ZORDER_CHEST, DEF_SIZE, DEF_SIZE,(partX)*F_CHEST_PART,0.0f,(partX+1)*F_CHEST_PART,F_CHEST_PART,true, I_CAMERA_AFFECT_YES);
        sprite.setAngle(player.getSprite().getAngle());

    }

    public static void loadItem(String id,String script, String texture, int partX, int lockLevel, boolean isCorpse) {
        Chest item = new Chest();

        item.texture = P_CHESTS_TEXTURES_PATH+texture;
        item.partX = partX;
        item.lockLevel = lockLevel;
        item.corpse = isCorpse;
        item.script = script;
        chests.put(id,item);
    }

    public Chest(){}
    public Chest(String id,String localID, double x, double y, List<Item> content, int gold) {
        Chest copy = chests.get(id);
        this.texture = copy.texture;
        this.partX = copy.partX;
        this.setX(x*DEF_SIZE);
        this.setY(y*DEF_SIZE);
        this.content = content;
        this.gold = gold;
        this.lockLevel = copy.lockLevel;
        this.corpse = copy.corpse;
        this.id = id;
        this.localID = localID;
        this.script = copy.script;
    }

    @Override
    public void init() {
        sprite = new Sprite(texture, locX,locY, I_ZORDER_CHEST, DEF_SIZE, DEF_SIZE,(partX)*F_CHEST_PART,0.0f,(partX+1)*F_CHEST_PART,F_CHEST_PART,true, I_CAMERA_AFFECT_YES);
        putOnMap();
    }

    @Override
    public void dispose() {
        SpriteHelper.removeSprite(sprite);
    }

    @Override
    public void hide() {

    }

    @Override
    public void show() {

    }

    @Override
    public void use() {
        if( !search(StarterHelper.game.getPlayer().getArms())){
            Event.makeEvent(S_EVENT_TOO_COMPLICATED_CHEST);
        }
        if(!corpse) {
            MapChanges.changeMapChest(localID);
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
         return progress == 0;
    }

    public void open(){
        progressVel = F_CHEST_PROGRESS_ADD;
    }

    public boolean search(int lockLevelPlayer) {
        if(lockLevelPlayer >= lockLevel) {
            progressVel = F_CHEST_PROGRESS_ADD;
            for (Item el : content) {
                el.sprite = new Sprite(el.texture,locX,locY,I_ZORDER_ITEM,DEF_SIZE,DEF_SIZE,TILES_TOTAL*el.partX,TILES_TOTAL*el.partY,TILES_TOTAL*(el.partX+1),TILES_TOTAL*(el.partY+1),true,I_CAMERA_AFFECT_YES);

                StarterHelper.game.getInventory().add(el);
                Event.makeEvent(S_EVENT_PICK_UP + el.getName());
            }
            if (gold > 0) {
                StarterHelper.game.gold += gold;
                Event.makeEvent(S_EVENT_PICK_UP_MONEY + gold);
            }
            return true;
        }
        return false;
    }

    public boolean logic() {
        if(progressVel > 0){
            if(corpse) return true;
            progress+=progressVel;
            if(progress > 1){
                progress = 1;
                progressVel = 0;
            }

        }
        if(progressVel != 0){
            int part = (int) (progress/F_CHEST_PART);
            sprite.topTex = part * F_CHEST_PART;
            sprite.botTex = (1+part) * F_CHEST_PART;
            sprite.replaceModelTID();
        }
        return false;
    }

    @Override
    public void doStuff(String command, String params) {

    }
}
