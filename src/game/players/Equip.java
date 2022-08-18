package game.players;

import engine.helpers.StarterHelper;
import game.general.Event;
import game.magic.Spell;
import game.world.specialObjects.Item;

import static engine._SetsStrings.*;
import static engine._TextString.*;

public class Equip {

    public static Item dressItem(Player player, int index, Item item) {
        if(index == I_PLAYER_ARMAMENTS_WEAPON_ONE || index == I_PLAYER_ARMAMENTS_WEAPON_TWO)
            StarterHelper.game.changedWeapon = true;
        else if(index == I_PLAYER_ARMAMENTS_SHIELD_ONE || index == I_PLAYER_ARMAMENTS_SHIELD_TWO)
            StarterHelper.game.changedShield = true;
        else if(index >= I_PLAYER_ARMAMENTS_ITEM_1)
            StarterHelper.game.changedItem = true;

        Item saved = player.armaments[index];
        if(saved != null){
            player.load -= saved.weight;
            if(saved.effect != null && index < I_PLAYER_ARMAMENTS_ITEM_1){
                saved.effect.wearOff(player);
            }
        }
        if(item != null){
            if(item.effect != null && index < I_PLAYER_ARMAMENTS_ITEM_1){
                item.effect.wearOn(player,player,false);
            }
            player.load += item.weight;
        }
        player.armaments[index] = item;
        if(index >= I_PLAYER_ARMAMENTS_ITEM_1){
            if(item == null){
                if (I_PLAYER_ARMAMENTS_ITEM_4 - index >= 0)
                    System.arraycopy(player.armaments, index + 1, player.armaments, index, I_PLAYER_ARMAMENTS_ITEM_4 - index);
                player.armaments[I_PLAYER_ARMAMENTS_ITEM_4] = null;
            }
            player.cursorItem = 0;
            player.curItem = player.armaments[I_PLAYER_ARMAMENTS_ITEM_1];

        } else {
            changeGear(player);
        }
        return saved;
    }

    private static void changeGear(Player player){
        player.curWeapon = player.armaments[I_PLAYER_ARMAMENTS_WEAPON_ONE];
        player.curShield = player.armaments[I_PLAYER_ARMAMENTS_SHIELD_ONE];
        player.weaponOneDamage = player.armaments[I_PLAYER_ARMAMENTS_WEAPON_ONE] == null ? F_PLAYER_DEFAULT_DAMAGE : (float) player.armaments[I_PLAYER_ARMAMENTS_WEAPON_ONE].damage;
        player.weaponTwoDamage = player.armaments[I_PLAYER_ARMAMENTS_WEAPON_TWO] == null ? F_PLAYER_DEFAULT_DAMAGE : (float) player.armaments[I_PLAYER_ARMAMENTS_WEAPON_TWO].damage;
        player.weaponOneDamageType = player.armaments[I_PLAYER_ARMAMENTS_WEAPON_ONE] == null ? I_PLAYER_DEFAULT_DAMAGE_TYPE : player.armaments[I_PLAYER_ARMAMENTS_WEAPON_ONE].damageType;
        player.weaponTwoDamageType = player.armaments[I_PLAYER_ARMAMENTS_WEAPON_TWO] == null ? I_PLAYER_DEFAULT_DAMAGE_TYPE : player.armaments[I_PLAYER_ARMAMENTS_WEAPON_TWO].damageType;
        player.weaponOneAmmo= player.armaments[I_PLAYER_ARMAMENTS_WEAPON_ONE] == null ? I_PLAYER_DEFAULT_AMMO : player.armaments[I_PLAYER_ARMAMENTS_WEAPON_ONE].ammoMax;
        player.weaponTwoAmmo= player.armaments[I_PLAYER_ARMAMENTS_WEAPON_TWO] == null ? I_PLAYER_DEFAULT_AMMO : player.armaments[I_PLAYER_ARMAMENTS_WEAPON_TWO].ammoMax;
        if(player.armaments[I_PLAYER_ARMAMENTS_ARMOR] != null){
            System.arraycopy(player.armaments[I_PLAYER_ARMAMENTS_ARMOR].defenses, 0, player.defenses, 0, player.armaments[I_PLAYER_ARMAMENTS_ARMOR].defenses.length);
            player.poise = player.armaments[I_PLAYER_ARMAMENTS_ARMOR].weight;
            player.poiseMax = player.poise;
        } else {
            player.defenses = new int[I_GAMEPLAY_DAMAGE_ARRAY_SIZE];
            player.poise = I_PLAYER_DEFAULT_POISE;
            player.poiseMax = I_PLAYER_DEFAULT_POISE;
        }
    }

