package engine.input.interfaces;

import engine.core.Mathp;
import engine.helpers.AudioHelper;
import engine.helpers.FontHelper;
import engine.helpers.SpriteHelper;
import engine.input.MainInput;
import engine.render.Sprite;

import static engine._SetsStrings.*;

public class Scroller extends Element{

    private boolean first = true;
    private final String activateSound;

    private final Button left, right;

    private float val, oldVal;
    private float maxVal;
    private int sizeY;
    private float locY;
    private float speed;
    private boolean dragged;
    private int[] wheelZone;
    public Scroller(String texture, int x, int y, int z, double sx, double sy, float init,float max, String activate, int[] wheelRectangle){
        val = init;
        locY = y;
        maxVal = max;
        sizeY = (int) (sy-sx*2);
        sprite = new Sprite(texture, x,y,z,sx,sx,I_CAMERA_AFFECT_NO);
        createModel();
        sprite.rightTex = F_ELEMENT_SCROLLER_PART_X;
        sprite.replaceModelTID();
        activateSound = activate;
        left = new Button(texture, x,(int) (y-sx/2+sy/2),z+1,sx,sx, FontHelper.getFont(S_FONT_MAIN_NAME)," ",activateSound);
        right = new Button(texture, x,(int) (y+sx/2-sy/2),z+1,sx,sx, FontHelper.getFont(S_FONT_MAIN_NAME)," ",activateSound);
        left.sprite.leftTex = F_ELEMENT_SCROLLER_PART_X;
        right.sprite.leftTex = F_ELEMENT_SCROLLER_PART_X;
        left.sprite.setAngle(-Math.PI/2);
        right.sprite.setAngle(Math.PI/2);
        left.sprite.replaceModelTID();
        right.sprite.replaceModelTID();
        wheelZone = wheelRectangle;
    }

    @Override
    public boolean work(MainInput input) {
        if(maxVal < I_ELEMENT_SCROLLER_SIZE_START_WORK){
            val = 0;
            sprite.setPosY(-999);
            return false;
        }
        oldVal = val;
        if(Mathp.isPointInRect(wheelZone,input.getMouseX(),input.getMouseY())) {
            double dw = input.getdWheel();
            if (dw != 0) {
                speed = 0;
                if (dw < 0 && val < maxVal) {
                    val++;
                } else if (dw > 0 && val > 0) {
                    val--;
                }
            }
        }
        left.work(input);
        right.work(input);
        if(left.pressed()){
            if(speed == 0){
                speed = -1;
            }
        } else if(right.pressed()){
            if(speed == 0){
                speed = +1;
            }
        } else if(left.state <=I_ELEMENT_STATE_HOVERED && right.state <=I_ELEMENT_STATE_HOVERED){
            speed = 0;
            first = true;
        }
        if(speed == -1){
            if(val > 0) val--;
            speed = first ? -F_ELEMENT_SPEED_ACCELERATE_FIRST : -F_ELEMENT_SPEED_ACCELERATE;
        }
        if(speed == +1){
            if(val < maxVal) val++;
            speed = first ? F_ELEMENT_SPEED_ACCELERATE_FIRST : F_ELEMENT_SPEED_ACCELERATE;
        }
        if(speed > 0 && speed < 1){
            speed += first ? F_ELEMENT_SPEED_ACCELERATE_FIRST : F_ELEMENT_SPEED_ACCELERATE;
            if(speed > 1){
                speed = F_ELEMENT_SPEED_ACCELERATE;
                if(val < maxVal) val++;
                first = false;
            }
        }
        if(speed < 0 && speed > -1){
            speed += first ? -F_ELEMENT_SPEED_ACCELERATE_FIRST : -F_ELEMENT_SPEED_ACCELERATE;
            if(speed < -1){
                speed = -F_ELEMENT_SPEED_ACCELERATE;
                if(val > 0) val--;
                first = false;
            }
        }
        float start = (float) (locY + sizeY/2 - sprite.sizX/2);
        float add = (float) (sizeY-sprite.sizX) * val/maxVal;
        float curY = start - add;
        sprite.setPosY(curY);

        if(!dragged) {
            if (Mathp.isPointInRect(sprite.locX, locY, sprite.sizX, sizeY, input.getMouseX(), input.getMouseY())) {
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
        if(dragged) {
            double y = input.getMouseY();
            double perY = (float) (sizeY - sprite.sizX) / (maxVal);
            double startY = locY + sizeY/2.0;
            y -= startY;
            val = (int)(-y/perY);
            if(val < 0) val = 0;
            if(val > maxVal) val = maxVal;
            if (!input.isKeyHolded(I_MOUSE_BUTTON_LEFT)) {
                dragged = false;
                AudioHelper.playSoundInterface(activateSound);
            }
        }

        return false;
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

    public void setMax(int i) {
        maxVal = i;
        if(val > maxVal) {
            val = maxVal;
            oldVal = maxVal;
        } if(maxVal < 0){
            val = 0;
            oldVal = 0;
        }
    }

    public int getVal() {
        return oldVal == val ? -1 : (int) val;
    }

    public int getValNormal() {
        return (int)val;
    }
}
