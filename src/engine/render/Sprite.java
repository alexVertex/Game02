package engine.render;

import engine.window.Window;

import java.io.Serial;
import java.io.Serializable;

import static engine._SetsStrings.*;

public class Sprite implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    static Shader shader =  new Shader(P_SHADERS_FILENAME_EXE);
    private static float globalBrightness;

    public int locX, locY;
    public float shiftX, shiftY;
    public float shiftXCam, shiftYCam;
    public float otnX, otnY;
    public double sizX, sizY;
    public float red = 1, green = 1, blue = 1, alpha = 1;
    public float leftTex = 0, rightTex = 1, topTex = 0, botTex = 1;
    public float leftTexOld = 0, rightTexOld = 1, topTexOld = 0, botTexOld = 1;

    public String texture;
    private transient Model model;
    private float angle = 0;
    private final float cameraMult;
    public int zOrder;
    public Sprite(String tex, double lX, double lY, int lZ, double sX, double sY, float camera){
        texture = tex;
        locX = (int) lX;
        locY = (int) lY;
        sizX = sX;
        sizY = sY;
        zOrder = lZ;
        cameraMult = camera;
        createModel();
    }

    public Sprite(String tex, double lX, double lY, int lZ, double sX, double sY, float r, float g, float b, float a, boolean isCut, float camera){
        texture = tex;
        locX = (int) lX;
        locY = (int) lY;
        sizX = sX;
        sizY = sY;
        if(!isCut) {
            red = r;
            green = g;
            blue = b;
            alpha = a;
        } else{
            leftTex = r;
            topTex = g;
            rightTex = b;
            botTex = a;
        }
        zOrder = lZ;
        cameraMult = camera;
        createModel();
    }

    public void createModel(){
        leftTexOld = leftTex;
        topTexOld = topTex;
        rightTexOld = rightTex;
        botTexOld = botTex;
        otnX = (float) (sizX/(Window.getWidth()));
        otnY = (float) (sizY/Window.getHeight());
        shiftX = (locX)/Window.getWidth()*2;
        shiftY = (locY)/Window.getHeight()*2;
        float[] tex = new float[]{
                leftTex,topTex,
                rightTex,topTex,
                rightTex,botTex,
                leftTex,botTex,
        };
        model = new Model(FM_VERTICES_MASSIVE,tex,IM_INDICES_MASSIVE);
    }

    public static void setGlobalBrightness(float val){
        globalBrightness = F_SET_BRIGHTNESS_BASE+val*F_SET_BRIGHTNESS_MULT;
    }

    public void drawQuad(){
        shiftXCam = shiftX + cameraMult*((Camera.posX)/Window.getWidth())*2;
        shiftYCam = shiftY + cameraMult*((Camera.posY)/Window.getHeight())*2;
        float mult = Window.getSets().mult();
        if(shiftXCam*mult + otnX*mult < -1) return;
        if(shiftYCam*mult + otnY*mult < -1) return;
        if(shiftXCam*mult - otnX*mult > 1) return;
        if(shiftYCam*mult - otnY*mult > 1) return;
        if(alpha == 0) return;
        Textures.bind(texture);
        shader.bind();
        shader.setUniform("sampler",0);
        shader.setUniformF("red",red*globalBrightness);
        shader.setUniformF("green",green*globalBrightness);
        shader.setUniformF("blue",blue*globalBrightness);
        shader.setUniformF("alpha",alpha);
        shader.setUniformF("shiftY",shiftYCam*mult);
        shader.setUniformF("shiftX",shiftXCam*mult);
        shader.setUniformF("otnY",otnY*mult);
        shader.setUniformF("otnX",otnX*mult);
        shader.setUniformF("angle",angle);
        Window.timesRedraw++;
        model.render();
    }

    public void disposeModel() {
        model.dispose();
    }

    public void setPos(double x, double y) {
        shiftX = (float)((x)/Window.getWidth())*2;
        shiftY = (float)((y)/Window.getHeight())*2;
        locX = (int) x;
        locY = (int) y;
    }

    public void setAngle(double angle) {
        this.angle = (float) angle;
    }

    public void setPosX(double x) {
        shiftX = (float)((x)/Window.getWidth())*2;
        locX = (int) x;
    }

    public void setPosY(double y) {
        shiftY = (float)((y)/Window.getHeight())*2;
        locY = (int) y;
    }

    public void replaceModelTID() {
        if(leftTex != leftTexOld || rightTex != rightTexOld || topTex != topTexOld || botTex != botTexOld) {
            model.replaceTID(leftTex, topTex, rightTex, botTex);
            leftTexOld = leftTex;
            topTexOld = topTex;
            rightTexOld = rightTex;
            botTexOld = botTex;
        }
    }

    public void setSizeX(double x) {
        sizX = x;
        otnX = (float) (x/(Window.getWidth()));
    }

    public void setSizeY(double y) {
        sizY = y;
        otnY = (float) (y/(Window.getWidth()));
    }

    public double getAngle() {
        return angle;
    }

    public boolean emptyModel() {
        return model == null;
    }
}
