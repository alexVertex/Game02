package game.players;

import engine.core.Mathp;
import engine.helpers.*;
import engine.input.MainInput;
import engine.render.Sprite;
import engine.script.Script;
import engine.script.ScriptMain;
import engine.window.Actor;
import game.general.Event;
import game.magic.Effect;
import game.magic.Spell;
import game.world.ammos.Ammo;
import game.world.specialObjects.Item;
import screens.dialogs.Dialog;
import screens.inGame.InGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static engine._SetsStrings.*;
import static engine._TextString.*;

public class Player extends Actor {

    public double[] tempTar = null;
    public double[] finishTar = null;

    public boolean[] deals = {false,false,false,false};
    public String buyMerch, buySpells;
    List<int[]> path = null;
    public boolean oneTimer = false;
    public static HashMap<String, Player> playersData = new HashMap<>();
    public double shieldColorRed;
    public double shieldColorBlue;
    public int playerStatus = 0;
    public int losedControl = 0;
    public double losedControlSpeed = 0;
    public double losedControlAngle;
    public List<Effect> effectList = new ArrayList<>();
    public List<String> loot = new ArrayList<>();
    public double[] stepsColor = {0,0,0};
    public AIpackage AIMain;
    public boolean onHit;
    public boolean onGuard;
    public float stepTime = 0;
    public boolean leftStep = false;

    public int hitProgress;
    public boolean visible = true;
    public String name;
    public int AIMonent = I_AI_MOMENT_NORMAL;
    public int searchTime;
    public double lastSeenX,lastSeenY;
    public int alarmedTimer;
    public String ID, texture, voiceSource = "heroMain", fraction;
    public double moveSpeed;
    public int visionDistant;
    public int goldMin,goldMax;
    public int fearedTime;
    public Player targetAttack;
    public String blood = "redBlood";
    public String lootID = "loot";
    public String steps = "steps";
    public String attackAnim = S_TEXTURE_ATTACK_DEF;

    public int coloredStepTime;

    int[] defenses = new int[11];
    int[] knowledge = new int[3];

    double moveMode = 1;
    public float health = 100, healthMax = 100;
    public float armor = 100, armorMax = 100;
    public float poise = 20, poiseMax = 20;
    public int arms = 110;

    public float magicCost = 120.0f;
    public int speed = 16;
    public float staminaMax = 100;
    public float stamina = 100;
    public int loadMax = 100;
    public int load = 0;
    double recoveryProgress = 1;

    public float weaponOneDamage = 14;
    public int weaponOneDamageType = 3;
    public int weaponOneAmmo = -1;
    public float weaponTwoDamage = 14;
    public int weaponTwoDamageType = 3;
    public int weaponTwoAmmo = -1;

    public Item[] armaments = {null,null,null,null,null,null,null,null,null,null,null,null};
    public Spell[] spell1 = {null,null,null,null,null,null};
    public Spell[] spell2 = {null,null,null,null,null,null};

    public float trade = 50.0f;
    Item curWeapon = null;
    Item curShield = null;
    public int cursorItem =0, cursorSpell1 = 0, cursorSpell2 = 0;
    public Item curItem = null;
    public Spell curSpell1 = null;
    public Spell curSpell2 = null;
    public double movedRast = 0;

    public double staminaOtn(){
        return stamina/staminaMax;
    }
    @Override
    public void hide() {
        sprite.alpha = 0;
        attackProj.alpha = 0;
        guardProj.alpha = 0;
    }

    @Override
    public void show() {
        sprite.alpha = 1;
        attackProj.alpha = 1;
        guardProj.alpha = 1;
    }

    @Override
    public void use() {
        InGame cur = (InGame) ScreenHelper.getScreenActive();
        cur.secondary = new Dialog(this,cur);
    }

    Sprite sprite, attackProj, guardProj;

    double speedX, speedY;
    int partX = 1, partY = 0;
    float walkProgress;

    public Player(){}
    public Player(String texture){

        this.texture = texture;
    }

    public void setCut(int X, int Y){
        sprite.leftTex = X * F_PLAYER_TEXTURE_PART;
        sprite.rightTex = (X+1) * F_PLAYER_TEXTURE_PART;
        sprite.topTex = Y * F_PLAYER_TEXTURE_PART;
        sprite.botTex = (Y+1) * F_PLAYER_TEXTURE_PART;
        sprite.replaceModelTID();
    }

