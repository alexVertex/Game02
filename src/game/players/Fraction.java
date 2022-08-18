package game.players;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Fraction {

    public static HashMap<String, Integer> fractions = new HashMap<>();

    public static void loadData(List<String> loadFile) {
        String[] names = loadFile.get(0).split(";");
        for(int i = 0; i < names.length;i++){
            String[] attitude = loadFile.get(1+i).split(";");
            for(int j = 0; j < names.length;j++){
                String name = names[i]+">"+names[j];
                int data = Integer.parseInt(attitude[j]);
                fractions.put(name,data);
            }
        }
    }

    public static int getAttitude(String who, String to){
        String name = who+">"+to;
        return fractions.get(name);
    }
}
