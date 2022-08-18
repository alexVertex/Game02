package game.world.map;

import engine.render.Sprite;

import java.util.ArrayList;
import java.util.List;

import static engine._SetsStrings.*;

public class Tile {
    public static List<Tile> tiles = new ArrayList<>();
    public static float TILES_TOTAL = F_SPELLS_TEXTURE_PART_CUT;

    private String texture;
    private int partX, partY;
    public Tile(String Texture, int X, int Y){
        texture = Texture;
        partX = X;
        partY = Y;
    }

    public static Sprite loadTile(int index, int X, int Y) {
        Tile copy = tiles.get(index);
        return new Sprite(copy.texture,X*I_ACTOR_DEF_SIZE,Y*I_ACTOR_DEF_SIZE,I_ZORDER_TILES,I_ACTOR_DEF_SIZE,I_ACTOR_DEF_SIZE,TILES_TOTAL*copy.partX,TILES_TOTAL*copy.partY,TILES_TOTAL*(1+copy.partX),TILES_TOTAL*(1+copy.partY),true,I_CAMERA_AFFECT_YES);
    }

    public static void loadData(List<String> loadFile) {
        for(String el : loadFile){
            String[] split = el.split(";");
            Tile test = new Tile(P_TILES_TEXTURES_PATH+split[0],Integer.parseInt(split[1]),Integer.parseInt(split[2]));
            tiles.add(test);
        }
    }
}
