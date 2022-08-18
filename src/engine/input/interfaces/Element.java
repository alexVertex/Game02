package engine.input.interfaces;

import engine.render.Sprite;
import engine.input.MainInput;

import static engine._SetsStrings.*;

public abstract class Element {

    static final float TEXTURE_PART_Y = F_ELEMENT_TEXTURE_CUT;
    public Sprite sprite;
    public int state = I_ELEMENT_STATE_ENABLED;
    public abstract boolean work(MainInput input);

    public  void disable(){
        changeState(I_ELEMENT_STATE_DISABLED);
    }

    public  void enable(){
        changeState(I_ELEMENT_STATE_ENABLED);
    }

    public void changeState(int newState){
        if(newState == state) return;
        state = newState;
        sprite.topTex = TEXTURE_PART_Y*state;
        sprite.botTex = TEXTURE_PART_Y*(state+1);
        sprite.replaceModelTID();
    }

    public void createModel(){
        sprite.topTex = TEXTURE_PART_Y*state;
        sprite.botTex = TEXTURE_PART_Y*(state+1);
        sprite.createModel();
    }

    public abstract boolean worked();

    public abstract void dispose();

    public abstract void add();
}
