package engine.script;

import engine.helpers.StarterHelper;
import engine.window.Actor;

import java.lang.reflect.Field;

import static engine._SetsStrings.*;

public class Blob {
    Script mineScript;

    public String commandName;
    public String var;
    public String params;

    public int teleport = 0;
    public Command val;
    public void makeBlob(String[] words) {
        switch (words[0]) {
            case S_SCRIPT_KEYWORD_SET,S_SCRIPT_KEYWORD_VAR -> makeSetBlob(words);
            case S_SCRIPT_KEYWORD_IF -> makeIfBlob(words);
            case S_SCRIPT_KEYWORD_DO, S_SCRIPT_KEYWORD_DO_GLOBAL,S_SCRIPT_KEYWORD_SET_DO -> makeDoBlob(words);
        }
    }

    private void makeIfBlob(String[] words) {
        val = new Command(words[1],words,1);
    }

    private void makeSetBlob(String[] words){
        var = words[1];
        String type = ScriptMain.getType(words[2]);
        if(type.equals(S_SCRIPT_TYPE_MATH)){
            val = new Command(type,words,2);
        } else {
            val = new Command(type,words[2]);
        }
    }

    private void makeDoBlob(String[] words){
        var = words[1];
        params = words[2];
    }



    public int execute(int cur, Actor obj) {
        Actor work = obj;
        String workVar = var;

        switch (commandName){
            case S_SCRIPT_TYPE_ACTORVAR:
                if(!obj.floats.containsKey(var)){
                    obj.floats.put(workVar,val.getFloatRes(work,obj.floats));
                }
                break;
            case S_SCRIPT_KEYWORD_SET:
                try {
                    if(var.contains(S_SCRIPT_GET_ACTOR_ARROW)){
                        String[] split = var.split(S_SCRIPT_GET_ACTOR_ARROW);
                        work = Actor.base.get(split[0]);
                        workVar = split[1];
                    }
                    Field field = work.getClass().getField(workVar);
                    field.setFloat(work,val.getFloatRes(work,obj.floats));
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    float value = val.getFloatRes(work,obj.floats);
                    obj.floats.put(workVar,value);
                }
                break;
            case S_SCRIPT_KEYWORD_IF:
                boolean result = val.test(obj,obj.floats);
                if(!result){
                    return teleport;
                }
                break;
            case S_SCRIPT_KEYWORD_ELSE:
                return teleport;
            case S_SCRIPT_KEYWORD_DO:
                ((Actor)obj).doStuff(var,params);
                break;
            case S_SCRIPT_KEYWORD_DO_GLOBAL:
                StarterHelper.game.doStuff(var,params);
                break;
            case S_SCRIPT_KEYWORD_SET_DO:
                float value = StarterHelper.game.getStuff((Actor) work,params);
                try {
                    if(var.contains(S_SCRIPT_GET_ACTOR_ARROW)){
                        String[] split = var.split(S_SCRIPT_GET_ACTOR_ARROW);
                        work = Actor.base.get(split[0]);
                        workVar = split[1];
                    }
                    Field field = work.getClass().getField(workVar);
                    field.setFloat(work,value);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    obj.floats.put(workVar,value);
                }
                break;
            case S_SCRIPT_KEYWORD_RETURN:
                return mineScript.blobs.size();
        }
        return cur + 1;
    }
}
