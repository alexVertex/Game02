package game.players;

import game.world.specialObjects.Item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayerLoader {
    
    public static void loadPlayer(String data){
        String[] split = data.split(";");
        Player player = new Player();

        player.ID = split[0];
        player.texture = split[1];
        player.name = split[2];
        player.voiceSource = split[3];
        player.fraction = split[4];
        player.playerStatus = Integer.parseInt(split[5]);

        player.health = Integer.parseInt(split[6]);
        player.stamina = Integer.parseInt(split[7]);
        player.armor = Integer.parseInt(split[8]);
        player.poise = Integer.parseInt(split[9]);
        player.moveSpeed = Integer.parseInt(split[10]);
        player.speed = Integer.parseInt(split[11]);
        player.visionDistant = Integer.parseInt(split[12]);

        player.weaponOneDamage = Integer.parseInt(split[13]);
        player.weaponOneDamageType = Integer.parseInt(split[14]);
        player.baseShieldDamageReduction = Integer.parseInt(split[15]);
        player.baseShieldStability = Integer.parseInt(split[16]);
        player.defenses[0] = Integer.parseInt(split[17]);
        player.defenses[1] = Integer.parseInt(split[18]);
        player.defenses[2] = Integer.parseInt(split[19]);
        player.defenses[3] = Integer.parseInt(split[20]);
        player.defenses[4] = Integer.parseInt(split[21]);
        player.defenses[5] = Integer.parseInt(split[22]);
        player.defenses[6] = Integer.parseInt(split[23]);
        player.defenses[7] = Integer.parseInt(split[24]);
        player.defenses[8] = Integer.parseInt(split[25]);
        player.defenses[9] = Integer.parseInt(split[26]);
        player.defenses[10] = Integer.parseInt(split[27]);

        player.goldMin = Integer.parseInt(split[28]);
        player.goldMax = Integer.parseInt(split[29]);

        player.blood = (split[30]);
        player.steps = (split[31]);
        int lootCount = Integer.parseInt(split[32]);
        player.loot.addAll(Arrays.asList(split).subList(33, lootCount + 33));
        Player.playersData.put(player.ID, player);
    }

    public static Player getPlayerByID(String id){
        Player copy = Player.playersData.get(id);
        Player player = new Player(copy.texture);

        player.ID = copy.ID;
        player.texture = copy.texture;
        player.name = copy.name;
        player.voiceSource = copy.voiceSource;
        player.fraction = copy.fraction;
        player.playerStatus = copy.playerStatus;

        player.health = copy.health;
        player.healthMax = player.health;
        player.stamina = copy.stamina;
        player.staminaMax = player.stamina;
        player.armor = copy.armor;
        player.armorMax = copy.armor;
        player.poise = copy.poise;
        player.poiseMax = player.poise;
        player.moveSpeed = copy.moveSpeed;
        player.speed = copy.speed;
        player.visionDistant = copy.visionDistant;

        player.weaponOneDamage = copy.weaponOneDamage;
        player.weaponOneDamageType = copy.weaponOneDamageType;
        player.baseShieldDamageReduction = copy.baseShieldDamageReduction;
        player.baseShieldStability = copy.baseShieldStability;
        System.arraycopy(copy.defenses, 0, player.defenses, 0, player.defenses.length);
        player.goldMax = copy.goldMax;
        player.goldMin = copy.goldMin;
        player.blood = copy.blood;
        player.steps = copy.steps;

        player.loot = copy.loot;
        return player;
    }

    public static List<Item> getLoot(List<String> lootIDs) {
        List<Item> loot = new ArrayList<>();
        for(String el : lootIDs){
            Item copy = new Item(el,"",0,0);
            loot.add(copy);
        }
        return loot;
    }
}
