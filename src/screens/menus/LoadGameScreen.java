package screens.menus;

import engine.core.Mathp;
import engine.helpers.*;
import engine.input.MainInput;
import engine.input.interfaces.Button;
import engine.render.Label;
import engine.window.Screen;
import game.general.Game;
import game.inter.Box;
import game.inter.Simple;
import game.inter.Text;
import screens.inGame.InGame;
import screens.inGame.LoadingScreen;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;

import static engine._SetsStrings.*;
import static engine._TextString.*;

public class LoadGameScreen extends Screen {

    private final MainMenu parent;
    Box main = new Box(P_BOX_BORDER_TEXTURE,P_BOX_BACK_TEXTURE,0,0,10005,400,600,S_FONT_MAIN_NAME,S_LOAD_HEAD_TEXT);
    Button ok = new Button(P_BUTTON_TEXTURE,0-146,0-270,10006,100,50, FontHelper.getFont(S_FONT_MAIN_NAME),S_OK_SYMBOL,"");
    Button cancel = new Button(P_BUTTON_TEXTURE,0+146,0-270,10006,100,50, FontHelper.getFont(S_FONT_MAIN_NAME),S_CANCEL_SYMBOL,"");

    Simple chooser = new Simple(P_BOX_BACK_TEXTURE,0,220,10009,380,30,0.5f,0.5f,0.5f);
    Simple choce = new Simple(P_BOX_BACK_TEXTURE,0,220,10010,380,30,0.9f,0.5f,0.0f);

    List<Text> games = new ArrayList<>();
    List<String> names = new ArrayList<>();
    String name = null;

    public LoadGameScreen(MainMenu mainMenu){
        ActorHelper.addActor(this,main);
        ActorHelper.addActor(this,chooser);
        ActorHelper.addActor(this,choce);
        choce.hide();
        InterfaceHelper.addElement(this,ok);
        InterfaceHelper.addElement(this,cancel);
        parent = mainMenu;
        int i = 0;
        final File folder = new File(P_SAVE_PATH);
        for (final File fileEntry : Objects.requireNonNull(folder.listFiles())) {
            String name = fileEntry.getName().substring(0,fileEntry.getName().length()-4);
            names.add(fileEntry.getName());
            long timestamp = fileEntry.lastModified();
            Date last=  new Date(timestamp);
            Format timeStampPattern = new SimpleDateFormat(S_SAVE_DATE_PATTERN);
            String date = timeStampPattern.format(last);
            name += "   "+date;
            Text game = new Text(S_FONT_SUPPORT_NAME,name,0,220-30*i++,10080, Label.CENTER);
            ActorHelper.addActor(this,game);
            games.add(game);
        }
    }

    @Override
    public void input(MainInput input) {
        InterfaceHelper.work(this,input);

        if(input.isKeyJustReleased(ControlHelper.ESC_MENU) || cancel.worked()){
            parent.secondary = null;
            dispose();
            AudioHelper.playSoundInterface(P_SOUND_INTERFACE_CANCEL);
        }
        if(ok.pressed()){
            //StarterHelper.load(name);

            AudioHelper.stopMusic();
            dispose();
            parent.dispose();
            ScreenHelper.setScreenActive(new LoadingScreen(true, P_SAVE_PATH+name));
        }
        int underCursor = -1;
        int i = 0;
        for(Text el : games){
            if(Mathp.isPointInRect(0,220-30*i,380,30,input.getMouseX(),input.getMouseY())){
                underCursor = i;
                if(input.isKeyJustReleased(0)){
                    name = names.get(underCursor);
                    choce.show();
                    choce.setY(220-30*underCursor);
                }
            }
            i++;
        }
        chooser.setY(220-30*underCursor);
        if(underCursor == -1){
            chooser.hide();
        } else {
            chooser.show();
        }
    }

    @Override
    public void logic() {

    }

    @Override
    public void render() {

    }
}
