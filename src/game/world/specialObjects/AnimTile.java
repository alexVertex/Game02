package game.world.specialObjects;

import engine.render.Sprite;
import engine.window.Actor;
import engine.helpers.SpriteHelper;

import java.util.HashMap;

import static engine._SetsStrings.*;

public class AnimTile extends Actor {

    public static HashMap<String, AnimTile> tiles = new HashMap<>();
    private float speed;
    private float perFrame;
    private Sprite sprite;
    private String texture, script;

    public static void loadAnimTile(String id,String script, String texture, float Speed, int Frames) {
        AnimTile item = new AnimTile();
        item.texture = P_ANIMTILES_TEXTURES_PATH+texture;
        item.speed = Speed;
        item.perFrame = 1/(float)Frames;
        item.script = script;
        tiles.put(id,item);
    }

    public AnimTile(){}
    public AnimTile(String id,String localID, double x, double y) {
        AnimTile copy = tiles.get(id);
        this.texture = copy.texture;
        speed = copy.speed;
        perFrame = copy.perFrame;
        script = copy.script;
        this.setX(x);
        this.setY(y);
        this.id = id;
        this.localID = localID;
    }
    private float progress = 0;
    public void logic(){
        progress += speed;
        if(progress > 1){
            progress -= 1;
        }
        int curFrame = (int) (progress/perFrame);
        sprite.leftTex = perFrame*curFrame;
        sprite.rightTex = perFrame*(curFrame+1);
        sprite.replaceModelTID();
    }
    @Override
    public void init() {
        sprite = new Sprite(texture, locX, locY, I_ZORDER_ANIMTILES, DEF_SIZE, DEF_SIZE, I_CAMERA_AFFECT_YES);
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

    }

    public void putOnMap() {
        SpriteHelper.addSprite(sprite);
        Actor.addActorToBase(localID,this);

    }
    public void removeFromMap() {
        SpriteHelper.removeSprite(sprite);
    }

    @Override
    public void doStuff(String command, String params) {

    }
}
