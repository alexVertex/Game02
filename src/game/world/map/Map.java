package game.world.map;

import engine.audio.Audio;
import engine.core.Mathp;
import engine.helpers.AudioHelper;
import engine.helpers.SpriteHelper;
import engine.render.Sprite;
import engine.window.Actor;
import engine.helpers.StarterHelper;
import game.general.Event;
import game.general.LoaderRes;
import game.players.*;
import game.world.specialObjects.Item;
import game.world.specialObjects.*;
import game.world.ammos.Ammo;
import game.world.decals.Decals;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static engine._SetsStrings.*;
import static org.lwjgl.glfw.GLFW.glfwGetKey;

public class Map implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final String minimapTexture;
    String[] exploreMusic;
    String[] battleMusic;

    int[][] tiles;
    float[][] pass;
    transient List<Sprite> tileSprites = new ArrayList<>();

    String mapName;
    String mapID;
    int sizeX;
    int sizeY;

    List<Spawner> spawners = new ArrayList<>();
    List<Item> items = new ArrayList<>();
    List<AnimTile> animTiles = new ArrayList<>();
    List<Door> doors = new ArrayList<>();
    List<Structure> structures = new ArrayList<>();
    List<Chest> chests = new ArrayList<>();
    List<Activator> activators = new ArrayList<>();
    List<SoundSource> sounds = new ArrayList<>();
    List<Ammo> ammos = new ArrayList<>();
    List<Decals> decals = new ArrayList<>();
    List<ResortArea> resortAreas = new ArrayList<>();

    MapChanges changes;
    public Map(String id, MapChanges changes){
        this.changes = changes;
        List<String> mapData = LoaderRes.readMapFile(id);
        String[] metadata = mapData.get(0).split(";");
        mapName = metadata[1];
        mapID = metadata[0];
        minimapTexture = metadata[2];
        sizeX = Integer.parseInt((metadata[3]));
        sizeY = Integer.parseInt((metadata[4]));
        exploreMusic = metadata[13].split(",");
        battleMusic = metadata[14].split(",");
        for(int i = 0; i < exploreMusic.length;i++){
            exploreMusic[i] = P_MUSIC_PATH+exploreMusic[i];
            AudioHelper.preload(exploreMusic[i]);
        }
        for(int i = 0; i < battleMusic.length;i++){
            battleMusic[i] = P_MUSIC_PATH+battleMusic[i];
            AudioHelper.preload(battleMusic[i]);
        }

        tiles = new int[sizeX][sizeY];
        pass = new float[sizeX][sizeY];
        String[] tileData = mapData.get(1).split(";");
        int cur = 0;
        for(int i = 0; i < sizeX;i++){
            for(int j = 0; j < sizeY;j++){
                tiles[j][sizeY-i-1] = Integer.parseInt(tileData[cur++]);
                pass[j][sizeY-i-1] = Integer.parseInt(tileData[cur++]);
            }
        }

        String[] itemData = mapData.get(2).split(";");
        for(int i = 0; i < itemData.length;i+=4){
            addItem(itemData[i],itemData[i+1],Integer.parseInt(itemData[i+2])*I_ACTOR_DEF_SIZE,(sizeY-1)*I_ACTOR_DEF_SIZE-Integer.parseInt(itemData[i+3])*I_ACTOR_DEF_SIZE);
        }

        String[] activatorData = mapData.get(3).split(";");
        for(int i = 0; i < activatorData.length;i+=4){
            addActivator(activatorData[i],activatorData[i+1],Integer.parseInt(activatorData[i+2]),(sizeY-1)-Integer.parseInt(activatorData[i+3]));
        }

        String[] animData = mapData.get(4).split(";");
        for(int i = 0; i < animData.length;i+=4){
            addAnimTile(animData[i],animData[i+1],Integer.parseInt(animData[i+2])*I_ACTOR_DEF_SIZE,(sizeY-1)*I_ACTOR_DEF_SIZE-Integer.parseInt(animData[i+3])*I_ACTOR_DEF_SIZE);
        }

        for(Chest el : MapChanges.getChanges(mapID).temporalChests.values()){
            chests.add(el);
            el.init();
            Actor.addActorToBase(el.localID,el);
        }

        String[] chestData = mapData.get(5).split(";");
        for(int i = 0; i < chestData.length;i+=5){
            String[] lootData = chestData[i+4].split(",");
            int gold = Integer.parseInt(lootData[0]);
            int lootCount = Integer.parseInt(lootData[1]);
            List<Item> loot = new ArrayList<>();
            for(int j = 0; j < lootCount;j++){
                loot.add(new Item(lootData[2+j],"",0,0));
            }
            addChest(chestData[i],chestData[i+1],Integer.parseInt(chestData[i+2]),(sizeY-1)-Integer.parseInt(chestData[i+3]),loot,gold);
        }

        String[] doorData = mapData.get(6).split(";");
        for(int i = 0; i < doorData.length;i+=4){
            addDoor(doorData[i],doorData[i+1],Integer.parseInt(doorData[i+2]),(sizeY-1)-Integer.parseInt(doorData[i+3]),true);
        }
        String[] soundData = mapData.get(7).split(";");
        for(int i = 0; i < soundData.length;i+=4){
            addSoundSource(soundData[i],soundData[i+1],Integer.parseInt(soundData[i+2])*I_ACTOR_DEF_SIZE,(sizeY-1)*I_ACTOR_DEF_SIZE-Integer.parseInt(soundData[i+3])*I_ACTOR_DEF_SIZE);
        }
        String[] structuresData = mapData.get(8).split(";");
        for(int i = 0; i < structuresData.length;i+=4){
            addStructure(structuresData[i],structuresData[i+1],Integer.parseInt(structuresData[i+2])*I_ACTOR_DEF_SIZE,(sizeY-1)*I_ACTOR_DEF_SIZE-Integer.parseInt(structuresData[i+3])*I_ACTOR_DEF_SIZE);
        }

        String[] resortData = mapData.get(9).split(";");
        for(int i = 0; i < resortData.length;i+=5){
            resortAreas.add(new ResortArea(resortData[i],resortData[i+1],Integer.parseInt(resortData[i+2])*I_ACTOR_DEF_SIZE,(sizeY-1)*I_ACTOR_DEF_SIZE-Integer.parseInt(resortData[i+3])*I_ACTOR_DEF_SIZE,Integer.parseInt(resortData[i+4])*I_ACTOR_DEF_SIZE));
        }

        String[] playerData = mapData.get(10).split(";");
        for(int i = 0; i < playerData.length;i+=5){
            Player test = PlayerLoader.getPlayerByID(playerData[i]);
            test.localID = playerData[i+1];
            String[] AIdata = playerData[i+4].split(",");
            if(AIdata[0].equals("0")){
                test.AIMain = new AIpackage(Integer.parseInt(AIdata[1])*I_ACTOR_DEF_SIZE,Integer.parseInt(AIdata[3])*I_ACTOR_DEF_SIZE,(sizeY-1)*I_ACTOR_DEF_SIZE-Integer.parseInt(AIdata[2])*I_ACTOR_DEF_SIZE,(sizeY-1)*I_ACTOR_DEF_SIZE-Integer.parseInt(AIdata[4])*I_ACTOR_DEF_SIZE,Integer.parseInt(AIdata[5]),Integer.parseInt(AIdata[6]));
            } else {
                int countOfWayPoints = Integer.parseInt(AIdata[16]);
                int[][] points = new int[countOfWayPoints][3];
                for(int j = 0; j < countOfWayPoints; j++)
                {
                    points[j] = new int[]{ Integer.parseInt(AIdata[17 + j * 3])*I_ACTOR_DEF_SIZE, (sizeY-1)*I_ACTOR_DEF_SIZE-Integer.parseInt(AIdata[17 + j * 3 + 1])*I_ACTOR_DEF_SIZE, Integer.parseInt(AIdata[17 + j * 3 + 2])};
                }
                test.AIMain = new AIpackage(points);
            }
            for(int j = 0; j < 4;j++) {
                test.deals[j] = AIdata[7+j].equals("1");
            }
            boolean oneTimer = AIdata[ 11].equals("1");
            boolean enebled = AIdata[ 12].equals("1");

            test.buyMerch = AIdata[ 13];
            test.buySpells = AIdata[ 14];
            test.script = AIdata[15];
            Spawner spawner = new Spawner(test,Integer.parseInt(playerData[i+2])*32,(sizeY-1)*32-Integer.parseInt(playerData[i+3])*32,oneTimer,test.playerStatus);
            spawner.enabled = enebled;
            boolean killedOneTimer = changes.killedOneTimers.contains(spawner.localID);
            if(!killedOneTimer) {
                spawners.add(spawner);
            }
        }
        StarterHelper.game.mapChanged = true;



        PathFind.initNet(sizeX,sizeY);
    }

    public void firstSpawn(){
        for(Spawner el : spawners){
            el.spawn();
        }
    }

    public void addAmmo(Ammo ammo) {;
        ammos.add(ammo);
        ammo.init();
    }

    public void addDecal(Decals decal) {;
        decals.add(decal);
        decal.init();
    }

    private void addSoundSource(String id, String localID, double x, double y) {
        SoundSource activator = new SoundSource(id,localID,x,y);
        sounds.add(activator);
        Actor.addActorToBase(localID,activator);
    }

    private void addActivator(String id, String localID, double x, double y) {
        boolean active = changes.activated.contains(localID);

        Activator activator = new Activator(id,localID,x,y);
        if(active){
            activator.activate(99999);
        }
        activators.add(activator);
        activator.putOnMap();
    }

    public void addChest(String id, String localID, double x, double y,List<Item> content, int gold) {
        boolean active = changes.chestOpened.contains(localID);
        Chest item = new Chest(id,localID,x,y, content, gold);
        if(active){
            item.open();
        }
        chests.add(item);
        item.putOnMap();
    }

    private void addStructure(String id, String localID, double x, double y){
        Structure item = new Structure(id,localID,x,y);
        structures.add(item);
        item.putOnMap();
    }

    private void addDoor(String id, String localID, double x, double y, boolean isClosed) {
        Door item = new Door(id,localID,x,y,isClosed);
        if(isClosed){
            pass[(int) x][(int) y] = I_PASS_WALL;
        } else {
            pass[(int) x][(int) y] = I_PASS_FREE;
        }
        doors.add(item);
        item.putOnMap();
    }

    private void addAnimTile(String id, String localID, double x, double y) {
        AnimTile item = new AnimTile(id,localID,x,y);
        animTiles.add(item);
        item.putOnMap();
    }

    public void addItem(String id, String localID, double x, double y){
        if(changes.pickUpedItems.contains(localID)) return;
        Item item = new Item(id,localID,x,y);
        items.add(item);
        item.putOnMap();
    }

    public double[] testCollide(double x, double y, double speedX, double speedY) {

        double testX = x + speedX;
        double testY = y + speedY;

        if(testX < 0) speedX = 0;
        if(testX > sizeX*32) speedX = 0;
        if(testY < 0) speedY =  0;
        if(testY > sizeY*32) speedY = 0;
        double speedPush = 1;
        if(speedX > 0){
            int collide = collideState(testX,y,testX,y,31,0,31,31);
            if(collide > 0){
                speedX = 0;
                if(speedY < 1)
                {
                   if(collide == 2) speedY = speedPush;
                   if(collide == 3) speedY = -speedPush;
                }
            }
        }
        if(speedX < 0){
            int collide = collideState(testX,y,testX,y,0,0,0,31);
            if(collide >0){
                speedX = 0;
                if(speedY < 1)
                {
                    if(collide == 2) speedY = speedPush;
                    if(collide == 3) speedY = -speedPush;
                }
            }
        }
        if(speedY > 0){
            int collide = collideState(x,testY,x,testY,0,31,31,31);
            if(collide > 0){
                speedY = 0;
                if(speedX < 1)
                {
                   if(collide == 2) speedX = speedPush;
                   if(collide == 3) speedX = -speedPush;
                }
            }
        }
        if(speedY < 0){
            int collide = collideState(x,testY,x,testY,0,0,31,0);
            if(collide > 0){
                speedY = 0;
                if(speedX < 1)
                {
                    if(collide == 2) speedX = speedPush;
                    if(collide == 3) speedX = -speedPush;
                }
            }
        }
        return new double[]{speedX,speedY};
    }

    private int collideState(double x, double y,double x1, double y1,int addX1, int addY1,int addX2, int addY2){
        int i = (int) ((x+addX1)/32);
        int j = (int) ((y+addY1)/32);
        boolean stop = getPass(i,j) <= 0;
        i = (int) ((x1+addX2)/32);
        j = (int) ((y1+addY2)/32);
        boolean stop1 = getPass(i,j) <= 0;
        if(stop && stop1) return 1;
        if(stop) return 2;
        if(stop1) return 3;
        return 0;
    }

    public List<Item> getItems() {
        items.sort(Comparator.comparingDouble(o -> Mathp.rast(StarterHelper.game.getPlayer().getX(), StarterHelper.game.getPlayer().getY(), o.getX(), o.getY())));
        return items;
    }

    public void logic() {
        Player player = StarterHelper.game.getPlayer();
        for(AnimTile el : animTiles){
            double rastToPlayer = Mathp.rast(player.getX(),player.getY(),el.locX,el.locY);
            if(rastToPlayer < I_GAME_AI_RAST) {
                el.logic();
            }
        }
        for(Door el : doors){
            double rastToPlayer = Mathp.rast(player.getX(),player.getY(),el.locX,el.locY);
            if(rastToPlayer < I_GAME_AI_RAST) {
                el.logic();
            }
        }
        for(Structure el : structures){
            double rastToPlayer = Mathp.rast(player.getX(),player.getY(),el.locX,el.locY);
            if(rastToPlayer < I_GAME_AI_RAST) {
                el.logic(StarterHelper.game.getPlayer());
            }
        }
        for (int i = 0; i < chests.size(); i++) {
            Chest el = chests.get(i);
            double rastToPlayer = Mathp.rast(player.getX(),player.getY(),el.locX,el.locY);
            if(rastToPlayer < I_GAME_AI_RAST) {
                boolean delete = el.logic();
                if(delete){
                    chests.remove(i--);
                    el.dispose();
                    MapChanges.getChanges(getID()).removeTemporalChest(el.localID);
                }
            }
        }
        for(Activator el : activators){
            double rastToPlayer = Mathp.rast(player.getX(),player.getY(),el.locX,el.locY);
            if(rastToPlayer < I_GAME_AI_RAST) {
                el.logic();
            }
        }
        for(Ammo el : ammos){
            el.logic();
        }
        for(Decals el : decals){
            el.logic();
        }
        ammos.removeAll(removeAmmoList);
        removeAmmoList.clear();
        decals.removeAll(removeDecalsList);
        removeDecalsList.clear();
    }
    public List<Door> getDoors() {
        doors.sort(Comparator.comparingDouble(o -> Mathp.rast(StarterHelper.game.getPlayer().getX(), StarterHelper.game.getPlayer().getY(), o.getX(), o.getY())));
        return doors;
    }

    public void setPass(double x, double y, float val) {
        pass[(int) x][(int) y] = val;
    }

    public List<Chest> getChests() {
        chests.sort(Comparator.comparingDouble(o -> Mathp.rast(StarterHelper.game.getPlayer().getX(), StarterHelper.game.getPlayer().getY(), o.getX(), o.getY())));
        return chests;
    }

    public List<Activator> getActivators() {
        activators.sort(Comparator.comparingDouble(o -> Mathp.rast(StarterHelper.game.getPlayer().getX(), StarterHelper.game.getPlayer().getY(), o.getX(), o.getY())));
        return activators;
    }

    public void clear() {
        for(Sprite el : tileSprites){
            SpriteHelper.removeSprite(el);
        }
        tileSprites.clear();
        for(Item el : items){
            el.removeFromMap();
        }
        for(Structure el : structures){
            el.removeFromMap();
        }
        for(Door el : doors){
            el.removeFromMap();
        }
        for(AnimTile el : animTiles){
            el.removeFromMap();
        }for(Chest el : chests){
            el.removeFromMap();
        }
        for(Activator el : activators){
            el.removeFromMap();
        }
        for(SoundSource el : sounds){
            el.stop();
        }
        for(Ammo el : ammos){
            el.dispose();
        }
        for(Decals el : decals){
            el.dispose();
        }
    }

    public void load(boolean spawn){

        for(Item el : items){
            el.init();
        }
        for(Structure el : structures){
            el.init();
        }
        for(Door el : doors){
            el.init();
        }
        for(AnimTile el : animTiles){
            el.init();
        }for(Chest el : chests){
            el.init();
        }
        for(Activator el : activators){
            el.init();
        }
        for(SoundSource el : sounds){
            el.init();
        }
        for(Ammo el : ammos){
            el.init();
        }
        for(Decals el : decals){
            el.init();
        }
        tileSprites = new ArrayList<>();
        for(int i = 0; i < sizeX;i++){
            for(int j = 0; j < sizeY;j++){
                Sprite ad = (Tile.loadTile(tiles[j][sizeY-i-1],j,sizeY-i-1));
                ad.createModel();
                SpriteHelper.addSprite(ad);
                tileSprites.add(ad);
            }
        }
        if(spawn) {
            firstSpawn();
        }
    }

    public List<SoundSource> getSounds() {
        return sounds;
    }

    List<Ammo> removeAmmoList = new ArrayList<>();
    public void removeAmmo(Ammo ammo) {
        removeAmmoList.add(ammo);
        ammo.dispose();
    }
    List<Decals> removeDecalsList = new ArrayList<>();
    public void removeDecals(Decals decals) {
        removeDecalsList.add(decals);
        decals.dispose();
    }
    public boolean testCollide(double locX, double locY) {
        int i = (int) ((locX)/32);
        if(i < 0) return true;
        if(i >= pass.length) return true;
        int j = (int) ((locY)/32);
        if(j < 0) return true;
        if(j >= pass[0].length) return true;
        return pass[i][j] <= 0;
    }

    public boolean noObstacle(double x0, double y0, double x1, double y1) {
        boolean answer = true;
        boolean answer1 = true;

        double angle = Math.atan2(y1-y0,x1-x0);
        double angle1 = angle + Math.PI*0.5;
        double angle2 = angle - Math.PI*0.5;
        double offset = 16;
        int leftX0 = (int)(x0+Math.cos(angle1)*offset);
        int leftY0 = (int)(y0+Math.sin(angle1)*offset);
        int leftX1 = (int)(x1+Math.cos(angle1)*offset);
        int leftY1 = (int)(y1+Math.sin(angle1)*offset);

        int rightX0 = (int)(x0+Math.cos(angle2)*offset);
        int rightY0 = (int)(y0+Math.sin(angle2)*offset);
        int rightX1 = (int)(x1+Math.cos(angle2)*offset);
        int rightY1 = (int)(y1+Math.sin(angle2)*offset);

        angle1 = Math.atan2(leftY1-leftY0,leftX1-leftX0);
        double rast1 = Mathp.rast(leftX0,leftY0,leftX1,leftY1);
        for(int i = 0; i < rast1;i++){
            int x = (int) (leftX0 + Math.cos(angle1)*i)/32;
            int y = (int) (leftY0 + Math.sin(angle1)*i)/32;
            if(Event.eventChanged){
                //StarterHelper.game.current.addItem("shield1","22",x*32,y*32);
            }
            if (getPass(x,y) == I_PASS_WALL) {
                answer = false;
                break;
            }
        }

        angle2 = Math.atan2(rightY1-rightY0,rightX1-rightX0);
        double rast2 = Mathp.rast(rightX0,rightY0,rightX1,rightY1);
        for(int i = 0; i < rast2;i++){
            int x = (int) (rightX0 + Math.cos(angle2)*i)/32;
            int y = (int) (rightY0 + Math.sin(angle2)*i)/32;
            if(Event.eventChanged){
                //StarterHelper.game.current.addItem("bow1","22",x*32,y*32);
            }
            if (getPass(x,y) == I_PASS_WALL) {
                answer1 = false;
                break;
            }
        }
        return answer || answer1;
    }

    public boolean noObstacleWalk(double x0, double y0, double x1, double y1) {

        boolean answer = true;

        double angle = Math.atan2(y1-y0,x1-x0);
        double offset = 0;


        double rast1 = Mathp.rast(x0,y0,x1,y1);
        for(int i = 0; i < rast1;i++){
            int x = (int) (x0+ Math.cos(angle)*i);
            int y = (int) (y0+ Math.sin(angle)*i);
            if(x1 == 13*32 && y1 == 22*32){
               // StarterHelper.game.current.addItem("shield1","22",x,y);
            }
            int xLower = (int) Math.floor(x/32.0);
            int xUpper = (int) Math.ceil(x/32.0);
            int yLower = (int) Math.floor(y/32.0);
            int yUpper = (int) Math.ceil(y/32.0);
            int[] xes = {xLower,xUpper};
            int[] yes = {yLower,yUpper};
            for (int j = 0; j < 2;j++){
                for (int k = 0; k < 2;k++){
                    if (getPass(xes[j],yes[k]) == I_PASS_WALL) {
                        answer = false;
                        break;
                    }
                }
            }
            if(x1 == 17*32 && y1 == 18*32){
                //StarterHelper.game.current.addItem("bow1","22",xTest*32,yTest*32);
            }

        }


        return answer;


    }

    public double getWidth() {
        return sizeX *32;
    }
    public double getHeight() {
        return sizeY *32;
    }

    public double getPass(int i, int j){
        if(i < 0) return -1;
        if(j < 0) return -1;
        if(i >= pass.length) return -1;
        if(j >= pass[0].length) return -1;
        return pass[i][j];
    }

    public ResortArea getClosestResortArea(Player player) {
        resortAreas.sort(Comparator.comparingDouble(o -> Mathp.rast(player.getX(), player.getY(), o.locX, o.locY)));

        return resortAreas.get(0);
    }

    public void restart() {
        for(Spawner el : spawners){
            el.respawn();
        }
        for(Decals el : decals){
            el.dispose();
        }
        decals.clear();
        for(Ammo el : ammos){
            el.dispose();
        }
        ammos.clear();
    }

    public String getID() {
        return mapID;
    }

    public MapChanges getChanges() {
        return changes;
    }

    public String getBallteMusic() {
        return battleMusic[Mathp.random(0,battleMusic.length)];
    }

    public String getExploreMusic() {
        return exploreMusic[Mathp.random(0,exploreMusic.length)];
    }

    public String getMiniMap() {
        return minimapTexture;
    }

    public String getName() {
        return mapName;
    }

    public void addSprite(Sprite el){
        SpriteHelper.addSprite(el);
        tileSprites.add(el);
    }

    public void delSprite(Sprite el){
        SpriteHelper.removeSprite(el);
        tileSprites.remove(el);
    }

    public boolean inResort(Player player) {
        for(ResortArea el : resortAreas){
            if(Mathp.rast(el.locX,el.locY,player.getX(),player.getY())<el.rast){
                return true;
            }
        }
        return false;
    }
}
