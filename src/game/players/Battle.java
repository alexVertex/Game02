package game.players;

import engine.core.Mathp;
import game.general.Event;
import engine.helpers.StarterHelper;
import game.world.ammos.Ammo;

import java.util.List;

import static engine._SetsStrings.*;
import static engine._TextString.*;

public class Battle {

    private static int staminaNeed(Player player){
        double weaponWeight = player.getWeaponWeight();
        return (int) (weaponWeight * D_PLAYER_STAMINA_FOR_HIT_WEAPON_WEIGHT_KOEF);
    }

    public static void startHit(Player player){
        int needStamina = staminaNeed(player);
        if(player.stamina > needStamina) {
            player.onHit = true;
        }
    }

    public static void hitLogic(Player player){
        if(!player.onHit) return;
        player.onGuard = false;
        int ammo = player.getAmmo();
        if(ammo == 0) {
            Event.makeEvent(S_EVENT_NO_AMMO);
            player.onHit = false;
        } else {
            double needHitProgress = player.getWeaponInterval();
            double ont = player.hitProgress/needHitProgress;
            int frameOld = (int) (16*ont);
            player.hitProgress += player.speed * D_PLAYER_HIT_PROGRESS_MULT;
            player.attackProj.setSizeX(40);
            ont = player.hitProgress/needHitProgress;
            int frame = (int) (16*ont);
            player.attackProj.setAngle(Math.PI);
            player.attackProj.leftTex = 0.0625f*frame;
            player.attackProj.rightTex = 0.0625f*(frame+1);
            player.attackProj.replaceModelTID();
            if (frameOld == 12 && frame == 13){
                if (ammo == I_ITEM_NO_USE_AMMO) {
                    testHit(player);
                    player.say(S_PLAYER_SAY_ATTACKED);

                } else {
                    Ammo ammo1 = new Ammo(player, player.getX(), player.getY(), player.getAngle(), 0, player.getDamage(), player.getDamageType(), (int) player.getWeaponDist());
                    StarterHelper.game.current.addAmmo(ammo1);
                    player.lossAmmo();
                }
                player.staminaLoss(staminaNeed(player));
            }
            if(player.hitProgress > needHitProgress){
                player.hitProgress = 0;
                player.onHit = false;
                player.attackProj.setSizeX(0);
            }
        }
    }
    private static boolean meleeAttack = false;
    private static void testHit(Player player){
        double weaponDist = player.getWeaponDist();
        double pointX = player.getX() + Math.cos(player.getAngle())*weaponDist;
        double pointY = player.getY() - Math.sin(player.getAngle())*weaponDist;
        List<Player> players = StarterHelper.game.getPlayers();
        for (Player el : players) {
            if (el.equals(player)) continue;
            double rast = Mathp.rast(el.getX(), el.getY(), pointX, pointY);
            if (rast < 16) {
                meleeAttack = true;
                takeDamage(player, el, player.getDamage(), player.getDamageType(), player.getX(), player.getY(),player.getBalanceDamage());
            }
        }
    }
    public static void takeDamage(Player damager, Player suffer, double damageInit, int damageType, double attackSideX, double attackSideY, double balanceDamage){
        AutoControl.setGetDamaged(suffer, damager);
        double damage = damageInit;
        if(damageType < I_GAMEPLAY_DAMAGE_FIRE && !suffer.localID.equals(S_PLAYER_LOCAL_ID) && suffer.AIMonent == I_AI_MOMENT_NORMAL){
            damage *= D_PLAYER_BACKSTAB_DAMAGE_MULT;
            if(damager.localID.equals(S_PLAYER_LOCAL_ID)){
                Event.makeEvent(S_EVENT_BACKSTAB);
            }
        }
        damage -= suffer.defenses[damageType]*suffer.armorOtn();
        if(damage < 0) damage = 0;
        damage *= D_PLAYER_DAMAGE_SPRAY_BASE+Mathp.random()*D_PLAYER_DAMAGE_SPRAY_ADD;
        boolean staminaDamage = false;
        double angleAttack = Math.atan2(suffer.getY()-attackSideY,-suffer.getX()+attackSideX);
        if(suffer.onGuard && suffer.losedControl == 0){
            double angleShield = Mathp.angleBetwinAngles(suffer.getAngle(),angleAttack);
            if(angleShield < D_PLAYER_SHIELD_HIT_ANGLE) {
                staminaDamage = true;
            }
        }
        if(staminaDamage) {

            suffer.staminaLoss(damage*(1.0-suffer.getShieldStability()));
            damage *= (1-suffer.getShieldDefense());
            if(suffer.stamina == 0){
                damage += -suffer.stamina*2;
                suffer.loseControl(angleAttack,I_BATTLE_LOSED_CONTOROL_TIME_SHIELD_BREAK);
                suffer.stamina = 0;
            } else {
                if(meleeAttack){
                    damager.loseControl(angleAttack+Math.PI,I_PLAYER_LOSS_CONTROL_TIME_AFTER_HIT_ON_SHIELD);
                }
            }
            suffer.shieldColorRed = 1;
            suffer.shieldColorBlue = 0;
        } else {
            if(suffer.losedControl == 0) {
                suffer.poise -= balanceDamage;
                if (suffer.poise < 0) {
                    suffer.loseControl(angleAttack,I_PLAYER_LOSS_CONTROL_TIME_AFTER_HIT);
                }
            }
        }
        suffer.armor -= damageInit*D_PLAYER_DAMAGE_ARMOR_MULT;
        if(suffer.armor < 0) suffer.armor = 0;
        suffer.health -= damage;
        if(suffer.health < 0){
            suffer.death();
            suffer.health = 0;
        }
        meleeAttack = false;
    }

    public static void startGuard(Player player) {
        if(player.losedControl == 0) {
            player.onGuard = true;
        }
    }

    public static void takeDamage(Player owner, Player suffer, double power1, int damageType, double x, double y,double balanceDamage, double power2) {
        double defense = suffer.defenses[damageType]*suffer.armorOtn();
        power1 -= defense;
        power1 /= power2;
        power1 += defense;
        takeDamage(owner, suffer, power1, damageType, x, y,balanceDamage);
    }

    static void poiseControl(Player player){
        player.poise+=0.02;
        if(player.poise > player.poiseMax){
            player.poise = player.poiseMax;
        }
        if(player.losedControl > 0){
            player.onHit = false;
            player.hitProgress = 0;
            player.attackProj.setSizeX(0);
            player.onGuard = false;
            player.speedX = -Math.cos(player.losedControlAngle)*player.losedControlSpeed;
            player.speedY = Math.sin(player.losedControlAngle)*player.losedControlSpeed;
            player.losedControl--;
            if(player.losedControl == 0){
                player.poise = player.poiseMax;
            }
        }
    }
}
