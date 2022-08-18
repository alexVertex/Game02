package screens.menus;

import engine.input.MainInput;
import engine.input.interfaces.Button;
import engine.window.Screen;
import engine.helpers.*;
import screens.inGame.InGame;

import static engine._SetsStrings.*;
import static engine._TextString.*;

public class ESCmenu extends Screen {
    InGame parent;
    Screen secondary;
    Button sets = new Button(P_MAIN_MENU_BUTTON,I_ESC_MENU_BUTTONS_X,I_ESC_MENU_BUTTONS_Y_START,I_ESC_MENU_ZORDER,I_ESC_MENU_BUTTONS_SIZEX,I_ESC_MENU_BUTTONS_SIZEY, FontHelper.getFont(S_FONT_MAIN_NAME),S_ESC_MENU_BUTTONS_TEXT_SETS,"");
    Button back = new Button(P_MAIN_MENU_BUTTON,I_ESC_MENU_BUTTONS_X,I_ESC_MENU_BUTTONS_Y_START+I_ESC_MENU_BUTTONS_Y_PER,I_ESC_MENU_ZORDER,I_ESC_MENU_BUTTONS_SIZEX,I_ESC_MENU_BUTTONS_SIZEY, FontHelper.getFont(S_FONT_MAIN_NAME),S_ESC_MENU_BUTTONS_TEXT_CONTINUE,"");
    Button exit = new Button(P_MAIN_MENU_BUTTON,I_ESC_MENU_BUTTONS_X,I_ESC_MENU_BUTTONS_Y_START+I_ESC_MENU_BUTTONS_Y_PER*2,I_ESC_MENU_ZORDER,I_ESC_MENU_BUTTONS_SIZEX,I_ESC_MENU_BUTTONS_SIZEY, FontHelper.getFont(S_FONT_MAIN_NAME),S_ESC_MENU_BUTTONS_TEXT_QUIT,"");

    public ESCmenu(InGame game){
        parent = game;
        InterfaceHelper.addElement(this, sets);
        InterfaceHelper.addElement(this, back);
        InterfaceHelper.addElement(this, exit);
    }
    @Override
    public void input(MainInput input) {
        if(secondary == null) {
            InterfaceHelper.work(this, input);
            if (input.isKeyJustReleased(ControlHelper.ESC_MENU) || back.worked()) {
                AudioHelper.playSoundInterface(P_SOUND_INTERFACE_CLICK);
                parent.secondary = null;
                dispose();
                return;
            }
            if (exit.worked()) {
                StarterHelper.save();
                AudioHelper.playSoundInterface(P_SOUND_INTERFACE_CLICK);
                parent.secondary = null;
                parent.dispose();
                parent.clearGame();
                dispose();
                ScreenHelper.setScreenActive(new MainMenu());
                return;
            }
            if (sets.worked()) {
                AudioHelper.playSoundInterface(P_SOUND_INTERFACE_CLICK);
                secondary = new Sets(this);
            }
        } else {
            secondary.input(input);
        }
    }

    @Override
    public void logic() {

    }

    @Override
    public void render() {

    }
}
