package engine.helpers;

import engine.render.Font;

import java.util.HashMap;

import static engine._SetsStrings.*;

public class FontHelper {
    public static HashMap<String, Font> fontHashMap = new HashMap<>();

    public static void loadFonts(){
        fontHashMap.put(S_FONT_MAIN_NAME, new Font(S_FONT_MAIN_TEXTURE));
        fontHashMap.put(S_FONT_SUPPORT_NAME, new Font(S_FONT_SUPPORT_TEXTURE));
    }

    public static Font getFont(String name){
        return fontHashMap.getOrDefault(name,null);
    }
}
