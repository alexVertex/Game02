package game.players;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Merch {
    public static HashMap<String, Merch> data = new HashMap<>();

    public static void loadMerch(String line){
        String[] split = line.split(";");
        String name = split[0];
        Merch merch = new Merch();
        merch.merchIds.addAll(Arrays.asList(split).subList(1, split.length));
        data.put(name,merch);
    }

    public List<String> merchIds = new ArrayList<>();
}
