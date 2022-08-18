package game.players;

import engine.core.Mathp;
import engine.core.Starter;
import engine.helpers.StarterHelper;
import game.general.Event;
import game.world.ammos.Ammo;
import game.world.specialObjects.Item;
import jdk.jshell.EvalException;

import java.util.ArrayList;
import java.util.List;

import static engine._SetsStrings.*;

public class AutoControl {
    public static final double DIST_ATTACK = 32;
    static int scanStatus = I_AI_SCAN_STATUS_NO;

    public static void setHearAmmo(Player el, Ammo ammo) {
        if(el.playerStatus != I_GAME_PLAYER_STATUS_FRIEND) {
            if (el.alarmedTimer == 0 && el.searchTime == 0 && el.AIMonent != I_AI_MOMENT_BATTLE) {
                el.alarmedTimer = I_AI_ALARMED_TIME_HEAR;
                el.searchTime = I_AI_SEARCH_TIME;
                double angle = ammo.getAngle();
                el.lastSeenX = ammo.getX() - Math.cos(angle) * 200;
                el.lastSeenY = ammo.getY() + Math.sin(angle) * 200;
            }
        }
    }

    private static void setHearPlayerVoice(Player el, Player player) {
        if(Mathp.rast(player.getX(),player.getY(),el.getX(),el.getY()) < I_AI_PLAYER_VOICE_HEAR_DIST){
            if(el.alarmedTimer == 0 && el.searchTime == 0 && el.AIMonent != I_AI_MOMENT_BATTLE) {
                el.alarmedTimer = I_AI_ALARMED_TIME_HEAR;
                el.searchTime = I_AI_SEARCH_TIME;
                el.lastSeenX = player.getX();
                el.lastSeenY = player.getY();
            }
        }
    }

    private static void setFriendGetAlarmed(Player player, Player el) {
        if((el.AIMonent == I_AI_MOMENT_BATTLE || el.alarmedTimer > 0) && Mathp.rast(player.getX(),player.getY(),el.getX(),el.getY()) < player.getVisionDistant()*I_AI_SCAN_HEAR_MULT && player.AIMonent != I_AI_MOMENT_BATTLE){
            if(el.AIMonent == I_AI_MOMENT_BATTLE){
                player.lastSeenX = el.targetAttack.getX();
                player.lastSeenY = el.targetAttack.getY();
            } else {
                player.lastSeenX = el.lastSeenX;
                player.lastSeenY = el.lastSeenY;
            }
            if(player.alarmedTimer == 0 && player.searchTime == 0) {
                player.alarmedTimer = I_AI_ALARMED_TIME_FRIEND_ALARM;
                player.searchTime = I_AI_SEARCH_TIME;
            }
        }
    }

    public static void setGetDamaged(Player player, Player damager) {
        if(player.alarmedTimer == 0 && player.searchTime == 0) {
            player.alarmedTimer = I_AI_ALARMED_TIME_DAMAGE;
            player.searchTime = I_AI_SEARCH_TIME;
            player.lastSeenX = damager.getX();
            player.lastSeenY = damager.getY();
        }
    }