    public void setCutX(int X){
        sprite.leftTex = X * F_PLAYER_TEXTURE_PART;
        sprite.rightTex = (X+1) * F_PLAYER_TEXTURE_PART;
        sprite.replaceModelTID();
    }

    public void setCutY(int Y){
        sprite.topTex = Y * F_PLAYER_TEXTURE_PART;
        sprite.botTex = (Y+1) * F_PLAYER_TEXTURE_PART;
        sprite.replaceModelTID();
    }

    public void putOnWorld(String localID){
        StarterHelper.game.spawnPlayer(localID,this);
        SpriteHelper.addSprite(sprite);
        SpriteHelper.addSprite(attackProj);
        SpriteHelper.addSprite(guardProj);
    }

    public Sprite getSprite() {
        return sprite;
    }

    public float getVisionDistant(){
        return (float) (StarterHelper.game.visionMult*visionDistant);
    }

    public void setVisionDistant(int value){
        visionDistant = value;
    }
    public void logic(){
        Battle.poiseControl(this);
        Battle.hitLogic(this);
        Moving.move(this);
        if(onGuard){
            guardProj.setSizeX(40);
            if(shieldColorRed > 0){
                shieldColorRed -= D_SHIELD_AFTER_SHIELD_COLOR_PROGRESS;
                shieldColorBlue += D_SHIELD_AFTER_SHIELD_COLOR_PROGRESS;
            }
            guardProj.red = (float) shieldColorRed;
            guardProj.blue = (float) shieldColorBlue;
            guardProj.green = 0;
        } else {
            //guardProj.setSizeX(0);
            shieldColorRed = 0;
            shieldColorBlue = 1;
        }
        Effect.continues(this);
        if(!script.equals("")) {
            Script get = ScriptMain.getScript(script);
            get.execute(this);
        }
        AudioHelper.actorVoice(this);
    }

    public void setMove(double speedX, double speedY) {
        this.speedX += speedX;
        this.speedY += speedY;
    }

    public void setLook(double X, double Y) {
        if (losedControl > 0)
            return;
        sprite.setAngle(Math.atan2(-Y + locY, X - locX) - 1.57f);
    }

    @Override
    public void init() {
        sprite = new Sprite(P_DATA_CHARS_TEXTURES_PATH+texture,0,0,I_ZORDER_PLAYER,I_ACTOR_DEF_SIZE,I_ACTOR_DEF_SIZE,I_CAMERA_AFFECT_YES);
        attackProj = new Sprite(attackAnim,0,20,I_ZORDER_ATTACK_PROJ,16,16,I_CAMERA_AFFECT_YES);
        guardProj = new Sprite("guard",0,0,I_ZORDER_GUARD_PROJ,40,40,I_CAMERA_AFFECT_YES);
        attackProj.setSizeX(0);
        setCut(partX,partY);
        SpriteHelper.addSprite(sprite);
        SpriteHelper.addSprite(attackProj);
        SpriteHelper.addSprite(guardProj);
    }

    @Override
    public void dispose() {
        SpriteHelper.removeSprite(sprite);
        SpriteHelper.removeSprite(attackProj);
        SpriteHelper.removeSprite(guardProj);
    }

    public void setMoveMode(double moveMod) {
        this.moveMode = moveMod;
    }

    public Item dressItem(int index, Item item){
        return Equip.dressItem(this,index,item);
    }

    public Spell dressSpell(int row, int index, Spell item){
        return Equip.dressSpell(this,row,index,item);
    }

    public double loadOtn() {
        return (float)load/(float) loadMax;
    }
    public double healthOtn() {
        return health/ healthMax;
    }

    public String getLoadText() {
        return load + "/"+loadMax;
    }

    public String getHealthText() {
        return (int)health + "/"+(int)healthMax;
    }
    public String getArmorText() {
        return (int)armor + "/"+(int)armorMax;
    }

    public String getStaminaText() {
        return (int)stamina + "/"+(int)staminaMax;
    }

    public String getPoiseText() {
        return (int)poiseMax+"";
    }

    public String getSpeedText() {
        return speed+"";
    }

    public String getMagicCost() {
        return (int)magicCost+"";
    }

    public String getArmsText() {
        return arms+"";
    }

