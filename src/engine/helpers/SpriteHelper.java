package engine.helpers;

import engine.render.Sprite;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static engine._SetsStrings.I_CAMERA_AFFECT_YES;

public class SpriteHelper {

    static List<Sprite> spriteList = new ArrayList<>();
    public static void addSprite(String tex, double lX, double lY, int lZ, double sX, double sY){
        Sprite sprite = new Sprite(tex,lX,lY,lZ,sX,sY,I_CAMERA_AFFECT_YES);
        spriteList.add(sprite);
    }

    public static void renderSprites(){
        spriteList.sort(Comparator.comparingInt(sprite -> sprite.zOrder));
        for(Sprite el : spriteList){
            el.drawQuad();
        }
    }

    public static void addSprite(Sprite sprite) {
        if(sprite == null) return;
        if(sprite.texture.equals("null")){
            int u = 0;
        }
        spriteList.add(sprite);
    }

    public static void removeSprite(Sprite sprite) {
        spriteList.remove(sprite);
        sprite.disposeModel();
    }
}
