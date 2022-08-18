package game.magic;

import game.players.Battle;
import game.players.Player;

import java.io.Serial;
import java.io.Serializable;

import static engine._SetsStrings.*;

public class Effect implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private static final int FORTIFY_HEALTH = 0;
    private static final int HEAL = 1;
    private static final int FIRE_DAMAGE = 2;
    private static final int FIRE_DAMAGE_LONG = 3;

    public Player owner;
    public int id;
    public double power1;
    public double power2;
    public int time;

    public Effect(int ID, double Power, int Time){
        id = ID;
        power1 = Power;
        time = Time;
    }

    public Effect(int ID, double Power,double Power2, int Time){
        id = ID;
        power1 = Power;
        time = Time;
        power2 = Power2;
    }

    public void wearOn(Player player, Player owner, boolean makeCopy) {
        Effect copy = this;
        if(makeCopy) {
            copy = new Effect(this.id, this.power1, this.power2, this.time);
        }
        copy.owner = owner;

        player.effectList.add(copy);
        switch (id) {
            case FORTIFY_HEALTH -> player.healthMax += power1;
        }
    }

    public static void continues(Player player) {
        for (int i = 0; i < player.effectList.size(); i++) {
            Effect el = player.effectList.get(i);
            if (el.time > 0) {
                el.time--;
                if (el.time == 0) {
                    player.effectList.remove(el);
                    i--;
                }
            }
            switch (el.id) {
                case HEAL -> {
                    player.health += el.power1;
                    if (player.health > player.healthMax) {
                        player.health = player.healthMax;
                    }
                }
                case FIRE_DAMAGE -> Battle.takeDamage(el.owner, player, el.power1, I_GAMEPLAY_DAMAGE_FIRE, player.getX(), player.getY(),0);
                case FIRE_DAMAGE_LONG -> Battle.takeDamage(el.owner, player, el.power1, I_GAMEPLAY_DAMAGE_FIRE, player.getX(), player.getY(),0,el.power2);
            }
        }
    }

    public void wearOff(Player player) {
        player.effectList.remove(this);
        switch (id) {
            case FORTIFY_HEALTH -> {
                player.healthMax -= power1;
                if (player.health > player.healthMax) {
                    player.health = player.healthMax;
                }
            }
        }
    }

    public void dispose() {

    }
}
