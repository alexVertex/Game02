package engine.script;

import java.io.File;
import java.util.HashMap;

import static engine._SetsStrings.*;

public class ScriptMain {

    private static final String SCRIPT_PATH = P_SCRIPTS_PATH;

    private final static HashMap<String, Script> scripts = new HashMap<>();

    public static void loadScripts(){
        File dir = new File(SCRIPT_PATH);
        File[] arrFiles = dir.listFiles();
        assert arrFiles != null;
        for(File el : arrFiles) {
            Script script = new Script(el.getPath());
            scripts.put(el.getName(),script);
        }
    }

    public static Script getScript(String name){
        return scripts.getOrDefault(name,null);
    }

    static String getType(String word){
        String type = S_SCRIPT_TYPE_ACTORVAR;
        if(isMath(word)){
            type = S_SCRIPT_TYPE_MATH;
        } else if(canParse(word)){
            type = S_SCRIPT_TYPE_FLOAT;
        }
        return type;
    }

    static boolean isMath(String word) {
        return word.equals(S_SCRIPT_MATH_DIV) || word.equals(S_SCRIPT_MATH_MUL) || word.equals(S_SCRIPT_MATH_MINUS) || word.equals(S_SCRIPT_MATH_PLUS);
    }

    static boolean canParse(String word){
        try {
            Float.parseFloat(word);
        } catch (NumberFormatException e){
            return false;
        }
        return true;
    }
}
