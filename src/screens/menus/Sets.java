package screens.menus;

import engine.input.interfaces.Keyholder;
import engine.render.Label;
import engine.render.Sprite;
import engine.audio.Audio;
import engine.input.MainInput;
import engine.input.interfaces.Button;
import engine.input.interfaces.Slider;
import engine.window.Screen;
import engine.window.Window;
import engine.window.WindowSettings;
import engine.helpers.*;
import game.inter.Box;
import game.inter.Text;

import java.util.ArrayList;
import java.util.List;

import static engine._SetsStrings.*;
import static engine._TextString.*;

public class Sets extends Screen {
    WindowSettings sets = Window.getSets();

    int curType = sets.vidMode;

    Box main = new Box(P_BOX_BORDER_TEXTURE,P_BOX_BACK_TEXTURE,0,0,10005,400,600,S_FONT_MAIN_NAME,S_ESC_MENU_BUTTONS_TEXT_SETS);
    Text video = new Text(S_FONT_MAIN_NAME,S_VIDEO_TEXT,0-190,0+220+25,10006, Label.LEFT);

    Text videoType = new Text(S_FONT_SUPPORT_NAME,S_VIDEOMODE_TEXT,0-190,0+190+25,10006,Label.LEFT);
    Text videoTypeVal = new Text(S_FONT_SUPPORT_NAME,SM_SETS_VIDMODE_TYPES[curType], 0-45,0+190+25,10006,Label.CENTER);

    Text videoBright = new Text(S_FONT_SUPPORT_NAME,S_BRIGHT_TEXT,0-190,0+170+25,10006,Label.LEFT);

    Button ok = new Button(P_BUTTON_TEXTURE,0-146,0-270,10006,100,50, FontHelper.getFont(S_FONT_MAIN_NAME),S_OK_SYMBOL,"");
    Button cancel = new Button(P_BUTTON_TEXTURE,0+146,0-270,10006,100,50, FontHelper.getFont(S_FONT_MAIN_NAME),S_CANCEL_SYMBOL,"");
    Button left1 = new Button(P_LEFT_TEXTURE,0-95,0+190+25,10006,18,18, FontHelper.getFont(S_FONT_MAIN_NAME)," ",P_SOUND_INTERFACE_CLICK);
    Button right1 = new Button(P_RIGHT_TEXTURE,5,0+190+25,10006,18,18, FontHelper.getFont(S_FONT_MAIN_NAME)," ",P_SOUND_INTERFACE_CLICK);
    Slider brightVal = new Slider(P_SLIDER_TEXTURE,-45,170+25,10006,118,18,sets.brightness,P_SOUND_INTERFACE_CLICK);

    Text audio = new Text(S_FONT_MAIN_NAME,S_AUDIO_TEXT,20,0+220+25,10006,Label.LEFT);
    Text music = new Text(S_FONT_SUPPORT_NAME,S_MUSIC_TEXT,20,0+190+25,10006,Label.LEFT);
    Text sound = new Text(S_FONT_SUPPORT_NAME,S_SOUND_TEXT,20,0+170+25,10006,Label.LEFT);

    Slider musicVal = new Slider(P_SLIDER_TEXTURE,-45+180,190+25,10006,118,18,sets.musicVolume,P_SOUND_INTERFACE_CLICK);
    Slider soundVal = new Slider(P_SLIDER_TEXTURE,-45+180,170+25,10006,118,18,sets.soundVolume,P_SOUND_INTERFACE_CLICK);

    Text game = new Text(S_FONT_MAIN_NAME,S_CONTROLS_TEXT,0-190,0+140+25,10006,Label.LEFT);



    Screen mainMenu;
    List<Keyholder> keyholderList = new ArrayList<>();
    public Sets(Screen mainMenu) {
        super();
        this.mainMenu = mainMenu;
        ActorHelper.addActor(this,main);
        ActorHelper.addActor(this,video);
        ActorHelper.addActor(this,videoType);
        ActorHelper.addActor(this,videoBright);
        ActorHelper.addActor(this,videoTypeVal);
        ActorHelper.addActor(this,audio);
        ActorHelper.addActor(this,music);
        ActorHelper.addActor(this,sound);
        ActorHelper.addActor(this,game);

        for(int i = 0; i < SM_SETS_CONTROLS.length;i++){
            ActorHelper.addActor(this,new Text(S_FONT_SUPPORT_NAME,SM_SETS_CONTROLS[i],-190,0+135-20*i,10006,0));
            Keyholder keyholder = new Keyholder(P_KEYHOLDER_TEXTURE,98,0+135-20*i,10006,196,18,FontHelper.getFont(S_FONT_SUPPORT_NAME),ControlHelper.codes[i],"");
            keyholderList.add(keyholder);
            InterfaceHelper.addElement(this,keyholder);
        }

        InterfaceHelper.addElement(this,ok);
        InterfaceHelper.addElement(this,cancel);
        InterfaceHelper.addElement(this,left1);
        InterfaceHelper.addElement(this,right1);
        InterfaceHelper.addElement(this,musicVal);
        InterfaceHelper.addElement(this,soundVal);

        InterfaceHelper.addElement(this,brightVal);

    }

    public void save(){
        sets.brightness = brightVal.getVal();
        sets.musicVolume = musicVal.getVal();
        sets.soundVolume = soundVal.getVal();
        sets.vidMode = curType;
        for(int i = 0; i < SM_SETS_CONTROLS.length;i++){
            int keyCode = keyholderList.get(i).getKey();
            ControlHelper.changeCode(i,keyCode);
        }
        sets.makeFile();
    }

    void close(){
        if(mainMenu instanceof MainMenu) {
            ((MainMenu)(mainMenu)).secondary = null;
        }
        if(mainMenu instanceof ESCmenu) {
            ((ESCmenu)(mainMenu)).secondary = null;
        }
        dispose();
    }

    @Override
    public void input(MainInput input) {
        boolean breaking = InterfaceHelper.hasFocused();
        InterfaceHelper.work(this,input);
        Sprite.setGlobalBrightness(brightVal.getVal());
        Audio.musicSetBaseGain(musicVal.getVal());
        Audio.soundSetBaseGain(soundVal.getVal());
        if(breaking) {
            return;
        }
        if(input.isKeyJustReleased(ControlHelper.ESC_MENU) || cancel.worked()){
            close();
            AudioHelper.playSoundInterface(P_SOUND_INTERFACE_CANCEL);
            cancel();

        }
        if(input.isKeyJustReleased(ControlHelper.OK_MENU) || ok.worked()){
            close();
            AudioHelper.playSoundInterface(P_SOUND_INTERFACE_OK);
            save();
        }
        if(left1.worked()){
            curType--;
            if(curType == -1){
                curType = SM_SETS_VIDMODE_TYPES.length-1;
            }
            videoTypeVal.setText(SM_SETS_VIDMODE_TYPES[curType]);
        }
        if(right1.worked()){
            curType++;
            if(curType == SM_SETS_VIDMODE_TYPES.length){
                curType = 0;
            }
            videoTypeVal.setText(SM_SETS_VIDMODE_TYPES[curType]);
        }
    }

    private void cancel() {
        sets.makeFile();
        Sprite.setGlobalBrightness(sets.brightness);
        Audio.musicSetBaseGain(sets.musicVolume);
        Audio.soundSetBaseGain(sets.soundVolume);

    }

    @Override
    public void logic() {

    }

    @Override
    public void render() {

    }
}
