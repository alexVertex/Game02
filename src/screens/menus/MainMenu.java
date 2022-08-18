package screens.menus;

import engine.input.MainInput;
import engine.input.interfaces.Button;
import engine.window.Screen;
import engine.helpers.*;
import game.inter.Simple;
import game.inter.Text;

import static engine._SetsStrings.*;
import static engine._TextString.*;

public class MainMenu extends Screen {

    Screen secondary = null;

    Button newGame = new Button(P_MAIN_MENU_BUTTON,325,100,100,350,74, FontHelper.getFont(S_FONT_MAIN_NAME),S_ESC_MENU_BUTTONS_TEXT_NEW_GAME,P_SOUND_INTERFACE_CLICK);
    Button load = new Button(P_MAIN_MENU_BUTTON,325,10,100,350,74, FontHelper.getFont(S_FONT_MAIN_NAME),S_ESC_MENU_BUTTONS_TEXT_LOAD_GAME,P_SOUND_INTERFACE_CLICK);
    Button sets = new Button(P_MAIN_MENU_BUTTON,325,-80,100,350,74, FontHelper.getFont(S_FONT_MAIN_NAME),S_ESC_MENU_BUTTONS_TEXT_SETS,P_SOUND_INTERFACE_CLICK);
    Button exit = new Button(P_MAIN_MENU_BUTTON,325,-170,100,350,74, FontHelper.getFont(S_FONT_MAIN_NAME),S_ESC_MENU_BUTTONS_TEXT_QUIT,P_SOUND_INTERFACE_CLICK);
    Text version = new Text(S_FONT_MAIN_NAME,S_GAME_VERSION,-492,-350,2,0);
    Simple sprite = new Simple(P_MAIN_MENU_BACKGROUND,0,0,0,1024,768);
    public MainMenu(){
        ActorHelper.addActor(this,sprite);
        InterfaceHelper.addElement(this, newGame);
        InterfaceHelper.addElement(this, load);
        InterfaceHelper.addElement(this, sets);
        InterfaceHelper.addElement(this, exit);
        ActorHelper.addActor(this,version);
        AudioHelper.playMusic(P_MUSIC_MAIN_MUSIC);
    }

    @Override
    public void input(MainInput input) {
        if(secondary == null) {
            InterfaceHelper.work(this,input);
            if(newGame.worked()){
                secondary = new StartGameScreen(this);
            }
            if (exit.worked()) {
                StarterHelper.endGame();
            }
            if (sets.worked()) {
                secondary = new Sets(this);
            }
            if (load.worked()) {
                secondary = new LoadGameScreen(this);
            }
        } else {
            secondary.input(input);
        }
    }
    @Override
    public void logic() {
        if (!AudioHelper.isMusicPlaying()) {
            AudioHelper.playMusic(P_MUSIC_MAIN_MUSIC);
        }
    }

    @Override
    public void render() {
        SpriteHelper.renderSprites();
    }
}
