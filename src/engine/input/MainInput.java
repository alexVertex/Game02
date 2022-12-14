package engine.input;

import engine.window.Window;

import static engine._SetsStrings.I_FIRST_KEY_CODE;
import static engine._SetsStrings.I_LAST_MOUSE_BUTTON;
import static org.lwjgl.glfw.GLFW.*;

public class MainInput {
    private final long window;
    private final boolean[] keys;
    private final boolean[] keysOld;
    private double dWheel;
    static double[] xpos = {0};
    static double[] ypos = {0};

    public MainInput(long window){
        this.window = window;
        this.keys = new boolean[GLFW_KEY_LAST];
        this.keysOld = new boolean[GLFW_KEY_LAST];
        for(int i = 0; i < keys.length;i++){
            keys[i] = false;
            keysOld[i]  = false;
        }
    }

    public static double getX() {
        return xpos[0];
    }

    public static double getY() {
        return Window.getHeight()-ypos[0];
    }

    public boolean isKeyDown(int key){
        return glfwGetKey(window,key) == 1;
    }

    public boolean isMouseDown(int button){
        return glfwGetMouseButton(window,button) == 1;
    }

    public boolean isKeyJustPressed(int key){
        return keys[key] && !keysOld[key];
    }

    public boolean isKeyJustReleased(int key){
        return !keys[key] && keysOld[key];
    }
    public boolean isKeyHolded(int key){
        return keys[key];
    }
    public double getdWheel(){
        double save = dWheel;
        dWheel = 0;
        return save;
    }

    private static final int[] left = {-191,-112,0,128};
    private static final int[] top = {-144,-84,0,96};
    private static final int[] right = {831,912,1024,1152};
    private static final int[] bot = {624,684,768,864};

    public void setCursorInWindow(){
        if(xpos[0] < left[Window.getSets().vidRes]) xpos[0] = left[Window.getSets().vidRes];
        if(ypos[0] < top[Window.getSets().vidRes]) ypos[0] = top[Window.getSets().vidRes];
        if(xpos[0] > right[Window.getSets().vidRes]) xpos[0] = right[Window.getSets().vidRes];
        if(ypos[0] > bot[Window.getSets().vidRes]) ypos[0] = bot[Window.getSets().vidRes];
        glfwSetCursorPos	(	window,	xpos[0], ypos[0]);
    }

    private int lastKeyReleased;

    public void update(){
        lastKeyReleased = -1;
        glfwSetScrollCallback(window, (windowHandle, xoffset, yoffset) -> dWheel = yoffset);
        glfwGetCursorPos(window, xpos, ypos);
        setCursorInWindow();
        xpos[0]-=Window.getWidth()/2;
        ypos[0]+=Window.getHeight()/2;
        for(int i = I_FIRST_KEY_CODE; i < keys.length;i++){
            keysOld[i] = keys[i];
            keys[i] = isKeyDown(i);
            if(isKeyJustReleased(i)){
                lastKeyReleased = i;
            }
        }
        for(int i = 0; i < I_LAST_MOUSE_BUTTON;i++){
            keysOld[i] = keys[i];
            keys[i] = isMouseDown(i);
            if(isKeyJustPressed(i)){
                lastKeyReleased = i;
            }
        }
    }

    public double getMouseX() {
        return xpos[0];
    }

    public double getMouseY() {
        return Window.getHeight()-ypos[0];
    }

    public int getLastKeyReleased() {
        return lastKeyReleased;
    }

    public static boolean isLetter(int keyCode){
        return keyCode == 39 || keyCode == 44 || keyCode == 46 || keyCode == 59 || (keyCode >= 65 && keyCode <= 91) || keyCode == 93 || keyCode == 96;
    }
    public static String keyName(int keyCode){
        return switch (keyCode) {
            case 0 -> "??????";
            case 1 -> "??????";
            case 2 -> "??????";
            case 32 -> "????????????";
            case 39 -> "??";
            case 44 -> "??";
            case 45 -> "-";
            case 46 -> "??";
            case 47 -> ".";
            case 48 -> "0";
            case 49 -> "1";
            case 50 -> "2";
            case 51 -> "3";
            case 52 -> "4";
            case 53 -> "5";
            case 54 -> "6";
            case 55 -> "7";
            case 56 -> "8";
            case 57 -> "9";
            case 59 -> "??";
            case 61 -> "=";
            case 65 -> "??";
            case 66 -> "??";
            case 67 -> "??";
            case 68 -> "??";
            case 69 -> "??";
            case 70 -> "??";
            case 71 -> "??";
            case 72 -> "??";
            case 73 -> "??";
            case 74 -> "??";
            case 75 -> "??";
            case 76 -> "??";
            case 77 -> "??";
            case 78 -> "??";
            case 79 -> "??";
            case 80 -> "??";
            case 81 -> "??";
            case 82 -> "??";
            case 83 -> "??";
            case 84 -> "??";
            case 85 -> "??";
            case 86 -> "??";
            case 87 -> "??";
            case 88 -> "??";
            case 89 -> "??";
            case 90 -> "??";
            case 91 -> "??";
            case 92 -> "\\";
            case 93 -> "??";
            case 96 -> "??";
            case 256 -> "????????????";
            case 257 -> "????????";
            case 262 -> "????????????";
            case 263 -> "??????????";
            case 264 -> "????????";
            case 265 -> "??????????";
            case 258 -> "??????????????????";
            case 280 -> "????????";
            case 290 -> "??1";
            case 291 -> "??2";
            case 292 -> "??3";
            case 293 -> "??4";
            case 294 -> "??5";
            case 295 -> "??6";
            case 296 -> "??7";
            case 297 -> "??8";
            case 298 -> "??9";
            case 299 -> "??10";
            case 300 -> "??11";
            case 301 -> "??12";
            case 340 -> "??.????????";
            case 341 -> "??.??????????????";
            case 342 -> "??.????????";
            case 344 -> "??.????????";
            case 345 -> "??.??????????????";
            case 346 -> "??.????????";

            default -> keyCode+"";
        };
    }
}
