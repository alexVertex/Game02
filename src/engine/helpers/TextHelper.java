package engine.helpers;

import engine.render.Label;
import engine.render.Sprite;

import static engine._SetsStrings.*;

public class TextHelper {
    public static void addText(Label head) {
        if(head == null) return;
        for(Sprite el : head.sprites){
            SpriteHelper.addSprite(el);
            el.red = F_TEXT_DEFAULT_COLOR_RED;
            el.green = F_TEXT_DEFAULT_COLOR_GREEN;
            el.blue = F_TEXT_DEFAULT_COLOR_BLUE;
        }
        head.red = F_TEXT_DEFAULT_COLOR_RED;
        head.green = F_TEXT_DEFAULT_COLOR_GREEN;
        head.blue = F_TEXT_DEFAULT_COLOR_BLUE;
    }

    public static void disposeText(Label label) {
        if(label == null) return;
        for(Sprite el : label.sprites){
            SpriteHelper.removeSprite(el);
        }
    }

    public static void setDefColor(Label label){
        if(label == null) return;
        label.setColor(F_TEXT_DEFAULT_COLOR_RED,F_TEXT_DEFAULT_COLOR_GREEN,F_TEXT_DEFAULT_COLOR_BLUE);
    }
}
