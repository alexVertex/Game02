package game.world.map;

import engine.helpers.StarterHelper;
import game.world.specialObjects.Chest;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapChanges implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    List<String> pickUpedItems = new ArrayList<>();
    List<String> activated = new ArrayList<>();
    List<String> chestOpened = new ArrayList<>();
    List<String> killedOneTimers = new ArrayList<>();
    HashMap<String,String> merchChanged = new HashMap<>();
    HashMap<String, Chest> temporalChests = new HashMap<>();

    public void addTemporalChest(String id, Chest chest){
        temporalChests.put(id,chest);
    }



    public void removeTemporalChest(String id){
        temporalChests.remove(id);
    }

    public void itemPickedUp(String localID){
        pickUpedItems.add(localID);
    }

    public List<String> getItems() {
        return pickUpedItems;
    }

    public void activate(String localID) {
        activated.add(localID);
    }
    public List<String> getActivated() {
        return activated;
    }

    public void openChest(String localID) {
        chestOpened.add(localID);
    }
    public List<String> getChestOpened() {
        return chestOpened;
    }

    public void killOneTime(String localID) {
        killedOneTimers.add(localID);
    }
    public List<String> getKilledOneTimers() {
        return killedOneTimers;
    }

    public void changeMerch(String localID, String newMerch) {
        merchChanged.put(localID,newMerch);
    }
    public String getMerch(String id) {
        return merchChanged.get(id);
    }

    public void addMerch(String id, String newMerch) {
        String cur = merchChanged.get(id);
        cur += newMerch;
        merchChanged.put(id,cur);
    }

    private static MapChanges getChanges() {
        return getChanges(StarterHelper.game.current.getID());
    }

    public static MapChanges getChanges(String currentMap) {
        MapChanges get = StarterHelper.game.mapChanges.getOrDefault(currentMap,null);
        if(get == null){
            get = new MapChanges();
            StarterHelper.game.mapChanges.put(currentMap,get);
        }
        return get;
    }

    public static void changeMapActivator(String localID) {
        MapChanges changes = getChanges();
        changes.activate(localID);
    }

    public static void changeMapChest(String localID) {
        MapChanges changes = getChanges();
        changes.openChest(localID);
    }

    public static void changeMapPickup(String localID) {
        MapChanges changes = getChanges();
        changes.itemPickedUp(localID);
    }

    public static void changeMapKillOneTimer(String localID) {
        MapChanges changes = getChanges();
        changes.killOneTime(localID);
    }
}
