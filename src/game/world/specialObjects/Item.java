package game.world.specialObjects;

import engine.render.Sprite;
import engine.window.Actor;
import game.general.Event;
import engine.helpers.SpriteHelper;
import engine.helpers.StarterHelper;
import game.magic.Effect;
import game.world.map.MapChanges;

import java.util.HashMap;

import static engine._SetsStrings.*;
import static engine._TextString.*;

public class Item extends Actor {
    public Effect effect;
    public double damageReduction;
    public double staminaDamageReduction;
    public double balanceDamage;

    public static HashMap<String, Item> items = new HashMap<>();

    public static float TILES_TOTAL = F_SPELLS_TEXTURE_PART_CUT;
    public double damage;
    public int interval;
    public int cost;
    public int damageType;
    public int ammoMax, ammo;
    public int radius;
    public int projectileID;
    public int whatToRaise;
    public double raiseMult;
    public int staminaNeed;
    String label ;

    public Item(){

    }

    public Item(String id,String localID, double x, double y){
        Item copy = items.get(id);
        this.setX(x);
        this.setY(y);
        name = copy.name;
        sort = copy.sort;
        type = copy.type;
        weight = copy.weight;
        texture = copy.texture;
        partX = copy.partX;
        partY = copy.partY;
        damage = copy.damage;
        interval = copy.interval;
        damageType = copy.damageType;
        ammoMax = copy.ammoMax;
        ammo = copy.ammoMax;
        if(copy.effectID != -1)
            effect = new Effect(copy.effectID,copy.effectPower,copy.effectTime);
        desc = copy.desc;
        this.distant = copy.distant;
        this.radius = copy.radius;
        this.id = id;
        this.damageReduction = copy.damageReduction;
        this.staminaDamageReduction = copy.staminaDamageReduction;
        this.localID = localID;

        if(copy.defenses != null){
            defenses = new int[11];
            System.arraycopy(copy.defenses, 0, defenses, 0, copy.defenses.length);
        }
        this.whatToRaise = copy.whatToRaise;
        this.raiseMult = copy.raiseMult;
        this.staminaNeed = copy.staminaNeed;
        this.label = copy.label;
        this.projectileID = copy.projectileID;
        this.balanceDamage = copy.balanceDamage;
        this.cost = copy.cost;
        this.difficult = copy.difficult;
    }

    public int difficult;
    Sprite sprite;
    String texture, desc = "";
    float partX, partY;

    int effectID = -1;
    float effectPower;
    int effectTime;
    public int[] defenses = null;
    public int weight = 0;
    String name;
    public int sort;
    public int type;
    public int distant;
    public static final String[] classes = SM_ITEMS_CLASSES;
    public static final String[] swords = SM_ITEMS_TYPES_SWORD;
    public static final String[] shortDrev = SM_ITEMS_TYPES_AXES;
    public static final String[] longDrev = SM_ITEMS_TYPES_SPEARS;
    public static final String[] range = SM_ITEMS_TYPES_RANGE;
    public static final String[] shield = SM_ITEMS_TYPES_SHIELD;
    public static final String[] armor = SM_ITEMS_TYPES_ARMOR;
    public static final String[] amulet = SM_ITEMS_TYPES_AMULET;
    public static final String[] uses = SM_ITEMS_TYPES_USES;
    public static final String[] materials = SM_ITEMS_TYPES_MATERIALS;
    public static final String[] prise = SM_ITEMS_TYPES_MISC;

    private static final String[][] subtypes = {swords,shortDrev,longDrev,range,shield,armor,amulet,uses,materials,prise};

    //Броня
    public static void loadItem(String id, String texture, float partX, float partY,String name, int sort, int type,int weight,int cost, int defense0, int defense1, int defense2, int defense3, int defense4, int defense5, int defense6, int defense7, int defense8, int defense9, int defense10){
        Item item = new Item();
        item.texture = P_ITEMS_TEXTURES_PATH+texture;
        item.partX = partX;
        item.partY = partY;
        item.name = name;
        item.sort = sort;
        item.type = type;
        item.weight = weight;
        item.cost = cost;
        item.defenses = new int[]{defense0,defense1,defense2,defense3,defense4,defense5,defense6,defense7,defense8,defense9,defense10};
        items.put(id,item);
    }
    //Щит
    public static void loadItem(String id, String texture, float partX, float partY,String name, int sort, int type,int weight,int cost, double damageReduction, double staminaDamageReduction){
        Item item = new Item();
        item.texture = P_ITEMS_TEXTURES_PATH+texture;
        item.partX = partX;
        item.partY = partY;
        item.name = name;
        item.sort = sort;
        item.type = type;
        item.weight = weight;
        item.cost = cost;

        item.damageReduction = damageReduction/100.0;
        item.staminaDamageReduction = staminaDamageReduction/100.0;
        items.put(id,item);
    }

