package game.general;

import engine.core.Mathp;
import engine.input.MainInput;
import engine.window.Actor;
import engine.window.Window;
import engine.helpers.*;
import game.inter.Simple;
import game.magic.Spell;
import game.players.*;
import game.story.Quest;
import game.world.map.Map;
import game.world.map.MapChanges;
import game.world.map.ResortArea;
import game.world.specialObjects.*;
import game.world.specialObjects.Item;
import game.world.decals.Decals;
import screens.inGame.LoadingScreen;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import static engine._SetsStrings.*;

public class Game implements Serializable {
    public String saveName = "авто";

    @Serial
    private static final long serialVersionUID = 1L;
    public boolean inResort;
    public boolean mapChanged;
    public boolean changedWeapon = true;
    public boolean changedShield = true;
    public boolean changedItem = true;
    public boolean changedSpell1 = true;
    public boolean changedSpell2 = true;
    boolean isInBattle = false;
    public double visionMult;
    public HashMap<String, MapChanges> mapChanges = new HashMap<>();
    HashMap<String,ResortArea> activatedAreas = new HashMap<>();
    int[] lastResortPos = {I_FIRST_LAST_RESORT_LOC_X,I_FIRST_LAST_RESORT_LOC_Y};
    List<Player> players = new ArrayList<>();
    List<Player> onDead = new ArrayList<>();
    public List<Quest> activeQuests = new ArrayList<>();
    List<Quest> completedQuests = new ArrayList<>();
    List<Item> inventory = new ArrayList<>();
    List<Spell> spellbook = new ArrayList<>();
    public int gold = 999999;
    public int time = I_GAME_TIME_MAX/2;
    public int timeMax = I_GAME_TIME_MAX;
    public Map current;
    Player player = new Player("Player1");
    public boolean mainPlayerKilled;

    public Game(){

    }

    public void addActivatedResort(){
        ResortArea closest = current.getClosestResortArea(player);
        ResortArea extend = new ResortArea(closest,current.getName(),current.getID(),current.getMiniMap());
        activatedAreas.put(extend.mapID+extend.ID,extend);
    }

    public void loadMap(String id) {
        MapChanges changes = MapChanges.getChanges(id);

        current = new Map(id,changes);


        player.script = "";
        player.visionDistant = 12*32;
    }

    public void spawnPlayer(String localID,Player player) {
        players.add(player);
        Actor.addActorToBase(localID,player);
    }

    public void logic(){
        inResort = current.inResort(player);
        Quest.mainControl(activeQuests,completedQuests,current.getID());
        players.sort(Comparator.comparingDouble(o -> Mathp.rast(o.getX(), o.getY(), player.getX(), player.getY())));
        boolean noOneAttackPlayer = true;
        for(Player el : players){
            double rastToPlayer = Mathp.rast(el.getX(),el.getY(),player.getX(),player.getY());
            if(!el.equals(player))
                el.setVisible(rastToPlayer < player.getVisionDistant() && current.noObstacle(el.getX(),el.getY(),player.getX(),player.getY()));
            el.logic();
            if(rastToPlayer < I_GAME_AI_RAST) {
                if (!el.equals(player)) {
                    AutoControl.control(el);
                    if(el.targetAttack != null && el.targetAttack.equals(player)){
                        noOneAttackPlayer = false;
                    }
                }
            }
        }
        if(player.health > 0) {
            setInBattle(!noOneAttackPlayer);
        }
        current.logic();
        time+=1;
        if(time > timeMax){
            time = 0;
        }
        if(!AudioHelper.isMusicPlaying() && player.health > 0){
            String musicToPlay = isInBattle ? current.getBallteMusic() : current.getExploreMusic();
            AudioHelper.playMusic(musicToPlay);
        }
        for(int i = 0; i < onDead.size();i++){
            AudioHelper.actorVoice(onDead.get(i));
            if(!onDead.get(i).voice.isPlaying()){
                onDead.remove(i);
                i--;
            }
        }
        for(Player el : players){
            el.clearStates();
        }
        players.removeAll(killedPlayers);
        killedPlayers.clear();
    }

