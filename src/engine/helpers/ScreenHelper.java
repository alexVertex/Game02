package engine.helpers;

import engine.window.Screen;
import engine.window.Window;

public class ScreenHelper {
    public static void setScreenActive(Screen newScreen){
        Window.currentScreen = newScreen;
    }
    public static Screen getScreenActive() {
        return Window.currentScreen;
    }
}
