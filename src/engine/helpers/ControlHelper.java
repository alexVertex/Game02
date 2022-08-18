package engine.helpers;

import static engine._SetsStrings.*;
import static org.lwjgl.glfw.GLFW.*;

public class ControlHelper {
    public static final int ESC_MENU = GLFW_KEY_ESCAPE;
    public static final int OK_MENU = GLFW_KEY_ENTER;

    public static int MOVE_UP = GLFW_KEY_W;
    public static int MOVE_DOWN = GLFW_KEY_S;
    public static int MOVE_LEFT = GLFW_KEY_A;
    public static int MOVE_RIGHT = GLFW_KEY_D;

    public static int ACTIVATE = GLFW_KEY_SPACE;
    public static int SWITCH_ACTIVATE = GLFW_KEY_Q;

    public static int MOVE_RUN = GLFW_KEY_LEFT_SHIFT;
    public static int MOVE_SNEAK = GLFW_KEY_LEFT_CONTROL;

    public static int CAMP_MENU = GLFW_KEY_TAB;
    public static final int CAMP_INVENTORY = GLFW_KEY_F1;
    public static final int CAMP_SPELS = GLFW_KEY_F2;
    public static final int CAMP_STATS = GLFW_KEY_F3;
    public static final int CAMP_QUESTS = GLFW_KEY_F4;
    public static final int CAMP_TRANSIT = GLFW_KEY_F5;

    public static int HIT = I_MOUSE_BUTTON_LEFT;
    public static int GUARD = I_MOUSE_BUTTON_RIGHT;

    public static int SWITCH_WEAPON = GLFW_KEY_R;
    public static int SWITCH_SHIELD = GLFW_KEY_F;
    public static int SWITCH_ITEM = GLFW_KEY_Z;
    public static int SWITCH_SPELL1 = GLFW_KEY_X;
    public static int SWITCH_SPELL2 = GLFW_KEY_C;

    public static int USE_ITEM = GLFW_KEY_1;
    public static int USE_SPELL1 = GLFW_KEY_2;
    public static int USE_SPELL2 = GLFW_KEY_3;

    public static final int[] codes = {MOVE_UP,MOVE_DOWN,MOVE_LEFT,MOVE_RIGHT,MOVE_RUN,MOVE_SNEAK,ACTIVATE,SWITCH_ACTIVATE,HIT,GUARD,
            SWITCH_WEAPON,SWITCH_SHIELD,SWITCH_ITEM,SWITCH_SPELL1,SWITCH_SPELL2,USE_ITEM,USE_SPELL1,USE_SPELL2,CAMP_MENU};

    public static final int GAME_RESTART = GLFW_KEY_SPACE;

    public static void changeCode(int i, int keyCode) {
        codes[i] = keyCode;
        switch (i){
            case 0 -> MOVE_UP = keyCode;
            case 1 -> MOVE_DOWN = keyCode;
            case 2 -> MOVE_LEFT = keyCode;
            case 3 -> MOVE_RIGHT = keyCode;
            case 4 -> MOVE_RUN = keyCode;
            case 5 -> MOVE_SNEAK = keyCode;
            case 6 -> ACTIVATE = keyCode;
            case 7 -> SWITCH_ACTIVATE = keyCode;
            case 8 -> HIT = keyCode;
            case 9 -> GUARD = keyCode;
            case 10 -> SWITCH_WEAPON = keyCode;
            case 11 -> SWITCH_SHIELD = keyCode;
            case 12 -> SWITCH_ITEM = keyCode;
            case 13 -> SWITCH_SPELL1 = keyCode;
            case 14 -> SWITCH_SPELL2 = keyCode;
            case 15 -> USE_ITEM = keyCode;
            case 16 -> USE_SPELL1 = keyCode;
            case 17 -> USE_SPELL2 = keyCode;
            case 18 -> CAMP_MENU = keyCode;
        }
    }
}
