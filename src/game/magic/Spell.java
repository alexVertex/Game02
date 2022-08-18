package game.magic;

import engine.window.Actor;

import java.util.HashMap;

import static engine._SetsStrings.*;
import static engine._TextString.*;

public class Spell extends Actor {

    public static float TILES_TOTAL = F_SPELLS_TEXTURE_PART_CUT;

    public static HashMap<String, Spell> items = new HashMap<>();

    String desc = "";
    public int projectileID;
    public float leftTex,rightTex,topTex,botTex;
    public int distant;
    public int radius;
    private int effectID;
    public int effectTime;
    public double effectPower;
    public Spell(){

    }

    public Spell(String id, double x, double y){
        Spell copy = items.get(id);
        this.setX(x);
        this.setY(y);
        makeCopy(this,copy);
    }

    public Spell(Spell clone){
        makeCopy(this,clone);
    }
    public String texture;
    float partX, partY;
    public double staminaNeed;
    public int difficult;
    public int targetType;
    public Effect effect;
    String name;
    public int sort;
    public int type;
    public int cost;
    public String runes = "";
    public static final String[] targetTypes = SM_SPELL_TYPE_TEXT;
    public static final String[] classes = SM_SPELL_CLASSES_TEXT;

    public static final String[] elemental = SM_SPELL_SUBCLASS_ELEMENTAL_TEXT;
    public static final String[] sense = SM_SPELL_SUBCLASS_SENSE_TEXT;
    public static final String[] speechCraft = SM_SPELL_SUBCLASS_SPEECH_TEXT;
    public static final String[] closeCombat = SM_SPELL_SUBCLASS_WEAPON_TEXT;
    public static final String[] rangeCombat = SM_SPELL_SUBCLASS_RANGE_TEXT;
    public static final String[] shield = SM_SPELL_SUBCLASS_SHIELD_TEXT;

    private static final String[][] subtypes = {elemental,sense,speechCraft,closeCombat,rangeCombat,shield};

    public static void makeCopy(Spell clone,Spell copy) {
        clone.effectID = copy.effectID;
        clone.effectPower = copy.effectPower;
        clone.effectTime = copy.effectTime;
        clone.name = copy.name;
        clone.sort = copy.sort;
        clone.type = copy.type;
        clone.texture = copy.texture;
        clone.partX = copy.partX;
        clone.partY = copy.partY;
        clone.leftTex = clone.partX*TILES_TOTAL;
        clone.rightTex = (clone.partX+1)*TILES_TOTAL;
        clone.topTex = clone.partY*TILES_TOTAL;
        clone.botTex = (clone.partY+1)*TILES_TOTAL;
        clone.id = copy.id;
        clone.effect = new Effect(copy.effectID,copy.effectPower,copy.effectTime);
        clone.staminaNeed = copy.staminaNeed;
        clone.targetType = copy.targetType;
        clone.distant = copy.distant;
        clone.radius = copy.radius;
        clone.desc = copy.desc;
        clone.difficult = copy.difficult;
        clone.cost = copy.cost;
        clone.projectileID = copy.projectileID;
    }

    public static void loadItem(String id, String texture, float partX, float partY,String name,int cost, int sort, int type, int projectileID,int effectID, double effectPower, int effectTime, int targetType, double staminaNeed, int difficult, int distant, int radius, String desc){
        Spell item = new Spell();
        item.texture = P_TEXTURE_SPELLS_PATH+texture;
        item.partX = partX;
        item.partY = partY;
        item.name = name;
        item.sort = sort;
        item.difficult = difficult;
        item.staminaNeed = staminaNeed;
        item.targetType = targetType;
        item.effectID = effectID;
        item.effectPower = effectPower;
        item.effectTime = effectTime;
        item.type = type;
        item.projectileID = projectileID;
        item.distant = distant;
        item.radius = radius;
        item.desc = desc;
        item.cost = cost;
        item.id = id;
        items.put(id,item);
    }

    @Override
    public void init() {

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


    public void setUpGeneral(String[][] data){
        data[0][1] = name;
        data[1][0] = S_INTERFACE_DESCRIBE_LEFT_TYPE;
        data[1][1] = classes[sort];
        data[2][0] = S_INTERFACE_DESCRIBE_LEFT_SUBTYPE;
        data[2][1] = subtypes[sort][type];
        data[3][0] = S_INTERFACE_DESCRIBE_LEFT_EFFECT;
        data[3][1] = (int)effect.power1+"";
        data[4][0] = S_INTERFACE_DESCRIBE_LEFT_TIME;
        data[4][1] = effect.time+"";
        data[5][0] = S_INTERFACE_DESCRIBE_LEFT_TARGET_TYPE;
        data[5][1] = targetTypes[targetType];
        data[6][0] = S_INTERFACE_DESCRIBE_LEFT_STAMINA;
        data[6][1] = (int)staminaNeed+"";
        data[7][0] = S_INTERFACE_DESCRIBE_LEFT_DIFFICULT;
        data[7][1] = difficult+"";
        data[8][0] = S_INTERFACE_DESCRIBE_LEFT_DISTANT;
        data[8][1] = distant != 0 ? Math.abs(distant)+"" : "-";
        data[9][0] = S_INTERFACE_DESCRIBE_LEFT_RADIUS;
        data[9][1] = radius != 0 ? Math.abs(radius)+"" : "-";
        data[10][0] = S_INTERFACE_DESCRIBE_LEFT_COST;
        data[10][1] = cost+"";
        data[11][0] = S_INTERFACE_DESCRIBE_LEFT_DESCRIPTION;
        data[11][1] = desc+"";
    }

    public String[][] getDescription() {
        String[][] data = new String[12][2];
        setUpGeneral(data);
        return data;
    }

    public String getName() {
        return name;
    }

    public String getTexture() {
        return texture;
    }

    public float getPartX() {
        return partX;
    }

    public float getPartY() {
        return partY;
    }

    @Override
    public void doStuff(String command, String params) {

    }
}