    public void setInBattle(boolean inBattle){
        if(inBattle != isInBattle){
            AudioHelper.generalFadeMusicControl(inBattle);
            isInBattle = inBattle;
        }
    }

    public void input(MainInput input) {

        ManualControl.control(input,player);
    }

    public void changeMap(String mapID, int X, int Y) {
        if(current != null) {
            clear();
        }
        for(Player el : players){
            el.dispose();
        }
        ScreenHelper.getScreenActive().dispose();
        ScreenHelper.setScreenActive(new LoadingScreen(mapID, X,Y));
        //loadMap(mapID);
        //current.load();
        //StarterHelper.game.getPlayer().init();
        //StarterHelper.game.getPlayer().putOnWorld("player");
        player.setPos(X,Y);
        CameraHelper.setPos(-(int)X,-(int)Y);

    }

    public void changeMap(String mapID) {
        clear();
        loadMap(mapID);
    }

    public double[] testCollide(double x, double y, double speedX, double speedY) {
        return current.testCollide(x,y,speedX,speedY);
    }

    public List<Actor> scanForActivators() {
        List<Actor> onActive = new ArrayList<>();
        for(Item el : current.getItems()){
            double rast = Mathp.rast(el.getX(),el.getY(),player.getX(),player.getY());
            if(rast < I_GAME_ITEM_ACTIVATE_RAST){
                onActive.add(el);
            }
        }
        for(Door el : current.getDoors()){
            double rast = Mathp.rast(el.getX(),el.getY(),player.getX(),player.getY());
            if(el.canClose() || el.getState()) {
                if (rast < I_GAME_OBJECTS_ACTIVATE_RAST) {
                    onActive.add(el);
                }
            }
        }
        for(Chest el : current.getChests()){
            double rast = Mathp.rast(el.getX(),el.getY(),player.getX(),player.getY());
            if((rast < I_GAME_OBJECTS_ACTIVATE_RAST) && el.getState()){
                onActive.add(el);
            }
        }
        for(Activator el : current.getActivators()){
            double rast = Mathp.rast(el.getX(),el.getY(),player.getX(),player.getY());
            if(rast < I_GAME_OBJECTS_ACTIVATE_RAST && ! el.isFloored() && el.getState()){
                onActive.add(el);
            }
        }
        for(Player el : getPlayers()){
            double rast = Mathp.rast(el.getX(),el.getY(),player.getX(),player.getY());
            if(rast < I_GAME_OBJECTS_ACTIVATE_RAST && el.playerStatus == I_GAME_PLAYER_STATUS_FRIEND){
                onActive.add(el);
            }
        }
        return onActive;
    }

    public void activate(Actor activate) {
        activate.use();
    }

    public Player getPlayer() {
        return player;
    }

    public void setPass(double x, double y, float val) {
        current.setPass(x,y,val);
    }

    public List<Item> getInventory() {
        return inventory;
    }

    public List<Player> getPlayers() {
        //players.sort(Comparator.comparingDouble(o -> Mathp.rast(StarterHelper.game.getPlayer().getX(), StarterHelper.game.getPlayer().getY(), o.getX(), o.getY())));
        return players;
    }

    public String getEvent() {
        return Event.getLastEvent();
    }

    public void setTime(Simple timeCircle, Simple dayAndNight) {
        double timeOtn = (double)(time)/(double) timeMax;
        timeCircle.setAgnle(Math.toRadians(360*timeOtn+45));
        int hour = time / 3601;
        float part = time%3601f/3601f;
        double[] curTime = DM_GAME_TIMECYCLE[hour];
        double[] nextTime = DM_GAME_TIMECYCLE[hour == 23 ? 1 : hour+1];
        double[] countTime = {
                curTime[0]+(nextTime[0] - curTime[0])*part,
                curTime[1]+(nextTime[1] - curTime[1])*part,
                curTime[2]+(nextTime[2] - curTime[2])*part,
                curTime[3]+(nextTime[3] - curTime[3])*part,
        };
        visionMult = curTime[4];
        dayAndNight.setColor((float) countTime[0],(float)countTime[1],(float)countTime[2],(float)countTime[3]);
    }

    public List<Spell> getSpellbook() {
        return spellbook;
    }

