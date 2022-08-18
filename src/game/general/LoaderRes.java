package game.general;

import game.magic.Spell;
import game.players.Fraction;
import game.players.Merch;
import game.players.PlayerLoader;
import game.story.DialogData;
import game.story.Quest;
import game.world.map.Tile;
import game.world.specialObjects.Item;
import game.world.specialObjects.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static engine._SetsStrings.*;

public class LoaderRes {
    public static void loadAllRes(){
        loadItems();
        loadObjects();
        loadHistory();
        loadPlayers();
        loadTiles();
    }

    private static void loadTiles() {
        List<String> loadFile = readFile(P_DATA_TILES_PATH);
        Tile.loadData(loadFile);
    }

    private static void loadPlayers(){
        List<String> loadFile = readFile(P_DATA_PLAYER_FRACTION_PATH);
        Fraction.loadData(loadFile);
        loadFile = readFile(P_DATA_PLAYER_CHARS_PATH);
        for(String el : loadFile){
            PlayerLoader.loadPlayer(el);
        }
        loadFile = readFile(P_DATA_PLAYER_TRADES_PATH);
        for(String el : loadFile){
            Merch.loadMerch(el);
        }
    }

    private static void loadHistory() {
        final File folder = new File(P_DATA_DIALOGS_PATH);
        for (final File fileEntry : Objects.requireNonNull(folder.listFiles())) {
            List<String> loadFile = readFile(P_DATA_DIALOGS_PATH_ONLY+fileEntry.getName());
            DialogData.loadDialog(loadFile,fileEntry.getName().substring(0,fileEntry.getName().length()-4));
        }
        final File folder1 = new File(P_DATA_QUEST_PATH);
        for (final File fileEntry : Objects.requireNonNull(folder1.listFiles())) {
            List<String> loadFile = readFile(P_DATA_QUEST_PATH_ONLY+fileEntry.getName());
            Quest.LoadQuest(loadFile,fileEntry.getName().substring(0,fileEntry.getName().length()-4));
        }
    }

    private static void loadObjects() {
        List<String> data = readFile(P_DATA_DOORS_PATH);
        for (String el : data) {
            String[] split = el.split(";");
            Door.loadItem(split[0],split[1], split[2], Integer.parseInt(split[3]));
        }
        data = readFile(P_DATA_ACTIVATORS_PATH);
        for (String el : data) {
            String[] split = el.split(";");
            Activator.loadItem(split[0], split[1],split[2], Integer.parseInt(split[3]),Boolean.parseBoolean(split[4]),Integer.parseInt(split[5]));
        }
        data = readFile(P_DATA_ANIMTILES_PATH);
        for (String el : data) {
            String[] split = el.split(";");
            AnimTile.loadAnimTile(split[0], split[1],split[2], Float.parseFloat(split[3].replaceAll(",",".")),Integer.parseInt(split[4]));
        }
        data = readFile(P_DATA_CHESTS_PATH);
        for (String el : data) {
            String[] split = el.split(";");
            Chest.loadItem(split[0], split[1],split[2], Integer.parseInt(split[3]),Integer.parseInt(split[4]),Boolean.parseBoolean(split[5]));
        }
        data = readFile(P_DATA_SOUNDSOURCES_PATH);
        for (String el : data) {
            String[] split = el.split(";");
            SoundSource.loadItem(split[0], split[1],split[2], Integer.parseInt(split[3]));
        }
        data = readFile(P_DATA_STRUCTURES_PATH);
        for (String el : data) {
            String[] split = el.split(";");
            Structure.loadItem(split[0], split[1],split[2], Integer.parseInt(split[3]), Integer.parseInt(split[4]), Integer.parseInt(split[5]), Integer.parseInt(split[6]), Integer.parseInt(split[7]));
        }
    }

