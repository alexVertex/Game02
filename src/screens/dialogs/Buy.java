package screens.dialogs;

import engine.core.Mathp;
import engine.helpers.*;
import engine.input.MainInput;
import engine.input.interfaces.Button;
import engine.input.interfaces.Scroller;
import engine.render.Label;
import engine.window.Screen;
import game.inter.Box;
import game.inter.Describe;
import game.inter.Simple;
import game.inter.Text;
import game.players.Merch;
import game.players.Player;
import game.world.specialObjects.Item;
import screens.inGame.InGame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static engine._SetsStrings.*;
import static engine._TextString.*;

public class Buy extends Screen {
    int sort = 0;

    Simple slots = new Simple(S_INVENTORY_SLOTS,37,100-115,10002,436,260);
    Simple sorts = new Simple(S_ITEMS_SORT,37,-50-115,10002,436,22);
    Simple sortChooser = new Simple(S_SORT_CHOOSER,-161,-50-115,10003,40,22);

    Describe describe = new Describe(P_BOX_BORDER_TEXTURE,P_BOX_BACK_BLACK_TEXTURE,0,0, 10015,200,200,S_FONT_SUPPORT_NAME," ");
    InGame inGame;
    Text gold = new Text(S_FONT_SUPPORT_NAME," ", -282,130,10003, Label.LEFT);
    int[] wheel = {0,0,1024,768};
    Scroller scroll = new Scroller(P_SCROLLER_TEXTURE,270,-15,10005,20,260,0,10,P_SOUND_INTERFACE_CLICK,wheel);
    Player sellTO ;

    Button cancel = new Button(P_BUTTON_TEXTURE,246,0+270-115,10001,100,50, FontHelper.getFont(S_FONT_MAIN_NAME),S_CANCEL_SYMBOL,"");
    Box main = new Box(P_BOX_BORDER_TEXTURE,P_BOX_BACK_TEXTURE,0,0,10001,600,370,S_FONT_MAIN_NAME,S_BUY_HEAD);

    public Buy(InGame ingame, Player player){
        ActorHelper.addActor(this,slots);
        ActorHelper.addActor(this,sorts);
        ActorHelper.addActor(this,sortChooser);
        ActorHelper.addActor(this,describe);
        ActorHelper.addActor(this,gold);
        ActorHelper.addActor(this,main);
        InterfaceHelper.addElement(this,scroll);

        InterfaceHelper.addElement(this,cancel);
        gold.changeText(S_GOLD+StarterHelper.game.gold);
        describe.hide();
        this.inGame = ingame;
        sellTO = player;

        String id = StarterHelper.game.current.getID()+sellTO.localID+S_SAVE_MERCH_ITEMS_POSTFIX;
        String data = StarterHelper.game.current.getChanges().getMerch(id);
        List<String> ids;
        if(data == null){
            ids = Merch.data.get(player.buyMerch).merchIds;
        } else {
            ids = Arrays.asList(data.split(";").clone());
        }

        for(String el : ids){
            String[] sp = el.split(",");
            if(sp.length == 1) continue;
            int count = Integer.parseInt(sp[1]);
            for(int i = 0; i < count;i++) {
                Item copy = new Item(sp[0], "1", 0, 0);
                merch.add(copy);
            }
        }

        setUpGreed();
    }
    List<Item> merch = new ArrayList<>();

    List<Item> gathered = new ArrayList<>();
    List<Item> forshow = new ArrayList<>();


    List<Simple> forShowSimples = new ArrayList<>();

