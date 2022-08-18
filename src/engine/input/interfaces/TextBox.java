package engine.input.interfaces;

import engine.core.Mathp;
import engine.helpers.SpriteHelper;
import engine.helpers.TextHelper;
import engine.input.MainInput;
import engine.render.Font;
import engine.render.Label;
import engine.render.Sprite;

import static engine._SetsStrings.*;

public class TextBox extends Element{

    private final String activateSound;
    private final Label textIn;
    private String text;
    private boolean dragged;
    private final Font font;
    public TextBox(String texture, int x, int y, int z, double sx, double sy, Font font, String text, String activate){
        sprite = new Sprite(texture, x,y,z,sx,sy,I_CAMERA_AFFECT_NO);
        createModel();
        textIn = new Label(font,text, sprite.locX, sprite.locY,sprite.zOrder+1, Label.CENTER);
        activateSound = activate;
        this.text = text;
        this.font = font;
    }

    @Override
    public boolean work(MainInput input) {
        if(!dragged) {
            if (Mathp.isPointInRect(sprite.locX, sprite.locY, sprite.sizX, sprite.sizY, input.getMouseX(), input.getMouseY())) {
                if (input.isKeyHolded(I_MOUSE_BUTTON_LEFT)) {
                    changeState(I_ELEMENT_STATE_PRESSED);
                    dragged = true;
                } else {
                    changeState(I_ELEMENT_STATE_HOVERED);
                }
            } else {
                changeState(I_ELEMENT_STATE_ENABLED);
            }
        } else {
            if (!Mathp.isPointInRect(sprite.locX, sprite.locY, sprite.sizX, sprite.sizY, input.getMouseX(), input.getMouseY())){
                if(input.isKeyJustReleased(I_MOUSE_BUTTON_LEFT)){
                    dragged = false;
                    return false;
                }
            }
            changeState(I_ELEMENT_STATE_HOVERED);
            int last = input.getLastKeyReleased();
            if(last == I_ELEMENT_KEYHOLDER_CANCEL_CHANGE_KEYCODE){
                dragged = false;
            }
            if(last == I_ELEMENT_TEXTBOX_BACKSPACE_KEYCODE){
                if(text.length() > 0){
                    text = text.substring(0,text.length()-1);
                    textIn.changeText(text);
                }
            }
            if(MainInput.isLetter(last)){
                String name = MainInput.keyName(last);
                if(!input.isKeyDown(I_ELEMENT_TEXTBOX_LSHIFT_KEYCODE) && !input.isKeyDown(I_ELEMENT_TEXTBOX_RSHIFT_KEYCODE)){
                    name = name.toLowerCase();
                }
                name = text + name;
                float width = textIn.getTextWide(name);
                if(width < sprite.sizX) {
                    text = name;
                    textIn.changeText(name);
                }
            }
        }
        return dragged;
    }

    @Override
    public boolean worked() {
        return false;
    }

    @Override
    public void dispose() {
        SpriteHelper.removeSprite(sprite);
        TextHelper.disposeText(textIn);
    }

    @Override
    public void add() {
        SpriteHelper.addSprite(sprite);
        TextHelper.addText(textIn);
    }

    public String getText() {
        return text;
    }
}