    public int getArms() {
        return arms;
    }

    public String getTrade() {
        return (int)trade+"";
    }

    String[] damageTypes = {"Колющий","Режущий","Рубящий","Дробящий"};
    public String getWeaponDamage1() {
        return (int)weaponOneDamage+"";
    }
    public String getWeaponDamage2() {
        return (int)weaponTwoDamage+"";
    }
    public String getWeaponTypeDamage1() { return damageTypes[weaponOneDamageType]; }
    public String getWeaponTypeDamage2() {
        return damageTypes[weaponTwoDamageType];
    }
    public String getWeaponAmmo1() {
        return weaponOneAmmo == I_ITEM_NO_USE_AMMO ? "-": weaponOneAmmo+"";
    }
    public String getWeaponAmmo2() {
        return weaponTwoAmmo == I_ITEM_NO_USE_AMMO ? "-": weaponTwoAmmo+"";
    }
    public int getKnowledge(int index) {return knowledge[index];}
    public int getDefense(int i) {
        return defenses[i];
    }

    public void setPos(int x, int y) {
        locX = x;
        locY = y;
        sprite.setPos(locX,locY);
    }

    public Item getCurWeapon() {
        return curWeapon;
    }

    public Item getCurShield() {
        return curShield;
    }

    public double getAngle() {
        return sprite.getAngle()+1.57;
    }

    public Sprite getProj() {
        return attackProj;
    }

    public void death() {
        StarterHelper.game.killPlayer(this);
    }

    public double getDamage() {
        return weaponOneDamage;
    }

    public Sprite getGuard() {
        return guardProj;
    }

    public int getDamageType() {
        return weaponOneDamageType;
    }

    public double getWeaponInterval() {
        return curWeapon == null ? I_PLAYER_DEFAULT_INTERVAL : curWeapon.interval;
    }

    public int getAmmo() {
        return curWeapon == null ? I_PLAYER_DEFAULT_AMMO : curWeapon.ammo;
    }

    public void lossAmmo() {
        curWeapon.ammo--;
    }

    public double armorOtn() {
        return armor/ armorMax;
    }

    public void addEffect(Effect effect){
        effect.wearOn(this, effect.owner,true);
    }

    public void switchItem(int i) {
        Equip.switchItem(this,i);
    }

    public boolean mustSay = false;
    public String sayLine = "";
    public void say(String status) {
        if(voice != null && status.equals(S_PLAYER_SAY_DIED)){
            voice.stop();
        }
        int variant = Mathp.random(I_PLAYER_VOICE_VARIANT_MIN,I_PLAYER_VOICE_VARIANT_MAX);
        sayLine = P_PLAYER_VOICE_PATH+voiceSource+"/"+status+"/"+variant;
        mustSay = true;
    }

    public void clearStates() {
        onGuard = false;
    }

    public double getWeaponWeight() {
        return curWeapon == null ? 2 : curWeapon.weight;
    }
    public double getWeaponDist() {
        return curWeapon == null ? 20 : curWeapon.distant;
    }
    public void staminaLoss(double minus) {
        stamina -= minus;
        if(stamina < 0){
            stamina = 0;
            recoveryProgress = D_PLAYER_STAMINA_RECOVERY_WAIT_AFTER_DROUGHT;
        } else {
            recoveryProgress = D_PLAYER_STAMINA_RECOVERY_WAIT;
        }
    }

    public int getItemCharges(){
        return curItem == null ? I_ITEM_NO_USE_AMMO : curItem.ammo;
    }

    public void useItem() {
        if(losedControl > 0) return;
        if(curItem == null) return;
        if(curItem.ammo == 0) {
            Event.makeEvent(S_EVENT_NO_ITEM_AMMO);
            return;
        }
        if(recoveryProgress < 1) return;
        staminaLoss(0);
        curItem.ammo--;
        if(curItem.type == 0){
            addEffect(curItem.effect);
        } else {
            int dist = curItem.distant;
            if(dist < 0){
                double tarX = MainInput.getX()- CameraHelper.getX();
                double tarY = MainInput.getY()- CameraHelper.getY();
                dist = (int) Mathp.rast(getX(),getY(), tarX,tarY);
                if(dist > -curItem.distant){
                    dist = - curItem.distant;
                }
            }
            Ammo ammo1 = new Ammo(this, this.getX(), this.getY(), this.getAngle(), curItem.projectileID,curItem.effect,dist,curItem.radius);
            StarterHelper.game.current.addAmmo(ammo1);
        }
    }

