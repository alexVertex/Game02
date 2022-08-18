package game.world.specialObjects;

import engine.core.Mathp;
import engine.render.Sprite;
import engine.window.Actor;
import engine.helpers.SpriteHelper;
import game.players.Player;

import java.util.HashMap;

import static engine._SetsStrings.*;

public class Structure  extends Actor {

    public static HashMap<String, Structure> items = new HashMap<>();


    Sprite spriteSolid, spriteLiquid;
    String texture, script;
    float partX, partY;
    float sizeX, sizeY, transparentPart;
    public static float TILES_TOTAL = F_STRUCTURE_PART;

    double X, Y, SX, SY;
    public Structure(){}
    public static void loadItem(String id,String script, String texture, float partX, float partY, float sizeX, float sizeY, float transparentPart){
        Structure item = new Structure();
        item.texture = P_STRUCTURES_TEXTURES_PATH+texture;
        item.partX = partX;
        item.partY = partY;
        item.sizeX = sizeX;
        item.sizeY = sizeY;
        item.transparentPart = transparentPart;
        item.script = script;
        items.put(id,item);
    }
    double liquidX, liquidY;
    double solidX, solidY;

    public Structure(String id,String localID, double x, double y){
        Structure copy = items.get(id);
        this.transparentPart = copy.transparentPart;
        double remY = y;
        y -= (transparentPart-1)/2*DEF_SIZE;
        x += (copy.sizeX-1)/2*DEF_SIZE;
        liquidX = x;
        liquidY = y;
        this.texture = copy.texture;
        X = x;
        Y = y;
        SX = DEF_SIZE*(1+copy.sizeX);
        SY = DEF_SIZE*(1+(copy.sizeY-(copy.sizeY-transparentPart)));

        y = remY - (copy.sizeY-transparentPart-1)/2*DEF_SIZE - DEF_SIZE * transparentPart;
        solidX = x;
        solidY = y;
       this.setX(x);
        this.setY(y);
        this.script = copy.script;
        this.id = id;
        this.localID = localID;
    }

    @Override
    public void init() {
        spriteLiquid = new Sprite(texture,liquidX,liquidY,I_ZORDER_STRUCTURE_LIQUID,DEF_SIZE*sizeX,DEF_SIZE*(sizeY-(sizeY-transparentPart)),
                TILES_TOTAL*partX,TILES_TOTAL*partY,TILES_TOTAL*(partX+sizeX),TILES_TOTAL*(partY+(transparentPart)),true,I_CAMERA_AFFECT_YES);
        spriteSolid = new Sprite(texture,solidX,solidY,I_ZORDER_STRUCTURE_HARD,DEF_SIZE*sizeX,DEF_SIZE*((sizeY-transparentPart)),
                TILES_TOTAL*partX,TILES_TOTAL*transparentPart,TILES_TOTAL*(partX+sizeX),TILES_TOTAL*(partY+sizeY),true,I_CAMERA_AFFECT_YES);
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
        SpriteHelper.addSprite(spriteSolid);
        SpriteHelper.addSprite(spriteLiquid);
        Actor.addActorToBase(localID,this);

    }

    public void logic(Player player) {
        boolean inLiquid = Mathp.isPointInRect(X,Y,SX,SY,player.getX(),player.getY());
        if(inLiquid){
            spriteLiquid.alpha = F_STRUCTURE_LIQUID_PART_ALPHA;
        } else {
            spriteLiquid.alpha = 1.0f;
        }
    }

    public void removeFromMap() {
        SpriteHelper.removeSprite(spriteSolid);
        SpriteHelper.removeSprite(spriteLiquid);
    }

    @Override
    public void doStuff(String command, String params) {

    }
}
