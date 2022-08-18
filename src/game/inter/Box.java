package game.inter;

import engine.render.Label;
import engine.render.Sprite;
import engine.window.Actor;
import engine.helpers.FontHelper;
import engine.helpers.SpriteHelper;
import engine.helpers.TextHelper;

import static engine._SetsStrings.*;

public class Box extends Actor {

    static int borderSize = I_INTERFACE_BOX_BORDER_SIZE;
    Sprite[] sprites = {null,null,null,null,null};
    Label head;
    public Box(String textureLine, String textureBack, float X, float Y, int Z, float W, float H, String font, String text){
        sprites[I_INTERFACE_BOX_BORDER_TOP] = new Sprite(textureLine,X,Y-H/2,Z,W,borderSize,I_CAMERA_AFFECT_NO);
        sprites[I_INTERFACE_BOX_BORDER_BOT] = new Sprite(textureLine,X,Y+H/2,Z,W,borderSize,I_CAMERA_AFFECT_NO);
        sprites[I_INTERFACE_BOX_BORDER_LEFT] = new Sprite(textureLine,X-W/2,Y,Z,borderSize,H,I_CAMERA_AFFECT_NO);
        sprites[I_INTERFACE_BOX_BORDER_RIGHT] = new Sprite(textureLine,X+W/2,Y,Z,borderSize,H,I_CAMERA_AFFECT_NO);
        sprites[I_INTERFACE_BOX_BACKGROUND] = new Sprite(textureBack,X,Y,Z-1,W,H,0,0,0,F_INTERFACE_BOX_BACKGROUND_ALPHA,false,I_CAMERA_AFFECT_NO);
        head = new Label(FontHelper.getFont(font),text,X,Y+H/2-I_INTERFACE_BOX_HEAD_OFFSET,Z+1,Label.CENTER);
    }
    public Box(String textureLine, String textureBack, float X, float Y, int Z, float W, float H){
        sprites[I_INTERFACE_BOX_BORDER_TOP] = new Sprite(textureLine,X,Y-H/2,Z,W,2,I_CAMERA_AFFECT_NO);
        sprites[I_INTERFACE_BOX_BORDER_BOT] = new Sprite(textureLine,X,Y+H/2,Z,W,2,I_CAMERA_AFFECT_NO);
        sprites[I_INTERFACE_BOX_BORDER_LEFT] = new Sprite(textureLine,X-W/2,Y,Z,2,H,I_CAMERA_AFFECT_NO);
        sprites[I_INTERFACE_BOX_BORDER_RIGHT] = new Sprite(textureLine,X+W/2,Y,Z,2,H,I_CAMERA_AFFECT_NO);
        sprites[I_INTERFACE_BOX_BACKGROUND] = new Sprite(textureBack,X,Y,Z-1,W,H,I_CAMERA_AFFECT_NO);
    }
    @Override
    public void init() {
        TextHelper.addText(head);
        for(Sprite el : sprites){
            SpriteHelper.addSprite(el);
        }
    }
    @Override
    public void dispose() {
        TextHelper.disposeText(head);
        for(Sprite el : sprites){
            SpriteHelper.removeSprite(el);
        }
    }

    @Override
    public void hide() {
        head.hide();
        for(Sprite el : sprites){
            el.alpha = 0;
        }
    }

    @Override
    public void show() {
        head.show();
        for(Sprite el : sprites){
            el.alpha = 1;
        }
    }

    @Override
    public void use() {

    }

    public void changeHead(String name) {
        head.changeText(name);
    }

    @Override
    public void doStuff(String command, String params) {

    }

    public void changeBack(String s) {
        sprites[I_INTERFACE_BOX_BACKGROUND].texture = s;
    }
}
