package engine.input.interfaces;

import engine.core.Mathp;
import engine.render.Sprite;
import engine.input.MainInput;
import engine.helpers.AudioHelper;
import engine.helpers.SpriteHelper;

import static engine._SetsStrings.*;

public class Flag extends Element{

    private static final float PART_X = F_FLAG_ACTIVE_TEXTURE_CUT;
    int status;

    String activateSound;
    public Flag(String texture, int x, int y, int z, double sx, double sy, int initStatus, String activate){
        sprite = new Sprite(texture, x,y,z,sx,sy,I_CAMERA_AFFECT_NO);
        createModel();
        sprite.leftTex = PART_X*initStatus;
        sprite.rightTex = PART_X*(initStatus+1);
        sprite.replaceModelTID();
        status = initStatus;
        activateSound = activate;
    }
    boolean ready = false;

    @Override
    public boolean work(MainInput input) {

        if(Mathp.isPointInRect(sprite.locX,sprite.locY,sprite.sizX, sprite.sizY, input.getMouseX(), input.getMouseY())){
            if(input.isKeyHolded(I_MOUSE_BUTTON_LEFT)){
                changeState(I_ELEMENT_STATE_PRESSED);
                ready = true;
            } else {
                changeState(I_ELEMENT_STATE_HOVERED);
            }
        } else {
            changeState(I_ELEMENT_STATE_ENABLED);
            ready = false;
        }
        return false;
    }

    @Override
    public boolean worked() {
        boolean worked = ready && state == I_ELEMENT_STATE_HOVERED;
        if(worked) {
            status = 1 - status;
            ready = false;
            sprite.leftTex = PART_X*status;
            sprite.rightTex = PART_X*(status+1);
            sprite.replaceModelTID();
            AudioHelper.playSoundInterface(activateSound);
        }
        return worked;
    }

    @Override
    public void dispose() {
        SpriteHelper.removeSprite(sprite);
    }

    @Override
    public void add() {
        SpriteHelper.addSprite(sprite);
    }

    public void setStatus(int status){
        this.status = status;
    }

    public int getStatus(){
        return status;
    }
}
