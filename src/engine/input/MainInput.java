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
            case 0 -> "ЛКМ";
            case 1 -> "ПКМ";
            case 2 -> "СКМ";
            case 32 -> "Пробел";
            case 39 -> "Э";
            case 44 -> "Б";
            case 45 -> "-";
            case 46 -> "Ю";
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
            case 59 -> "Ж";
            case 61 -> "=";
            case 65 -> "Ф";
            case 66 -> "И";
            case 67 -> "С";
            case 68 -> "В";
            case 69 -> "У";
            case 70 -> "А";
            case 71 -> "П";
            case 72 -> "Р";
            case 73 -> "Ш";
            case 74 -> "О";
            case 75 -> "Л";
            case 76 -> "Д";
            case 77 -> "Ь";
            case 78 -> "Т";
            case 79 -> "Щ";
            case 80 -> "З";
            case 81 -> "Й";
            case 82 -> "К";
            case 83 -> "Ы";
            case 84 -> "Е";
            case 85 -> "Г";
            case 86 -> "М";
            case 87 -> "Ц";
            case 88 -> "Ч";
            case 89 -> "Н";
            case 90 -> "Я";
            case 91 -> "Х";
            case 92 -> "\\";
            case 93 -> "Ъ";
            case 96 -> "Ё";
            case 256 -> "Отмена";
            case 257 -> "Ввод";
            case 262 -> "Вправо";
            case 263 -> "Влево";
            case 264 -> "Вниз";
            case 265 -> "Вверх";
            case 258 -> "Табулятор";
            case 280 -> "Капс";
            case 290 -> "Ф1";
            case 291 -> "Ф2";
            case 292 -> "Ф3";
            case 293 -> "Ф4";
            case 294 -> "Ф5";
            case 295 -> "Ф6";
            case 296 -> "Ф7";
            case 297 -> "Ф8";
            case 298 -> "Ф9";
            case 299 -> "Ф10";
            case 300 -> "Ф11";
            case 301 -> "Ф12";
            case 340 -> "Л.Шифт";
            case 341 -> "Л.Контрол";
            case 342 -> "Л.Альт";
            case 344 -> "П.Шифт";
            case 345 -> "П.Контрол";
            case 346 -> "П.Альт";

            default -> keyCode+"";
        };
    }
}