    private void setUpGreed(){
        gathered.clear();
        for(Simple el : forShowSimples){
            ActorHelper.remove(el);
        }
        forShowSimples.clear();
        int i = 0;
        for(Item el : merch){
            if(el.sort != sort) continue;

            gathered.add(el);
            i++;
        }

        scroll.setMax((gathered.size()-60)/10);

        chooseSimples(0);
    }
    private void chooseSimples(int start){
        forshow.clear();
        for (int i = 0; i < forShowSimples.size(); i++) {
            Simple el = forShowSimples.get(i);
            if (el.getX() > -218) {
                ActorHelper.remove(el);
                forShowSimples.remove(el);
                i--;
            }
        }
        int i = 0;
        for(int j = start*10, size = gathered.size(); j < size && j < 60+start*10;j++ ) {
            Item el = gathered.get(j);
            forshow.add(el);
            int x = -161+44 * (i%10);
            int y = 95-44 * (i/10);
            i++;
            forShowSimples.add(new Simple(el.getSprite().texture, x, y, 10010, 40, 40, el.getSprite().leftTex, el.getSprite().topTex, el.getSprite().rightTex, el.getSprite().botTex, true));
            ActorHelper.addActor(this,forShowSimples.get(forShowSimples.size()-1));

        }

    }


    Item oldUnderCursor = null;
    Item underCursor = null;
    int undressIndex = 0;
    private void setUnderCursor(double cursorX, double cursorY){
        oldUnderCursor = underCursor;
        underCursor = null;
        int i = 0;
        for(Item el : forshow){
            if(el.sort != sort) continue;
            int x = -161+44 * (i%10);
            int y = 95-44 * (i/10);
            if(Mathp.isPointInRect(x,y,40,40,cursorX,cursorY)){
                underCursor = el;
            }
            i++;
        }
        i = -1;
        for(Item el : StarterHelper.game.getPlayer().armaments){
            i++;
            if(el == null) continue;
            int x = -161-101+44 * (i%2);
            int y = 95-44 * (i/2);
            if(Mathp.isPointInRect(x,y,40,40,cursorX,cursorY)){
                underCursor = el;
                undressIndex = i;
            }
        }
    }
    @Override
    public void input(MainInput input) {
        InterfaceHelper.work(this,input);
        if(input.isKeyJustReleased(ControlHelper.CAMP_MENU) || input.isKeyJustReleased(ControlHelper.ESC_MENU) || cancel.worked()){
            AudioHelper.playSoundInterface(P_SOUND_INTERFACE_CANCEL);
            dispose();
            inGame.secondary = new Dialog(sellTO,inGame);
            return;
        }
        double cursorX = input.getMouseX();
        double cursorY = input.getMouseY();
        InterfaceHelper.work(this,input);
        int getScrollVal = scroll.getVal();
        if(getScrollVal != -1){
            chooseSimples(getScrollVal);
        }
        if(Math.abs(-165-cursorY) < 10) {
            for(int i = 0; i < 10;i++) {
                if (Math.abs((-161+i*44) - cursorX) < 20) {
                    if (input.isKeyJustReleased(0)) {
                        sort = i;
                        sortChooser.setX(-161+i*44);
                        setUpGreed();
                        AudioHelper.playSoundInterface(P_SOUND_INTERFACE_CLICK);

                        break;
                    }
                }
            }
        }
        setUnderCursor(cursorX,cursorY);
        if(underCursor != null){
            describe.show();
            describe.setPos(cursorX,cursorY,true);
            if(underCursor != oldUnderCursor) {
                describe.setText(underCursor.getDescription());
            }
            if(input.isKeyJustReleased(I_MOUSE_BUTTON_LEFT) ) {

                if (StarterHelper.game.gold >= underCursor.cost) {
                    merch.remove(underCursor);
                    StarterHelper.game.gold -= underCursor.cost;
                    gold.changeText(S_GOLD + StarterHelper.game.gold);
                    setUpGreed();
                    StarterHelper.game.getInventory().add(underCursor);
                    StringBuilder newMerch = new StringBuilder();
                    for(Item el : merch){
                        newMerch.append(el.id).append(",1;");
                    }
                    String id = StarterHelper.game.current.getID()+sellTO.localID+S_SAVE_MERCH_ITEMS_POSTFIX;
                    StarterHelper.game.current.getChanges().changeMerch(id,newMerch.toString());
                }

            }
        } else {
            describe.hide();
        }
    }



    @Override
    public void logic() {

    }

    @Override
    public void render() {

    }
}