    //Руны
    public static void loadItem(String id, String texture, float partX, float partY,String name, int sort, int type,int weight,int cost, int whatToRaise, double raiseMult, int staminaNeed, int difficult, String desc, String label){
        Item item = new Item();
        item.texture = P_ITEMS_TEXTURES_PATH+texture;
        item.partX = partX;
        item.partY = partY;
        item.name = name;
        item.sort = sort;
        item.type = type;
        item.weight = weight;
        item.cost = cost;
        item.difficult = difficult;
        item.whatToRaise = whatToRaise;
        item.raiseMult = raiseMult;
        item.staminaNeed = staminaNeed;
        item.label = label;
        item.desc = desc;

        items.put(id,item);
    }

    //Прочее
    public static void loadItem(String id, String texture, float partX, float partY,String name, int sort, int type,int weight,int cost, String desc){
        Item item = new Item();
        item.texture = P_ITEMS_TEXTURES_PATH+texture;
        item.partX = partX;
        item.partY = partY;
        item.name = name;
        item.sort = sort;
        item.type = type;
        item.weight = weight;
        item.cost = cost;

        item.desc = desc;
        items.put(id,item);
    }

    //Талисман
    public static void loadItem(String id, String texture, float partX, float partY,String name, int sort, int type,int weight,int cost, int effectID, float effectPower, int effectTime, String desc){
        Item item = new Item();
        item.texture = P_ITEMS_TEXTURES_PATH+texture;
        item.partX = partX;
        item.partY = partY;
        item.name = name;
        item.sort = sort;
        item.type = type;
        item.weight = weight;
        item.cost = cost;

        item.desc = desc;
        item.effectID = effectID;
        item.effectPower = effectPower;
        item.effectTime = effectTime;
        items.put(id,item);
    }

    //Расходник
    public static void loadItem(String id, String texture, float partX, float partY,String name, int sort, int type,int weight,int cost,int projectileID, int effectID, float effectPower, int effectTime, int ammo,int distant,int radius, String desc){
        Item item = new Item();
        item.texture = P_ITEMS_TEXTURES_PATH+texture;
        item.partX = partX;
        item.partY = partY;
        item.name = name;
        item.sort = sort;
        item.type = type;
        item.weight = weight;
        item.cost = cost;

        item.ammoMax = ammo;
        item.desc = desc;
        item.effectID = effectID;
        item.effectPower = effectPower;
        item.effectTime = effectTime;
        item.distant = distant;
        item.radius = radius;
        item.projectileID = projectileID;
        items.put(id,item);
    }
    //Оружие
    public static void loadItem(String id, String texture, float partX, float partY,String name, int sort, int type,int weight,int cost,double damage,int damageType,int interval, int ammo, int distant, int balanceDamage){
        Item item = new Item();
        item.texture = P_ITEMS_TEXTURES_PATH+texture;
        item.partX = partX;
        item.partY = partY;
        item.name = name;
        item.sort = sort;
        item.type = type;
        item.weight = weight;
        item.cost = cost;

        item.damage = damage;
        item.damageType = damageType;
        item.interval = interval;
        item.ammoMax = ammo;
        item.distant = distant;
        item.balanceDamage = balanceDamage;
        items.put(id,item);
    }

    public void putOnMap() {
        SpriteHelper.addSprite(sprite);
        Actor.addActorToBase(localID,this);

    }


    @Override
    public void init() {
        sprite = new Sprite(texture,locX,locY,I_ZORDER_ITEM,DEF_SIZE,DEF_SIZE,TILES_TOTAL*partX,TILES_TOTAL*partY,TILES_TOTAL*(partX+1),TILES_TOTAL*(partY+1),true,I_CAMERA_AFFECT_YES);
        SpriteHelper.addSprite(sprite);
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
        this.removeFromMap();
        StarterHelper.game.current.getItems().remove(this);
        Actor.removeActorFromBase(this.localID);
        StarterHelper.game.getInventory().add(this);
        Event.makeEvent(S_EVENT_PICK_UP + this.getName());
        MapChanges.changeMapPickup(localID);
    }

