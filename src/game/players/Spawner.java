package game.players;

import java.io.Serial;
import java.io.Serializable;

import static engine._SetsStrings.I_PLAYER_SPOWNER_MULTITIMES;

public class Spawner implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    public String localID;
    public boolean enabled;
    Player player;
    String playerID;
    int locX;
    int locY;
    int spawnTimes;
    int playerStatus;


    public Spawner(Player test, int x, int y, boolean oneTime, int playerStatus) {
        this.localID = test.localID;
        this.player = test;
        this.locX = x;
        this.locY = y;
        spawnTimes = !oneTime ? I_PLAYER_SPOWNER_MULTITIMES : 1;
        this.playerStatus = playerStatus;
    }

    public void spawn(){
        if(!enabled) return;
        if(spawnTimes == 0) return;
        if(spawnTimes > 0)
            spawnTimes--;
        player.oneTimer = spawnTimes == 0;
        player.init();
        player.setPos(locX,locY);
        player.playerStatus = playerStatus;
        player.putOnWorld(player.localID);
    }
    public void respawn(){
        if(spawnTimes == 0 || !enabled) {
            if(player.health > 0){
                player.revive();
                player.setPos(locX,locY);
            }
            return;
        }
        if(spawnTimes > 0)
            spawnTimes--;
        if(player.health <= 0) {
            player.sprite.createModel();
            player.guardProj.createModel();
            player.attackProj.createModel();
            player.putOnWorld(player.localID);
        }
        player.revive();
        player.setPos(locX,locY);
    }
}