    private static void switchEquip(Player player, int indexOne, int indexTwo, String message){
        if(!player.onHit && !player.onGuard) {
            Item save = player.armaments[indexOne];
            player.armaments[indexOne] = player.armaments[indexTwo];
            player.armaments[indexTwo] = save;
            changeGear(player);
        } else {
            Event.makeEvent(message);
        }
    }

    public static void switchWeapon(Player player){
        switchEquip(player,I_PLAYER_ARMAMENTS_WEAPON_ONE,I_PLAYER_ARMAMENTS_WEAPON_TWO,S_EVENT_NO_WEAPON_SWITCH);
        StarterHelper.game.changedWeapon = true;
    }

    public static void switchShield(Player player){
        switchEquip(player,I_PLAYER_ARMAMENTS_SHIELD_ONE,I_PLAYER_ARMAMENTS_SHIELD_TWO,S_EVENT_NO_SHIELD_SWITCH);
        StarterHelper.game.changedShield = true;
    }

    public static Spell dressSpell(Player player, int row, int index, Spell item) {
        if(row == I_PLAYER_SPELL_LEFT_ROW)
            StarterHelper.game.changedSpell1 = true;
        else
            StarterHelper.game.changedSpell2 = true;
        Spell[] spells = row == I_PLAYER_SPELL_LEFT_ROW ? player.spell1 : player.spell2;
        Spell saved = spells[index];
        spells[index] = item;
        if(item == null){
            if (spells.length - 1 - index >= 0)
                System.arraycopy(spells, index + 1, spells, index, spells.length - 1 - index);
            spells[spells.length-1] = null;
        }
        if(row == 0){
            player.cursorSpell1 = 0;
            player.curSpell1 = player.spell1[0];
        } else {
            player.cursorSpell2 = 0;
            player.curSpell2 = player.spell2[0];
        }
        return saved;
    }

    public static void switchItem(Player player, int i) {
        if(i==I_MANUAL_SWITCH_ITEM){
            player.cursorItem++;
            if(player.cursorItem == (I_PLAYER_ARMAMENTS_ITEM_4-I_PLAYER_ARMAMENTS_ITEM_1+1) || player.armaments[player.cursorItem+I_PLAYER_ARMAMENTS_ITEM_1] == null){
                player.cursorItem = 0;
            }
            player.curItem = player.armaments[player.cursorItem+I_PLAYER_ARMAMENTS_ITEM_1];
            StarterHelper.game.changedItem = true;

        } else if(i == I_MANUAL_SWITCH_SPELL1){
            player.cursorSpell1++;
            if(player.cursorSpell1 == player.spell1.length || player.spell1[player.cursorSpell1] == null){
                player.cursorSpell1 = 0;
            }
            player.curSpell1 = player.spell1[player.cursorSpell1];
            StarterHelper.game.changedSpell1 = true;

        } else if(i==I_MANUAL_SWITCH_SPELL2) {
            player.cursorSpell2++;
            if (player.cursorSpell2 == player.spell2.length || player.spell2[player.cursorSpell2] == null) {
                player.cursorSpell2 = 0;
            }
            player.curSpell2 = player.spell2[player.cursorSpell2];
            StarterHelper.game.changedSpell2 = true;

        }
    }
}