    public void removeFromMap() {
        SpriteHelper.removeSprite(sprite);
    }

    public void setSpritePos(int x, int y, int z, int sx, int sy) {
        sprite.setSizeX(sx);
        sprite.setSizeY(sy);
        sprite.setPos(x,y);
        sprite.zOrder = z;

    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setUpGeneral(String[][] data){
        data[0][1] = name;
        data[1][0] = S_INTERFACE_DESCRIBE_LEFT_TYPE;
        data[1][1] = classes[sort];
        data[2][0] = S_INTERFACE_DESCRIBE_LEFT_SUBTYPE;
        data[2][1] = subtypes[sort][type];
        data[3][0] = S_INTERFACE_DESCRIBE_LEFT_WEIGHT;
        data[3][1] = weight+"";
        data[4][0] = S_INTERFACE_DESCRIBE_LEFT_COST;
        data[4][1] = cost+"";
    }

    int[] sizes = {10,10,10,10,7,16,6,9,9,6};
    public String[][] getDescription() {
        String[][] data = new String[sizes[sort]][2];
        if(sort <=I_ITEM_SORT_RANGE){
            data[4+1][0] = S_INTERFACE_DESCRIBE_LEFT_DAMAGE;
            data[4+1][1] = (int)damage+"";
            data[5+1][0] = S_INTERFACE_DESCRIBE_LEFT_DAMAGETYPE;
            data[5+1][1] = SM_WEAPON_DAMAGE_TYPES[damageType];
            data[6+1][0] = S_INTERFACE_DESCRIBE_LEFT_INTERVAL;
            data[6+1][1] = interval+"";
            data[7+1][0] = S_INTERFACE_DESCRIBE_LEFT_AMMO;
            data[7+1][1] = ammoMax == -1 ? "-" : ammoMax+"";
            data[8+1][0] = S_INTERFACE_DESCRIBE_LEFT_DISTANT;
            data[8+1][1] = (distant)+"";
        }else if (sort == I_ITEM_SORT_SHIELDS){
            data[4+1][0] = S_INTERFACE_DESCRIBE_LEFT_DEFENSE;
            data[4+1][1] = (int)(damageReduction*100)+"";
            data[5+1][0] = S_INTERFACE_DESCRIBE_LEFT_STABILITY;
            data[5+1][1] = (int)(staminaDamageReduction*100)+"";
        }else if (sort == I_ITEM_SORT_ARMOURS){
            for (int i = 0; i < SM_ITEMS_TYPES_DEFENSES.length;i++){
                data[5+i][0] = SM_ITEMS_TYPES_DEFENSES[i];
                data[5+i][1] = defenses[i]+"";
            }
        }else if (sort == I_ITEM_SORT_TALISMANS){
            data[5][0] = S_INTERFACE_DESCRIBE_LEFT_DESCRIPTION;
            data[5][1] = desc;
        }else if (sort == I_ITEM_SORT_USES){
            data[4+1][0] = S_INTERFACE_DESCRIBE_LEFT_AMMO;
            data[4+1][1] = ammoMax == -1 ? "-" : ammoMax+"";
            data[5+1][0] = S_INTERFACE_DESCRIBE_LEFT_DISTANT;
            data[5+1][1] = distant == 0 ? "-" : Math.abs(distant)+"";
            data[6+1][0] = S_INTERFACE_DESCRIBE_LEFT_RADIUS;
            data[6+1][1] = radius == 0 ? "-" : radius+"";
            data[7+1][0] = S_INTERFACE_DESCRIBE_LEFT_DESCRIPTION;
            data[7+1][1] = desc;
        } else if (sort == I_ITEM_SORT_RUNES){
            data[4+1][0] = S_INTERFACE_DESCRIBE_LEFT_WHAT_TO_RAISE;
            data[4+1][1] = SM_ITEMS_TYPES_WHAT_TO_RAISE[whatToRaise];
            data[5+1][0] = S_INTERFACE_DESCRIBE_LEFT_STAMINA;
            data[5+1][1] = staminaNeed+"";
            data[6+1][0] = S_INTERFACE_DESCRIBE_LEFT_DIFFICULT;
            data[6+1][1] = difficult+"";
            data[7+1][0] = S_INTERFACE_DESCRIBE_LEFT_DESCRIPTION;
            data[7+1][1] = desc;
        }else if (sort == I_ITEM_SORT_MISC){
            data[5][0] = S_INTERFACE_DESCRIBE_LEFT_DESCRIPTION;
            data[5][1] = desc;
        }
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
