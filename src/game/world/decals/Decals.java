package game.world.decals;

import engine.core.Mathp;
import engine.render.Sprite;
import engine.window.Actor;
import engine.helpers.SpriteHelper;
import engine.helpers.StarterHelper;
import game.players.Player;

import static engine._SetsStrings.*;

public class Decals extends Actor {
    int timeLive = I_DECALS_TIME_LIVE_MAX;
    Sprite sprite;
    double curalpha = 1;
    double XX, YY;
    int targetSize;
    int curSize = 0;
    int timeWait = 0;
    double[] colorBlood = {0,0,0};
    int type;
    public Decals(String texture, double x, double y, double angle, int size){
        sprite = new Sprite(P_PRINTS_TEXTURE+texture,x,y,I_ZORDER_DECALS,size,size,I_CAMERA_AFFECT_YES);
        sprite.setAngle(angle);
        XX = x;
        YY = y;
        type = I_DECAL_TYPE_STEP;
    }
    public Decals(String texture, double x, double y, int size){
        sprite = new Sprite(P_PRINTS_TEXTURE+texture,x,y,I_ZORDER_DECALS,0,0,I_CAMERA_AFFECT_YES);
        sprite.setAngle(Mathp.random()*Math.PI*2);
        XX = x;
        YY = y;
        targetSize = size;
        switch (texture) {
            case "redBlood" -> {
                colorBlood[0] = 1;
                colorBlood[1] = 0;
                colorBlood[2] = 0;
            }
            case "greenBlood" -> {
                colorBlood[0] = 0;
                colorBlood[1] = 1;
                colorBlood[2] = 0.03;
            }
            case "blueBlood" -> {
                colorBlood[0] = 0;
                colorBlood[1] = 0.77;
                colorBlood[2] = 1;
            }
        }
        type = I_DECAL_TYPE_BLOOD;

    }
    @Override
    public void init() {
        sprite.createModel();
        SpriteHelper.addSprite(sprite);
    }

    @Override
    public void dispose() {
        SpriteHelper.removeSprite(sprite);
    }

    @Override
    public void hide() {
        sprite.alpha = 0;
    }

    @Override
    public void show() {
        sprite.alpha = (float) curalpha;
    }

    @Override
    public void use() {

    }

    public void logic() {
        if(targetSize > curSize){
            if(timeWait == 0) {
                curSize++;
                sprite.setSizeX(curSize);
                sprite.setSizeY(curSize);
                timeWait = I_DECALS_SIZE_ADD_TIMEWAIT;
            } else {
                timeWait--;
            }
        }
        if(type == I_DECAL_TYPE_BLOOD) {
            for(Player ele : StarterHelper.game.getPlayers()){
                double rast = Mathp.rast(ele.getX(),ele.getY(),XX,YY);
                if(rast < curSize){
                    System.arraycopy(colorBlood, 0, ele.stepsColor, 0, 3);
                }
                ele.coloredStepTime = I_DECAL_STEP_BLOOD_COLOR_TIME;
            }
        }
        timeLive--;
        if(timeLive < F_DECALS_TIME_LIVE_START_FADE){
            curalpha = (float) timeLive/F_DECALS_TIME_LIVE_START_FADE;
            sprite.alpha = (float) curalpha;
        }
        if(timeLive == 0){
            StarterHelper.game.current.removeDecals(this);
        }
        double rast = Mathp.rast(StarterHelper.game.getPlayer().getX(),StarterHelper.game.getPlayer().getY(),sprite.locX,sprite.locY);
        if(rast < StarterHelper.game.getPlayer().getVisionDistant() && StarterHelper.game.current.noObstacle(XX,YY,StarterHelper.game.getPlayer().getX(),StarterHelper.game.getPlayer().getY())){
            show();
        } else {
            hide();
        }
    }

    @Override
    public void doStuff(String command, String params) {

    }

    public Sprite getSprite() {
        return sprite;
    }
}
