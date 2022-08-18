package game.players;

import engine.core.Mathp;

import java.io.Serial;
import java.io.Serializable;

import static engine._SetsStrings.*;

public class AIpackage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    double[] point = {0,0,0};
    int timeCur;
    public int left, right, top, bot, timeMin, timeMax;
    public int type;

    int curPoint = 0;
    int[][] points;
    public AIpackage(int left, int right, int top, int bot, int timeMin, int timeMax){
        this.left = left;
        this.right = right;
        this.top = top;
        this.bot = bot;
        this.timeMin = timeMin;
        this.timeMax = timeMax;
        type = I_AI_TYPE_ROAMING;
    }

    public AIpackage(int[][] ints) {
        type = I_AI_TYPE_PATROL;
        points = ints;
    }

    public double[] getPoint(Player player){
        if(type == I_AI_TYPE_ROAMING){
            double rast = Mathp.rast(point[0],point[1],player.getX(),player.getY());
            if(rast < 2 || timeCur == 0){
                if(timeCur == 0) {
                    double x = (right - left) * Mathp.random() + left;
                    double y = (bot - top) * Mathp.random() + top;
                    point[0] = x;
                    point[1] = y;
                    point[2] = Math.atan2(player.getY() - y, player.getX() - x);
                    timeCur = (int) ((timeMax - timeMin) * Mathp.random() + timeMin);
                } else {
                    timeCur--;
                }
            }
        } else if (type == I_AI_TYPE_PATROL){
            double rast = Mathp.rast(point[0],point[1],player.getX(),player.getY());
            if(rast < 2 || timeCur == 0){
                if(timeCur == 0) {
                    double x = points[curPoint][0];
                    double y = points[curPoint][1];
                    point[0] = x;
                    point[1] = y;
                    point[2] = Math.atan2(player.getY() - y, player.getX() - x);
                    timeCur = points[curPoint][2];
                    curPoint++;
                    if(curPoint == points.length){
                        curPoint = 0;
                    }
                } else {
                    timeCur--;
                }
            }
        }
        return point;
    }
}