    public static void control(Player player){
        scanStatus = I_AI_SCAN_STATUS_NO;
        Player scanned = scanForPlayer(player);
        player.targetAttack = null;
        if(scanned != null){
            if(scanStatus == I_AI_SCAN_STATUS_SEE) {
                player.AIMonent = I_AI_MOMENT_BATTLE;
            } else if(scanStatus == I_AI_SCAN_STATUS_HEAR){
                player.lastSeenX = scanned.getX();
                player.lastSeenY = scanned.getY();
                if(player.alarmedTimer == 0 && player.searchTime == 0) {
                    player.alarmedTimer = I_AI_ALARMED_TIME_HEAR;
                    player.searchTime = I_AI_SEARCH_TIME;
                }
            } else if (scanStatus == I_AI_SCAN_STATUS_FEAR){
                player.AIMonent = I_AI_MOMENT_FEARED;
                player.fearedTime = I_AI_FEAR_TIME;
                player.lastSeenX = scanned.getX();
                player.lastSeenY = scanned.getY();
            }
        } else {
            if (player.AIMonent == I_AI_MOMENT_BATTLE) {
                player.searchTime = I_AI_SEARCH_TIME;
                player.AIMonent = I_AI_MOMENT_SEARCH;
            }
        }
        double[] target = {0,0};
        double speed = player.moveSpeed*D_AI_SPEED_WALK;
        if(player.AIMonent == I_AI_MOMENT_NORMAL) {
            target = player.AIMain.getPoint(player);

        } else if(player.AIMonent == I_AI_MOMENT_BATTLE){
            target[0] = scanned.getX();
            target[1] = scanned.getY();
            player.lastSeenX =target[0];
            player.lastSeenY =target[1];
            if(Mathp.rast(scanned.getX(), scanned.getY(),player.getX(),player.getY()) > DIST_ATTACK) {
                speed = player.moveSpeed*D_AI_SPEED_WALK;
            } else {
                speed = 0;
                Battle.startHit(player);
            }
            player.targetAttack = scanned;
        } else if(player.AIMonent == I_AI_MOMENT_SEARCH){
            player.searchTime--;
            target[0] = player.lastSeenX;
            target[1] = player.lastSeenY;
            if(player.searchTime == 0){
                player.AIMonent = I_AI_MOMENT_NORMAL;
            }
            if(Mathp.rast(player.lastSeenX,player.lastSeenY,player.getX(),player.getY()) > DIST_ATTACK) {
                speed = player.moveSpeed*D_AI_SPEED_WALK;
            } else {
                speed = 0;
            }
        } else if (player.AIMonent == I_AI_MOMENT_FEARED){
            player.AIMain.timeCur = 0;
            player.fearedTime--;
            if(player.fearedTime == 0){
                player.AIMonent = I_AI_MOMENT_NORMAL;
            }
            double angleRun = Math.atan2(player.lastSeenY-player.getY(),player.lastSeenX-player.getX());
            double rast = (player.visionDistant*I_ACTOR_DEF_SIZE)*F_FEARED_DIST_MULT;
            target[0] = player.lastSeenX - rast*Math.cos(angleRun);
            target[1] = player.lastSeenY - rast*Math.sin(angleRun);
            double rastCur = Mathp.rast(player.lastSeenX ,player.lastSeenY,player.getX(),player.getY());
            if(rastCur < rast) {
                speed = player.moveSpeed*D_AI_SPEED_WALK;
            } else {
                speed = 0;
            }
        }
        if (player.alarmedTimer > 0){
            player.alarmedTimer--;
            if(player.alarmedTimer == 0){
                player.AIMonent = I_AI_MOMENT_SEARCH;
            }
        }
        double tarX = target[0], tarY = target[1];

        double locX = player.getX();
        double locY = player.getY();

        int[] path = PathFind.pathFind((int) (locX), (int) (locY), (int) (tarX), (int) (tarY));



        if(path != null && Mathp.rast(tarX,tarY,path[0]*32,path[1]*32) > 64){
            tarX = path[0]*32;
            tarY = path[1]*32;

        }

        System.out.println(locX/32+"|"+locY/32);

        System.out.println(tarX/32+":"+tarY/32);

        double angle = Math.atan2(tarY - locY, tarX - locX);
        double speedX = Math.cos(angle) * speed;
        double speedY = Math.sin(angle) * speed;
        double rast = Mathp.rast(locX, locY, tarX, tarY);
        if (rast > speed) {
            player.setMove(speedX, speedY);
            player.setLook(tarX, tarY);

        } else {
            player.setPos((int) tarX, (int) tarY);
        }
    }
    static List<Item> temporal = new ArrayList<>();
    private static Player scanForPlayer(Player AI) {
        Player scanned = null;
        int attitude = I_AI_FRACTION_ATTITUDE_FRIEND;
        for(Player el : StarterHelper.game.getPlayers()) {
            if(el.equals(StarterHelper.game.getPlayer())) continue;
            if(el.equals(AI)) continue;
            if(Fraction.getAttitude(AI.fraction,el.fraction) == I_AI_FRACTION_ATTITUDE_NEUTRAL) continue;
            int newAttitude = Fraction.getAttitude(AI.fraction,el.fraction);
            if(newAttitude == I_AI_FRACTION_ATTITUDE_FRIEND){
                setFriendGetAlarmed(AI,el);
            }
            if(attitude == I_AI_FRACTION_ATTITUDE_FRIEND && newAttitude == I_AI_FRACTION_ATTITUDE_ENEMY) attitude = newAttitude;
            if(attitude == I_AI_FRACTION_ATTITUDE_FRIEND && newAttitude == I_AI_FRACTION_ATTITUDE_FEAR) attitude = newAttitude;
            if(attitude == I_AI_FRACTION_ATTITUDE_ENEMY && newAttitude == I_AI_FRACTION_ATTITUDE_FEAR) attitude = newAttitude;
            if(attitude == I_AI_FRACTION_ATTITUDE_ENEMY || attitude == I_AI_FRACTION_ATTITUDE_FEAR){
                boolean heard = testHear(AI,el);
                boolean seen = testSee(AI,el);
                if(scanStatus < I_AI_SCAN_STATUS_SEE){
                    if(heard){
                        scanned = el;
                        scanStatus = I_AI_SCAN_STATUS_HEAR;
                    }
                }
                if(seen){
                    scanned = el;
                    if(attitude == I_AI_FRACTION_ATTITUDE_FEAR){
                        scanStatus = I_AI_SCAN_STATUS_FEAR;
                    } else if (scanStatus < I_AI_SCAN_STATUS_SEE){
                        scanStatus = I_AI_SCAN_STATUS_SEE;
                    }
                }
            }
        }
        if(scanStatus < I_AI_SCAN_STATUS_FEAR) {
            if (AI.playerStatus == I_AI_PLAYER_ATTITUDE_ENEMY) {
                Player player = StarterHelper.game.getPlayer();
                if (testHear(AI, player)) {
                    scanned = player;
                    scanStatus = I_AI_SCAN_STATUS_HEAR;
                }
                if (testSee(AI, player)) {
                    scanned = player;
                    scanStatus = I_AI_SCAN_STATUS_SEE;
                }
                if(player.voice != null && player.voice.isPlaying()){
                    setHearPlayerVoice(AI, player);
                }
            }
        }
        return scanned;
    }




    private static boolean testHear(Player AI, Player target){
        if (target.health > 0) {
            double rast = Mathp.rast(target.getX(), target.getY(), AI.getX(), AI.getY());
            double rastMult = (target.movedRast - D_AI_SCAN_KOEF_HEAR);
            return rast < AI.visionDistant * I_AI_SCAN_HEAR_MULT * rastMult;
        }
        return false;
    }

    private static boolean testSee(Player AI, Player target){
        if (target.health > 0) {
            double rast = Mathp.rast(target.getX(), target.getY(), AI.getX(), AI.getY());
            double angle = Math.atan2(-target.getY() + AI.getY(), target.getX() - AI.getX());
            angle = Mathp.angleBetwinAngles(angle, AI.getAngle());
            //return false;
            return angle < D_AI_ANGLE_SEE && rast < AI.getVisionDistant() * I_AI_SCAN_SEE_MULT && StarterHelper.game.current.noObstacle(target.getX(), target.getY(), AI.getX(), AI.getY());
        }
        return false;
    }

    public static void clear(Player player) {
        player.AIMonent = I_AI_MOMENT_NORMAL;
        player.alarmedTimer = 0;
        player.searchTime = 0;
        player.fearedTime = 0;
    }


}