    private static void loadItems() {
        List<String> data = readFile(P_DATA_WEAPONS_PATH);
        for (String el : data) {
            String[] split = el.split(";");
            Item.loadItem(split[0], split[1], Integer.parseInt(split[2]), Integer.parseInt(split[3]), split[4], Integer.parseInt(split[5]), Integer.parseInt(split[6]), Integer.parseInt(split[7]), Integer.parseInt(split[8])
                    , Integer.parseInt(split[9]), Integer.parseInt(split[10]), Integer.parseInt(split[11]), Integer.parseInt(split[12]), Integer.parseInt(split[13]), Integer.parseInt(split[14]));
        }
        data = readFile(P_DATA_SHIELDS_PATH);
        for (String el : data) {
            String[] split = el.split(";");
            Item.loadItem(split[0], split[1], Integer.parseInt(split[2]), Integer.parseInt(split[3]), split[4], Integer.parseInt(split[5]), Integer.parseInt(split[6]), Integer.parseInt(split[7]), Integer.parseInt(split[8])
                    , Double.parseDouble(split[9]), Double.parseDouble(split[10]));
        }
        data = readFile(P_DATA_ARMORS_PATH);
        for (String el : data) {
            String[] split = el.split(";");
            Item.loadItem(split[0], split[1], Integer.parseInt(split[2]), Integer.parseInt(split[3]), split[4], Integer.parseInt(split[5]), Integer.parseInt(split[6]), Integer.parseInt(split[7]), Integer.parseInt(split[8])
                    , Integer.parseInt(split[9]), Integer.parseInt(split[10]), Integer.parseInt(split[11]), Integer.parseInt(split[12]), Integer.parseInt(split[13]), Integer.parseInt(split[14]), Integer.parseInt(split[15]), Integer.parseInt(split[16]), Integer.parseInt(split[17]), Integer.parseInt(split[18]), Integer.parseInt(split[19]));
        }
        data = readFile(P_DATA_TALISMANS_PATH);
        for (String el : data) {
            String[] split = el.split(";");
            Item.loadItem(split[0], split[1], Integer.parseInt(split[2]), Integer.parseInt(split[3]), split[4], Integer.parseInt(split[5]), Integer.parseInt(split[6]), Integer.parseInt(split[7]), Integer.parseInt(split[8])
                    , Integer.parseInt(split[9]), Integer.parseInt(split[10]), Integer.parseInt(split[11]), split[12]);
        }
        data = readFile(P_DATA_USES_PATH);
        for (String el : data) {
            String[] split = el.split(";");
            Item.loadItem(split[0], split[1], Integer.parseInt(split[2]), Integer.parseInt(split[3]), split[4], Integer.parseInt(split[5]), Integer.parseInt(split[6]), Integer.parseInt(split[7]), Integer.parseInt(split[8])
                    , Integer.parseInt(split[9]), Integer.parseInt(split[10]), Integer.parseInt(split[11]), Integer.parseInt(split[12]), Integer.parseInt(split[13]), Integer.parseInt(split[14]), Integer.parseInt(split[15]), split[16]);
        }
        data = readFile(P_DATA_RUNES_PATH);
        for (String el : data) {
            String[] split = el.split(";");
            Item.loadItem(split[0], split[1], Integer.parseInt(split[2]), Integer.parseInt(split[3]), split[4], Integer.parseInt(split[5]), Integer.parseInt(split[6]), Integer.parseInt(split[7]), Integer.parseInt(split[8])
                    , Integer.parseInt(split[9]), Double.parseDouble(split[10]), Integer.parseInt(split[11]),Integer.parseInt(split[12]), split[13], split[14]);
        }
        data = readFile(P_DATA_MISC_PATH);
        for (String el : data) {
            String[] split = el.split(";");
            Item.loadItem(split[0], split[1], Integer.parseInt(split[2]), Integer.parseInt(split[3]), split[4], Integer.parseInt(split[5]), Integer.parseInt(split[6]), Integer.parseInt(split[7]), Integer.parseInt(split[8])
                    ,  split[9]);
        }
        data = readFile(P_DATA_SPELLS_PATH);
        for (String el : data) {
            String[] split = el.split(";");
            Spell.loadItem(split[0], split[1], Integer.parseInt(split[2]), Integer.parseInt(split[3]), split[4],Integer.parseInt(split[5]), Integer.parseInt(split[6]), Integer.parseInt(split[7]),
                    Integer.parseInt(split[8]), Integer.parseInt(split[9]),Double.parseDouble(split[10]),Integer.parseInt(split[11]),Integer.parseInt(split[12]),Double.parseDouble(split[13]),Integer.parseInt(split[14]),Integer.parseInt(split[15]),Integer.parseInt(split[16]),split[17]);
        }
    }

    public static List<String> readFilePhaseTwo(String fileName){
        List<String> strings = new ArrayList<>();
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = br.readLine()) != null){
                strings.add(line);
            }
            br.close();
        } catch (IOException e){
            e.printStackTrace();
        }
        return strings;
    }

    private static List<String> readFile(String fileName){
        return readFilePhaseTwo(P_DATA_PATH+fileName);
    }

    public static List<String> readMapFile(String fileName){
        return readFilePhaseTwo(P_DATA_MAPS_PATH+fileName);
    }
}
