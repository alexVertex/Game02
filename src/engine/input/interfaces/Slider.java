package engine.input.interfaces;

import engine.core.Mathp;
import engine.render.Sprite;
import engine.input.MainInput;
import engine.helpers.AudioHelper;
import engine.helpers.FontHelper;
import engine.helpers.SpriteHelper;

import static engine._SetsStrings.*;

public class Slider extends Element{

    private final String activateSound;

    private final Button left, right;

    private float val;
    private final float remX, remSizeX;
    public Slider(String texture, int x, int y, int z, double sx, double sy, float init, String activate){
        val = init;
        remX = x;
        remSizeX = (float)( sx-sy*2);
        sprite = new Sprite(texture, remX+remSizeX*val*0.5-remSizeX*0.5,y,z,remSizeX*val,sy,I_CAMERA_AFFECT_NO);
        createModel();
        sprite.rightTex = F_ELEMENT_SLIDER_PART_X;
        sprite.replaceModelTID();
        activateSound = activate;
        left = new Button(texture, (int) (x-sx/2+sy/2),y,z+1,sy,sy, FontHelper.getFont(S_FONT_MAIN_NAME)," ",activateSound);
        right = new Button(texture, (int) (x+sx/2-sy/2),y,z+1,sy,sy, FontHelper.getFont(S_FONT_MAIN_NAME)," ",activateSound);
        left.sprite.leftTex = F_ELEMENT_SLIDER_PART_X;
        right.sprite.leftTex = F_ELEMENT_SLIDER_PART_X;
        left.sprite.setAngle(Math.PI);
        left.sprite.replaceModelTID();
        right.sprite.replaceModelTID();
    }
    float speed = 0;
    boolean dragged = false;
    @Override
    public boolean work(MainInput input) {
        left.work(input);
        right.work(input);
        if(left.pressed()){
            speed -=F_ELEMENT_SLIDER_SPEED_ACCELERATE;
            if(speed < -F_ELEMENT_SLIDER_SPEED_BORDER){
                speed = -F_ELEMENT_SLIDER_SPEED_BORDER;
            }
        } else if(right.pressed()){
            speed +=F_ELEMENT_SLIDER_SPEED_ACCELERATE;
            if(speed > F_ELEMENT_SLIDER_SPEED_BORDER){
                speed = F_ELEMENT_SLIDER_SPEED_BORDER;
            }
        } else if(left.state <=I_ELEMENT_STATE_HOVERED && right.state <=I_ELEMENT_STATE_HOVERED){
            speed = 0;
        }
        val+= speed;
        if(!dragged) {
            if (Mathp.isPointInRect(remX, sprite.locY, remSizeX, sprite.sizY, input.getMouseX(), input.getMouseY())) {
                if (input.isKeyHolded(I_MOUSE_BUTTON_LEFT)) {
                    changeState(I_ELEMENT_STATE_PRESSED);
                    dragged = true;
                } else {
                    changeState(I_ELEMENT_STATE_HOVERED);
                }
            } else {
                changeState(I_ELEMENT_STATE_ENABLED);
            }
        }
        if(dragged){
            double x = input.getMouseX();
            val = (float) ((x - (remX-remSizeX*0.5f))/remSizeX);
            if(!input.isKeyHolded(0)){
                dragged = false;
                AudioHelper.playSoundInterface(activateSound);
            }
        }
        if(val < 0) val = 0;
        if(val > 1) val = 1;
        sprite.setPosX(remX+remSizeX*val*0.5-remSizeX*0.5);
        sprite.setSizeX(remSizeX*val);

        return dragged;
    }

    @Override
    public boolean worked() {
        return false;
    }

    @Override
    public void dispose() {
        SpriteHelper.removeSprite(sprite);
        SpriteHelper.removeSprite(left.sprite);
        SpriteHelper.removeSprite(right.sprite);
    }

    @Override
    public void add() {
        SpriteHelper.addSprite(sprite);
        SpriteHelper.addSprite(left.sprite);
        SpriteHelper.addSprite(right.sprite);
    }

    public float getVal() {
        return val;
    }
}