    public void clear() {
        current.clear();
        for(Player el : players){
            el.dispose();
        }
        player.dispose();
        players.clear();
    }

    public void load(boolean load){
        for(Player el : players){
            el.init();
        }
        current.load(load);
        mapChanged = true;
        changedWeapon = true;
        changedShield = true;
        changedItem = true;
        changedSpell1 = true;
        changedSpell2 = true;
    }
    public void load(){
        for(Player el : players){
            el.init();
        }
        current.load(true);
        mapChanged = true;
        changedWeapon = true;
        changedShield = true;
        changedItem = true;
        changedSpell1 = true;
        changedSpell2 = true;
    }
    public List<SoundSource> getSounds() {
        return current.getSounds();
    }

    List<Player> killedPlayers = new ArrayList<>();
    public void killPlayer(Player player) {
        if(Window.getSets().showDecals == 1) {
            Decals blood = new Decals(player.blood, player.getX(), player.getY(), I_GAME_BLOOD_SLAIN_SIZE);
            current.addDecal(blood);
        }
        if(player.equals(getPlayer())){
            if(!mainPlayerKilled) {
                mainPlayerKilled = true;
                player.say(S_PLAYER_SAY_DIED);
                StarterHelper.save();
                isInBattle = false;
            }
        } else {
            killedPlayers.add(player);
            player.dispose();
            addLootChest(player);
            player.say(S_PLAYER_SAY_DIED);
            onDead.add(player);
            Actor.removeActorFromBase(player.localID);
            if(player.oneTimer){
                MapChanges.changeMapKillOneTimer(player.localID);
            }
        }
    }

    private void addLootChest(Player player){
        Chest lootChest = new Chest(player);
        current.getChests().add(lootChest);
        lootChest.init();
        Actor.addActorToBase(lootChest.localID,lootChest);
        MapChanges.getChanges(current.getID()).addTemporalChest(lootChest.localID,lootChest);
    }

    public boolean testCollide(double locX, double locY) {
        return current.testCollide(locX,locY);
    }

    public void setQuestToMinimap(List<Simple> minimapTasks) {
        Quest.setMinimapsBlips(minimapTasks,activeQuests,current.getID());
    }

    public List<Quest> getQuests() {
        List<Quest> quests = new ArrayList<>();
        for(Quest el : activeQuests){
            if(el.mapID.equals(current.getID())){
                quests.add(el);
            }
        }
        for(Quest el : completedQuests){
            if(el.mapID.equals(current.getID())){
                quests.add(el);
            }
        }
        return quests;
    }

    public void restart(boolean dead) {
        if (dead) {
            player.setPos( lastResortPos[0],  lastResortPos[1]);
            mainPlayerKilled = false;
        } else {
            ResortArea resortArea = current.getClosestResortArea(player);
            lastResortPos[0] = (int) resortArea.locX;
            lastResortPos[1] = (int) resortArea.locY;
        }
        player.revive();
        for(Item el : inventory){
            if(el.sort == I_ITEM_SORT_USES || el.sort == I_ITEM_SORT_RANGE){
                el.ammo = el.ammoMax;
            }
        }
        current.restart();

    }

    public boolean isItemInInventory(String ele) {
        for(Item el : inventory){
            if(el.id.equals(ele)){
                return true;
            }
        }
        return false;
    }

    public List<ResortArea> getResorts() {
        List<ResortArea> add = new ArrayList<>();
        for(ResortArea el : activatedAreas.values()){
            if(el.mapID.equals(current.getID())){
                add.add(el);
            }
        }
        return add;
    }

    public void doStuff(String var, String params) {
        String[] split = params.split(";");
        switch (var) {
            case "setMusic" -> AudioHelper.playMusic("music/"+split[0]);
        }
    }

    public float getStuff(Actor caller, String params) {
        String[] split = params.split(";");
        String var = split[0];
        if ("getDistToPoint".equals(var)) {
            double x = Integer.parseInt(split[1]);
            double y = Integer.parseInt(split[2]);
            return (float) Mathp.rast(caller.locX, caller.locY,x,y);
        }
        return 0;
    }
}
