package engine.core;

import engine.audio.Audio;
import engine.script.ScriptMain;
import engine.window.Window;
import engine.helpers.FontHelper;
import engine.helpers.StarterHelper;

public class Starter {
    public static void main(String[] args){
        init();
        loop();
        destroy();
    }
    private static void init(){
        ScriptMain.loadScripts();
        FontHelper.loadFonts();
        Audio.init();
        Window.init();
        StarterHelper.startGame();
    }

    private static void loop(){
        Window.loop();
    }
    private static void destroy() {
        Window.destroy();
        Audio.destroy();
    }
}
