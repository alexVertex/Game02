package game.inter;

import engine.helpers.*;
import engine.render.Label;
import engine.render.Sprite;
import engine.window.Actor;

import static engine._SetsStrings.*;
import static engine._TextString.*;

public class Describe extends Actor {

    Sprite[] sprites = {null,null,null,null,null};
    Label head;
    Label[][] body = new Label[I_INTERFACE_DESCRIBE_DATA_SIZE][2];
    int z;
    float w, h;
    public Describe(String textureLine, String textureBack, float X, float Y, int Z, float W, float H, String font, String text){
        sprites[I_INTERFACE_BOX_BORDER_TOP] = new Sprite(textureLine,X,Y-H/2,Z,W,I_INTERFACE_BOX_BORDER_SIZE,I_CAMERA_AFFECT_NO);
        sprites[I_INTERFACE_BOX_BORDER_BOT] = new Sprite(textureLine,X,Y+H/2,Z,W,I_INTERFACE_BOX_BORDER_SIZE,I_CAMERA_AFFECT_NO);
        sprites[I_INTERFACE_BOX_BORDER_LEFT] = new Sprite(textureLine,X-W/2,Y,Z,I_INTERFACE_BOX_BORDER_SIZE,H,I_CAMERA_AFFECT_NO);
        sprites[I_INTERFACE_BOX_BORDER_RIGHT] = new Sprite(textureLine,X+W/2,Y,Z,I_INTERFACE_BOX_BORDER_SIZE,H,I_CAMERA_AFFECT_NO);
        sprites[I_INTERFACE_BOX_BACKGROUND] = new Sprite(textureBack,X,Y,Z-1,W,H,0,0,0,F_INTERFACE_DESCRIBE_BACKGROUND_ALPHA,false,I_CAMERA_AFFECT_NO);
        head = new Label(FontHelper.getFont(font),text,X,Y+H/2,Z+1,Label.CENTER);
        for(int i = 0; i < body.length;i++){
            body[i][0] = new Label(FontHelper.getFont(font)," ",X-W/2+I_INTERFACE_DESCRIBE_DATA_OFFSET_X,Y+H/2-I_INTERFACE_DESCRIBE_DATA_OFFSET_Y_START-I_INTERFACE_DESCRIBE_DATA_OFFSET_Y_PER*i,Z+1,Label.LEFT);
            body[i][1] = new Label(FontHelper.getFont(font)," ",X+W/2-I_INTERFACE_DESCRIBE_DATA_OFFSET_X,Y+H/2-I_INTERFACE_DESCRIBE_DATA_OFFSET_Y_START-I_INTERFACE_DESCRIBE_DATA_OFFSET_Y_PER*i,Z+1,Label.RIGHT);
        }
        z = Z;
        w = W;
        h = H;
    }
    @Override
    public void init() {
        TextHelper.addText(head);
        for(Sprite el : sprites){
            SpriteHelper.addSprite(el);
        }
        for (Label[] labels : body) {
            TextHelper.addText(labels[0]);
            TextHelper.addText(labels[1]);
        }
    }
    @Override
    public void dispose() {
        TextHelper.disposeText(head);
        for(Sprite el : sprites){
            SpriteHelper.removeSprite(el);
        }
        for (Label[] labels : body) {
            TextHelper.disposeText(labels[0]);
            TextHelper.disposeText(labels[1]);
        }
    }

    @Override
    public void hide() {
        head.hide();
        for(Sprite el : sprites){
            el.alpha = 0;
        }
        for (Label[] labels : body) {
            labels[0].hide();
            labels[1].hide();
        }
    }

    @Override
    public void show() {
        head.show();
        for(Sprite el : sprites){
            el.alpha = 1;
        }
        for (Label[] labels : body) {
            labels[0].show();
            labels[1].show();
        }
    }

    @Override
    public void use() {

    }

    public void setText(String[][] description) {
        setText(description,false);
    }

    public void setText(String[][] description, boolean changeCost) {
        head.changeText(description[0][1]);
        for(int i = 1; i < body.length;i++) {
            if(i < description.length) {
                if (description[i][0].equals(S_INTERFACE_DESCRIBE_LEFT_DESCRIPTION)) {
                    body[i - 1][0].changeText(description[i][1], I_LABEL_TYPE_WITH_RETURNS, (int) (w - I_INTERFACE_DESCRIBE_DATA_OFFSET_Y_PER));
                    body[i-1][1].changeText(" ");
                } else if (changeCost && description[i][0].equals(S_INTERFACE_DESCRIBE_LEFT_COST)) {
                    body[i - 1][0].changeText(description[i][0], 0, 0);
                    int cost = Integer.parseInt(description[i][1]);
                    int goldGain = (int) (cost * StarterHelper.game.getPlayer().trade/F_GAMEPLAY_TRADES_KOEF);
                    body[i - 1][1].changeText(goldGain+"");
                } else {
                    body[i - 1][0].changeText(description[i][0], 0, 0);
                    body[i - 1][1].changeText(description[i][1]);
                }
            } else {
                body[i-1][0].changeText(" ");
                body[i-1][1].changeText(" ");
            }
        }
    }

    public void setPos(double x, double y, boolean cursorPinned) {
        int lines = 0;
        for (Label[] labels : body) {
            if (!labels[0].getText().equals(" "))
                lines += labels[0].getLines();
        }
        h = lines*I_INTERFACE_DESCRIBE_DATA_OFFSET_Y_PER+I_INTERFACE_DESCRIBE_DATA_OFFSET_Y_START;
        float add = h * F_INTERFACE_DESCRIBE_HEIGHT_MULT;
        sprites[2].setSizeY(h+add);
        sprites[3].setSizeY(h+add);
        sprites[4].setSizeY(h+add);
        if(cursorPinned){
            x += w/2+I_INTERFACE_DESCRIBE_CURSOR_OFFSET_X;
            y -= h/2+I_INTERFACE_DESCRIBE_CURSOR_OFFSET_Y;
        }
        sprites[0].setPos(x,y-h/2);
        sprites[1].setPos(x,y+h/2);
        sprites[2].setPos(x-w/2,y);
        sprites[3].setPos(x+w/2,y);
        sprites[4].setPos(x,y);
        head.setPos(x,y+h/2-I_INTERFACE_DESCRIBE_HEAD_OFFSET);
        for(int i = 0; i < body.length;i++){
            body[i][0].setPos(x-w/2+I_INTERFACE_DESCRIBE_DATA_OFFSET_X,y+h/2-I_INTERFACE_DESCRIBE_DATA_OFFSET_Y_START-I_INTERFACE_DESCRIBE_DATA_OFFSET_Y_PER*i);
            body[i][1].setPos(x+w/2-I_INTERFACE_DESCRIBE_DATA_OFFSET_X,y+h/2-I_INTERFACE_DESCRIBE_DATA_OFFSET_Y_START-I_INTERFACE_DESCRIBE_DATA_OFFSET_Y_PER*i);
        }
    }

    @Override
    public void doStuff(String command, String params) {

    }
}
