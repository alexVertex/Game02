package engine.render;

import engine.window.Window;

import static engine._SetsStrings.*;

public class Cursor {

    private static final String CURSOR_PATH = P_CURSOR_PATH;
    Sprite sprite;
    double hotX, hotY;
    public Cursor(String name, double sizeX, double sizeY, double hotX, double hotY){
        sprite = new Sprite(CURSOR_PATH+name,0,0,0,sizeX,sizeY,I_CAMERA_AFFECT_NO);
        this.hotX = hotX;
        this.hotY = hotY;
    }

    public void render() {
        sprite.shiftX = (float) (Window.mainInput.getMouseX()+hotX)/Window.getWidth()*2;
        sprite.shiftY = (float) (Window.mainInput.getMouseY()-hotY)/Window.getHeight()*2;
        sprite.drawQuad();
    }
}
