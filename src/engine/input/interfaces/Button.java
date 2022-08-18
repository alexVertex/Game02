package engine.input.interfaces;

import engine.core.Mathp;
import engine.render.Font;
import engine.render.Label;
import engine.render.Sprite;
import engine.input.MainInput;
import engine.helpers.AudioHelper;
import engine.helpers.SpriteHelper;
import engine.helpers.TextHelper;

import static engine._SetsStrings.*;

public class Button extends Element{
    public Button(String texture, int x, int y, int z,double sx, double sy){
        sprite = new Sprite(texture, x,y,z,sx,sy,I_CAMERA_AFFECT_NO);
        createModel();
    }

    String activateSound;
    Label textIn;
    public Button(String texture, int x, int y, int z, double sx, double sy, Font font, String text,String activate){
        sprite = new Sprite(texture, x,y,z,sx,sy,I_CAMERA_AFFECT_NO);
        createModel();
        textIn = new Label(font,text, sprite.locX, sprite.locY,sprite.zOrder+1, Label.CENTER);
        activateSound = activate;
    }

    boolean ready = false;
    private int timer = 0;
    private boolean first = true;

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
        if(state == I_ELEMENT_STATE_PRESSED){
            timer--;
            if(timer == -1){
                timer = first ? I_BUTTON_REPEAT_PRESSED_TIMEWAIT : 1;
            }
        } else {
            first = true;
            timer = 0;
        }
        return false;
    }

    @Override
    public boolean worked() {
        boolean worked = ready && state == I_ELEMENT_STATE_HOVERED;
        if(worked) {
            ready = false;
            AudioHelper.playSoundInterface(activateSound);
        }
        return worked;
    }

    @Override
    public void dispose() {
        SpriteHelper.removeSprite(sprite);
        TextHelper.disposeText(textIn);
    }

    @Override
    public void add() {
        TextHelper.addText(textIn);
        SpriteHelper.addSprite(sprite);
    }

    public boolean pressed() {
        return (state == I_ELEMENT_STATE_PRESSED && timer == 0) || (worked());
    }
}