    public void useSpell(boolean first) {
        if(losedControl > 0) return;
        Spell cur = first ? curSpell1 : curSpell2;
        if(cur == null) return;
        if(recoveryProgress < 1) return;
        double staminaNeed = cur.staminaNeed * magicCost/100;
        if(stamina < staminaNeed) {Event.makeEvent(S_EVENT_NO_STAMINA);return;}
        if(cur.sort <= I_SPELL_SORT_SPEECH && knowledge[cur.sort] < cur.difficult) {Event.makeEvent(S_EVENT_NO_KNOWLEDGE);return;}
        staminaLoss( staminaNeed);
        if(cur.targetType == 0){
            addEffect(cur.effect);
        } else {
            int dist = cur.distant;
            if(dist < 0){
                double tarX = MainInput.getX()- CameraHelper.getX();
                double tarY = MainInput.getY()- CameraHelper.getY();
                dist = (int) Mathp.rast(getX(),getY(), tarX,tarY);
                if(dist > -cur.distant){
                    dist = - cur.distant;
                }
            }
            Ammo ammo1 = new Ammo(this, this.getX(), this.getY(), this.getAngle(), cur.projectileID,cur.effect,dist,cur.radius);
            StarterHelper.game.current.addAmmo(ammo1);
        }
    }

    public void revive() {
        health = healthMax;
        stamina = staminaMax;
        armor = armorMax;
        if(armaments[I_PLAYER_ARMAMENTS_WEAPON_ONE] != null && armaments[I_PLAYER_ARMAMENTS_WEAPON_ONE].sort == I_ITEM_SORT_RANGE){
            armaments[I_PLAYER_ARMAMENTS_WEAPON_ONE].ammo = armaments[I_PLAYER_ARMAMENTS_WEAPON_ONE].ammoMax;
        }
        if(armaments[I_PLAYER_ARMAMENTS_WEAPON_TWO] != null && armaments[I_PLAYER_ARMAMENTS_WEAPON_TWO].sort == I_ITEM_SORT_RANGE){
            armaments[I_PLAYER_ARMAMENTS_WEAPON_TWO].ammo = armaments[I_PLAYER_ARMAMENTS_WEAPON_TWO].ammoMax;
        }
        for(int i = I_PLAYER_ARMAMENTS_ITEM_1; i <= I_PLAYER_ARMAMENTS_ITEM_4;i++){
            if(armaments[i] != null){
                armaments[i].ammo = armaments[i].ammoMax;
            }
        }
        coloredStepTime = 0;
        stepsColor[0] = 0;stepsColor[1] = 0;stepsColor[2] = 0;
        attackProj.setSizeX(0);
        AutoControl.clear(this);
    }

    @Override
    public void doStuff(String command, String params) {
        String[] split = params.split(";");
        switch (command){
            case "setLoc":
                setPos(Integer.parseInt(split[0]),Integer.parseInt(split[1]));
                break;
        }
    }
    double baseShieldDamageReduction = D_PLAYER_DEFAULT_SHIELD_DAMAGE_REDUCTION;
    double baseShieldStability = D_PLAYER_DEFAULT_SHIELD_STABILITY;

    public double getShieldDefense() {
        return curShield == null ? baseShieldDamageReduction : curShield.damageReduction;
    }

    public double getShieldStability() {
        return curShield == null ? baseShieldStability : curShield.staminaDamageReduction;
    }

    public void setKnowledge(int i, int knowledgeVal) {
        knowledge[i] = knowledgeVal;
    }

    public void setArms(int armsVal) {
        arms = armsVal;
    }

    public double getBalanceDamage() {
        return curWeapon == null ? F_PLAYER_DEFAULT_BALANCE_DAMAGE : curWeapon.balanceDamage;
    }

    public void loseControl(double angleAttack,int loseControlTime) {
        say(S_PLAYER_SAY_HITED);
        losedControl = loseControlTime;
        losedControlAngle = angleAttack;
    }

    public void setVisible(boolean b) {
        visible = b;
        if(visible){
            show();
        } else {
            hide();
        }
    }
}
