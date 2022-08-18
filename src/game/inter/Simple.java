package game.inter;

import engine.render.Sprite;
import engine.window.Actor;
import engine.helpers.SpriteHelper;

import static engine._SetsStrings.*;

public class Simple extends Actor {
    float remAlpha = 1;
    Sprite sprite;
    public Simple(String texture, float x,float y, int z, float sx, float sy){
        sprite = new Sprite(texture,x,y,z,sx,sy,I_CAMERA_AFFECT_NO);
        locX = x;
    }

    public Simple(String texture, float x,float y, int z, float sx, float sy, float r, float g, float b) {
        super();
        sprite = new Sprite(texture,x,y,z,sx,sy,r,g,b,1,false,I_CAMERA_AFFECT_NO);
        locX = x;
    }

    public Simple(String texture, float x,float y, int z, float sx, float sy, float r, float g, float b,float a) {
        super();
        sprite = new Sprite(texture,x,y,z,sx,sy,r,g,b,a,false,I_CAMERA_AFFECT_NO);
        remAlpha = a;
        locX = x;
    }
    public Simple(String texture, float x,float y, int z, float sx, float sy, float r, float g, float b,float a,boolean t) {
        super();
        sprite = new Sprite(texture,x,y,z,sx,sy,r,g,b,a,t,I_CAMERA_AFFECT_NO);
        remAlpha = a;
        locX = x;
        locY = y;
    }
    @Override
    public void init() {
        SpriteHelper.addSprite(sprite);
    }

    @Override
    public void doStuff(String command, String params) {

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
        sprite.alpha = remAlpha;
    }

    @Override
    public void use() {

    }

    public void setSizeX(double x) {
        sprite.setSizeX(x);
    }
    @Override
    public void setX(double x) {
        sprite.setPosX(x);
    }
    @Override
    public void setY(double y) {
        sprite.setPosY(y);
    }
    public void setAgnle(double angle) {
        sprite.setAngle(angle);
    }

    public void setColor(float r, float g, float b, float a) {
        sprite.alpha = a;
        sprite.red = r;
        sprite.green = g;
        sprite.blue = b;
        remAlpha = a;
    }

    public void setTexture(String texture) {
        sprite.texture = texture;
    }


    public void setCuts(float partX, float partY, float v) {
        sprite.leftTex = partX * v;
        sprite.rightTex = (1+partX) * v;
        sprite.topTex = partY * v;
        sprite.botTex = (1+partY) * v;
        sprite.replaceModelTID();
    }

    public void setCutsX(int partX, float v) {
        sprite.leftTex = partX * v;
        sprite.rightTex = (1+partX) * v;
        sprite.replaceModelTID();
    }
}
