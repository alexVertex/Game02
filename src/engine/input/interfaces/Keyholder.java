package engine.input.interfaces;

import engine.core.Mathp;
import engine.helpers.AudioHelper;
import engine.helpers.SpriteHelper;
import engine.helpers.TextHelper;
import engine.input.MainInput;
import engine.render.Font;
import engine.render.Label;
import engine.render.Sprite;

import static engine._SetsStrings.*;

public class Keyholder extends Element{

    public Keyholder(String texture, int x, int y, int z,double sx, double sy){
        sprite = new Sprite(texture, x,y,z,sx,sy,I_CAMERA_AFFECT_NO);
        createModel();
    }

    boolean ready = false;
    boolean waitInput = false;
    String activateSound;
    Label textIn;
    int key;

    public Keyholder(String texture, int x, int y, int z, double sx, double sy, Font font, int key, String activate){
        sprite = new Sprite(texture, x,y,z,sx,sy,I_CAMERA_AFFECT_NO);
        createModel();
        textIn = new Label(font,MainInput.keyName(key), (float) (sprite.locX-sx/2+I_ELEMENT_KEYHOLDER_OFFSET_X), sprite.locY,sprite.zOrder+1, Label.LEFT);
        activateSound = activate;
        this.key = key;
    }

    @Override
    public boolean work(MainInput input) {
        if(waitInput){
            if(ready) {
                int last = input.getLastKeyReleased();
                if (last != -1) {
                    if (last != I_ELEMENT_KEYHOLDER_CANCEL_CHANGE_KEYCODE) {
                        textIn.changeText(MainInput.keyName(last));
                    }
                    waitInput = false;
                    ready = false;
                    key = last;
                }
            } else {
                changeState(I_ELEMENT_STATE_HOVERED);
                ready = true;
            }
        } else {
            if (Mathp.isPointInRect(sprite.locX, sprite.locY, sprite.sizX, sprite.sizY, input.getMouseX(), input.getMouseY())) {
                if (input.isKeyHolded(I_MOUSE_BUTTON_LEFT)) {
                    changeState(I_ELEMENT_STATE_PRESSED);
                } else {
                    if(state == I_ELEMENT_STATE_PRESSED){
                        waitInput = true;
                    }
                    changeState(I_ELEMENT_STATE_HOVERED);
                }
            } else {
                changeState(I_ELEMENT_STATE_ENABLED);
            }
        }
        return waitInput;
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

    public int getKey() {
        return key;
    }
}
