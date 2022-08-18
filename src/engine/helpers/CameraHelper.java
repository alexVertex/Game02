package engine.helpers;

import engine.render.Camera;

public class CameraHelper {
    public static double getX() {
        return Camera.posX;
    }

    public static double getY() {
        return Camera.posY;
    }

    public static void setPos(float x, float y) {
        Camera.posX = (int) x;
        Camera.posY = (int) y;
    }
}
