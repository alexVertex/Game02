package screens.dialogs;

import engine.input.MainInput;
import engine.input.interfaces.Button;
import engine.render.Label;
import engine.window.Screen;
import engine.helpers.*;
import game.inter.Box;
import game.inter.Simple;
import game.inter.Text;
import game.players.Player;
import game.story.DialogData;
import screens.inGame.InGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static engine._SetsStrings.*;

public class Dialog extends Screen {

    DialogData curDialog = null;
    InGame parent;
    Player player;

    Button sell = new Button(S_DIALOG_DEAL_TEXTURE_SELL,-246-25,0+270-115+12,10001,50,25, FontHelper.getFont(S_FONT_MAIN_NAME)," ","");
    Button buy = new Button(S_DIALOG_DEAL_TEXTURE_BUY,-246-25+50,0+270-115+12,10001,50,25, FontHelper.getFont(S_FONT_MAIN_NAME)," ","");
    Button teach = new Button(S_DIALOG_DEAL_TEXTURE_TEACH,-246-25+100,0+270-115+12,10001,50,25, FontHelper.getFont(S_FONT_MAIN_NAME)," ","");
    Button changeSpell = new Button(S_DIALOG_DEAL_TEXTURE_CHANGE,-246-25+150,0+270-115+12,10001,50,25, FontHelper.getFont(S_FONT_MAIN_NAME)," ","");

    Button cancel = new Button(P_BUTTON_TEXTURE,246,0+270-115,10001,100,50, FontHelper.getFont(S_FONT_MAIN_NAME),S_CANCEL_SYMBOL,"");
    Simple chooser = new Simple(P_BOX_BACK_TEXTURE,0,0,10002,580,20,0,0,0,0);
    List<Text> themes = new ArrayList<>();
    List<DialogData> dialogData = new ArrayList<>();

    Text line = new Text(S_FONT_SUPPORT_NAME," ",-290,90,10003,580, Label.WITH_RETURNS);
    Text talker = new Text(S_FONT_SUPPORT_NAME," ",-290,110,10003,Label.LEFT);

    private int curLine;

    public Dialog(Player player, InGame parent){
        this.parent = parent;
        this.player = player;
        Box main = new Box(P_BOX_BORDER_TEXTURE,P_BOX_BACK_TEXTURE,0,0,10001,600,370,S_FONT_MAIN_NAME,player.name.toUpperCase());
        ActorHelper.addActor(this,chooser);
        ActorHelper.addActor(this,main);
        ActorHelper.addActor(this,line);
        ActorHelper.addActor(this,talker);

        InterfaceHelper.addElement(this,cancel);
        if(player.deals[0])
            InterfaceHelper.addElement(this,sell);
        if(player.deals[1])
            InterfaceHelper.addElement(this,buy);
        if(player.deals[2])
            InterfaceHelper.addElement(this,teach);
        if(player.deals[3])
            InterfaceHelper.addElement(this,changeSpell);

        makeDialogCombo();
    }

    private void makeDialogCombo(){
        HashMap<String,DialogData> dialogs = DialogData.getDialog(player.ID);
        if(dialogs == null) return;
        dialogData.clear();
        int i = 0;
        for(DialogData el : dialogs.values()){
            if(el.canGo()) {
                Text text = new Text(S_FONT_SUPPORT_NAME, el.name, -290, 110 - i * 20, 10003, 0);
                ActorHelper.addActor(this, text);
                dialogData.add(el);
                themes.add(text);
                i++;
            }
        }
    }

    @Override
    public void input(MainInput input) {
        InterfaceHelper.work(this,input);

        if(curDialog == null){
            if(sell.worked()) {
                InGame cur = (InGame) ScreenHelper.getScreenActive();
                cur.secondary = new Sell(cur, player);
                dispose();
                return;
            }
            if(buy.worked()) {
                InGame cur = (InGame) ScreenHelper.getScreenActive();
                cur.secondary = new Buy(cur, player);
                dispose();
                return;
            }
            if(teach.worked()) {
                InGame cur = (InGame) ScreenHelper.getScreenActive();
                cur.secondary = new Teach(cur, player);
                dispose();
                return;
            }
            if(changeSpell.worked()) {
                InGame cur = (InGame) ScreenHelper.getScreenActive();
                cur.secondary = new SpellChange(cur, player);
                dispose();
                return;
            }
            if(input.isKeyJustReleased(ControlHelper.ESC_MENU) || cancel.worked()) {
                AudioHelper.playSoundInterface(P_SOUND_INTERFACE_CANCEL);
                parent.secondary = null;
                dispose();
                return;
            }
            double cursorX = input.getMouseX();
            double cursorY = input.getMouseY();
            for(Text el : themes){
                el.show();
            }
            chooser.show();

            int underCursor = -1;
            if(Math.abs(cursorX) < 290) {
                int i = 0;
                for (Text el : themes) {
                    if(Math.abs(cursorY- (110-i*20)) < 10) {
                        underCursor = i;
                        if(input.isKeyJustReleased(0)){
                            startDialog(dialogData.get(underCursor));
                        }
                    }
                    i++;
                }
            }
            if(underCursor == -1){
                chooser.setColor(0,0,0,0);
            } else {
                chooser.setColor(0.5f,0.5f,0.5f,1);
                chooser.setY(110-underCursor*20);
            }
        } else {
            for (Text el : themes) {
                el.dispose();
            }
            themes.clear();
            chooser.hide();

            if(input.isKeyJustReleased(ControlHelper.ESC_MENU) || cancel.worked()){
                if(curLine == curDialog.lines.size()-1) {
                    endDialog();
                } else {
                    toLast();
                }
                return;
            }
            if(input.isKeyJustReleased(0)){
                nextLine();
            }
            if(input.isKeyJustReleased(1)){
                prevLine();
            }
        }
    }

    private void setLineData(){
        line.changeText(curDialog.lines.get(curLine).text);
        talker.changeText(curDialog.lines.get(curLine).talker == 0 ? StarterHelper.game.getPlayer().name : player.name);
    }

    private void toLast() {
        curLine=curDialog.lines.size()-1;
        setLineData();
    }

    private void prevLine() {
        curLine--;
        if(curLine < 0) curLine = 0;
        setLineData();

    }

    private void nextLine() {
        curLine++;
        if(curLine < curDialog.lines.size()) {
            setLineData();
        } else {
            endDialog();
        }
    }

    private void endDialog() {
        curDialog.resultsUse();
        curDialog = null;
        line.changeText(" ");
        talker.changeText(" ");
        makeDialogCombo();
    }

    public void startDialog(DialogData el){
        curDialog = el;
        curLine = 0;
        setLineData();
    }

    @Override
    public void logic() {

    }

    @Override
    public void render() {

    }
}
