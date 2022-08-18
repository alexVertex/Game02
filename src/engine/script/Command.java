package engine.script;

import engine.window.Actor;

import java.lang.reflect.Field;
import java.util.HashMap;

import static engine._SetsStrings.*;

public class Command {

    private final String type;
    private final String val;
    private Command left, right;
    public Command(String type, String val) {
        this.type = type;
        this.val = val;
    }

    public Command(String type, String[] words, int start) {
        this.type = type;
        this.val = words[start];
        String typeLeft = ScriptMain.getType(words[start + 1]);
        if(typeLeft.equals(S_SCRIPT_TYPE_MATH)){
            left = new Command(typeLeft, words,start+1);
        } else {
            left = new Command(ScriptMain.getType(words[start + 1]), words[start + 1]);
        }
        String typeRight = ScriptMain.getType(words[start + 2]);
        if(typeRight.equals(S_SCRIPT_TYPE_MATH)){
            right = new Command(typeRight, words,start+2);
        } else {
            right = new Command(typeRight, words[start + 2]);
        }
    }

    public float getFloatRes(Object object, HashMap<String, Float> vars) {
        Object work = object;
        String workVar = val;
        switch (type) {
            case S_SCRIPT_TYPE_FLOAT:
                return Float.parseFloat(val);
            case S_SCRIPT_TYPE_ACTORVAR:
                try {
                    if(val.contains(S_SCRIPT_GET_ACTOR_ARROW)){
                        String[] split = val.split(S_SCRIPT_GET_ACTOR_ARROW);
                        work = Actor.base.get(split[0]);
                        workVar = split[1];
                    }
                    Field field = work.getClass().getField(workVar);
                    return field.getFloat(work);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    return vars.get(workVar);
                }
            case S_SCRIPT_TYPE_MATH:
                return switch (val) {
                    case S_SCRIPT_MATH_MUL -> left.getFloatRes(object,vars) * right.getFloatRes(object,vars);
                    case S_SCRIPT_MATH_DIV -> left.getFloatRes(object,vars) / right.getFloatRes(object,vars);
                    case S_SCRIPT_MATH_PLUS -> left.getFloatRes(object,vars) + right.getFloatRes(object,vars);
                    case S_SCRIPT_MATH_MINUS -> left.getFloatRes(object,vars) - right.getFloatRes(object,vars);
                    default -> 0;
                };
            default:
                return 0;
        }
    }

    public boolean test(Object object, HashMap<String, Float> vars) {
        return switch (val) {
            case S_SCRIPT_OP_LESSER -> left.getFloatRes(object,vars) < right.getFloatRes(object,vars);
            case S_SCRIPT_OP_BIGGER -> left.getFloatRes(object,vars) > right.getFloatRes(object,vars);
            case S_SCRIPT_OP_EQ_LESSER -> left.getFloatRes(object,vars) <= right.getFloatRes(object,vars);
            case S_SCRIPT_OP_EQ_BIGGER-> left.getFloatRes(object,vars) >= right.getFloatRes(object,vars);
            case S_SCRIPT_OP_EQ -> left.getFloatRes(object,vars) == right.getFloatRes(object,vars);
            case S_SCRIPT_OP_NOT_EQ -> left.getFloatRes(object,vars) != right.getFloatRes(object,vars);
            default -> false;
        };
    }
}
