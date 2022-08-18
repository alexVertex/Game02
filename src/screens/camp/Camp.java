package screens.camp;

import engine.core.Mathp;
import engine.input.MainInput;
import engine.input.interfaces.Button;
import engine.window.Screen;
import engine.helpers.*;
import game.inter.Box;
import game.inter.Simple;
import screens.inGame.InGame;

import static engine._SetsStrings.*;
import static engine._TextString.*;

public class Camp extends Screen {
    int curSecondary = I_CAMP_INVENTORY;
    Button cancel = new Button(P_BUTTON_TEXTURE,246,0+270-115,10001,100,50, FontHelper.getFont(S_FONT_MAIN_NAME),S_CANCEL_SYMBOL,"");
    Box main = new Box(P_BOX_BORDER_TEXTURE,P_BOX_BACK_TEXTURE,0,0,10001,600,370,S_FONT_MAIN_NAME, SM_CAMP_HEADS[curSecondary]);
    InGame inGame;
    Screen secondary;
    Simple buttons = new Simple(S_CAMP_MENUS_BUTTONS,-220,161,10002,152,40);
    Simple choosser = new Simple(S_CAMP_MENUS_BUTTONS_CHOOSER,-220-60,161,10003,32,40);

    Button rest = new Button(P_BUTTON_TEXTURE,0,-270,10001,100,50, FontHelper.getFont(S_FONT_MAIN_NAME),S_FIRE_SYMBOL,P_SOUND_INTERFACE_OK);

    public Camp(InGame inGame) {
        super();
        ActorHelper.addActor(this,main);
        ActorHelper.addActor(this,buttons);
        ActorHelper.addActor(this,choosser);

        InterfaceHelper.addElement(this,cancel);
        if(StarterHelper.game.inResort) {
            InterfaceHelper.addElement(this, rest);
        }
        this.inGame = inGame;
        secondary = new Inventory(this);
    }

    @Override
    public void input(MainInput input) {
        InterfaceHelper.work(this,input);
        if(input.isKeyJustReleased(ControlHelper.CAMP_MENU) || input.isKeyJustReleased(ControlHelper.ESC_MENU) || cancel.worked()){
            AudioHelper.playSoundInterface(P_SOUND_INTERFACE_CANCEL);
            inGame.secondary = null;
            secondary.dispose();
            dispose();
            return;
        }
        if(input.isKeyJustReleased(ControlHelper.CAMP_INVENTORY)){
            if(curSecondary != I_CAMP_INVENTORY) {
                AudioHelper.playSoundInterface(P_SOUND_INTERFACE_CAMP_CHANGE);
                secondary.dispose();
                secondary = new Inventory(this);
                curSecondary = I_CAMP_INVENTORY;
                main.changeHead(SM_CAMP_HEADS[curSecondary]);
            }
        }
        if(input.isKeyJustReleased(ControlHelper.CAMP_SPELS)){
            if(curSecondary != I_CAMP_SPELLBOOK) {
                AudioHelper.playSoundInterface(P_SOUND_INTERFACE_CAMP_CHANGE);
                secondary.dispose();
                secondary = new Spellbook(this);
                curSecondary = I_CAMP_SPELLBOOK;
                main.changeHead(SM_CAMP_HEADS[curSecondary]);
            }
        }
        if(input.isKeyJustReleased(ControlHelper.CAMP_STATS)){
            if(curSecondary != I_CAMP_STATS) {
                AudioHelper.playSoundInterface(P_SOUND_INTERFACE_CAMP_CHANGE);
                secondary.dispose();
                secondary = new Stats(this);
                curSecondary = I_CAMP_STATS;
                main.changeHead(SM_CAMP_HEADS[curSecondary]);

            }
        }
        if(input.isKeyJustReleased(ControlHelper.CAMP_QUESTS)){
            if(curSecondary != I_CAMP_JOURNAL) {
                AudioHelper.playSoundInterface(P_SOUND_INTERFACE_CAMP_CHANGE);
                secondary.dispose();
                secondary = new Journal(this);
                curSecondary = I_CAMP_JOURNAL;
                main.changeHead(SM_CAMP_HEADS[curSecondary]);
            }
        }
        if(input.isKeyJustReleased(ControlHelper.CAMP_TRANSIT)){
            if(curSecondary != I_CAMP_TRANSIT) {
                AudioHelper.playSoundInterface(P_SOUND_INTERFACE_CAMP_CHANGE);
                secondary.dispose();
                secondary = new Transit(this);
                curSecondary = I_CAMP_TRANSIT;
                main.changeHead(SM_CAMP_HEADS[curSecondary]);
            }
        }
        if(StarterHelper.game.inResort) {
            if(rest.worked()){
                StarterHelper.game.restart(false);
                StarterHelper.save();
            }
        }
        for(int i = 0; i <= I_CAMP_TRANSIT;i++){
            if(Mathp.isPointInRect(-220-60+30*i,161,32,40,input.getMouseX(),input.getMouseY())){
                if(input.isKeyJustReleased(I_MOUSE_BUTTON_LEFT)) {
                    curSecondary = i;
                    AudioHelper.playSoundInterface(P_SOUND_INTERFACE_CAMP_CHANGE);
                    secondary.dispose();
                    main.changeHead(SM_CAMP_HEADS[curSecondary]);
                    switch (curSecondary){
                        case I_CAMP_INVENTORY -> secondary = new Inventory(this);
                        case I_CAMP_SPELLBOOK -> secondary = new Spellbook(this);
                        case I_CAMP_STATS -> secondary = new Stats(this);
                        case I_CAMP_JOURNAL -> secondary = new Journal(this);
                        case I_CAMP_TRANSIT -> secondary = new Transit(this);
                    }

                }
            }
        }
        choosser.setX(-220-60+30*curSecondary);
        secondary.input(input);
    }

    @Override
    public void logic() {

    }

    @Override
    public void render() {

    }
}
