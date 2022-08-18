package screens.camp;

import engine.core.Mathp;
import engine.input.MainInput;
import engine.input.interfaces.Scroller;
import engine.window.Screen;
import engine.helpers.*;
import game.general.Event;
import game.inter.Describe;
import game.inter.Simple;
import game.inter.Text;
import game.world.specialObjects.Item;

import java.util.ArrayList;
import java.util.List;

import static engine._SetsStrings.*;
import static engine._TextString.*;

public class Inventory extends Screen {
    int sort = 0;

    Simple loadOut = new Simple(S_LOAOOUT,-240,100-115,10002,84,260);
    Simple slots = new Simple(S_INVENTORY_SLOTS,37,100-115,10002,436,260);
    Simple sorts = new Simple(S_ITEMS_SORT,37,-50-115,10002,436,22);
    Simple sortChooser = new Simple(S_SORT_CHOOSER,-161,-50-115,10003,40,22);

    Describe describe = new Describe(P_BOX_BORDER_TEXTURE,P_BOX_BACK_BLACK_TEXTURE,0,0, 10015,200,200,S_FONT_SUPPORT_NAME," ");
    Screen inGame;
    Text gold = new Text(S_FONT_SUPPORT_NAME," ", -282,130,10003,0);
    Simple load = new Simple(S_IN_GAME_PLAYER_INFO_BAR,-240,-50-115,10003,84,20,1,0,0);
    Text loadText = new Text(S_FONT_SUPPORT_NAME," ", -240,-50-115,10004,2);
    int[] wheel = {0,0,1024,768};

    Scroller scroll = new Scroller(P_SCROLLER_TEXTURE,270,-15,10005,20,260,0,10,P_SOUND_INTERFACE_CLICK,wheel);
    public Inventory(Screen parent){
        ActorHelper.addActor(this,loadOut);
        ActorHelper.addActor(this,slots);
        ActorHelper.addActor(this,sorts);
        ActorHelper.addActor(this,sortChooser);
        ActorHelper.addActor(this,describe);
        ActorHelper.addActor(this,gold);
        ActorHelper.addActor(this,load);
        ActorHelper.addActor(this,loadText);

        InterfaceHelper.addElement(this,scroll);
        gold.changeText(S_GOLD+StarterHelper.game.gold);
        describe.hide();

        inGame = parent;
        setUpGreed();
    }

    List<Item> gathered = new ArrayList<>();
    List<Item> forshow = new ArrayList<>();

    List<Item> dressed = new ArrayList<>();

    List<Simple> forShowSimples = new ArrayList<>();

    private void setUpGreed(){
        gathered.clear();
        dressed.clear();
        for(Simple el : forShowSimples){
            ActorHelper.remove(el);
        }
        forShowSimples.clear();
        int i = 0;
        for(Item el : StarterHelper.game.getInventory()){
            if(el.sort != sort) continue;

            gathered.add(el);
            i++;
        }
        i = -1;
        for(Item el : StarterHelper.game.getPlayer().armaments){
            i++;
            if(el == null) continue;
            int x = -161-101+44 * (i%2);
            int y = 95-44 * (i/2);
            dressed.add(el);
            forShowSimples.add(new Simple(el.getSprite().texture,x,y,10010,40,40,el.getSprite().leftTex,el.getSprite().topTex,el.getSprite().rightTex,el.getSprite().botTex,true));
            ActorHelper.addActor(this,forShowSimples.get(forShowSimples.size()-1));
        }
        scroll.setMax((gathered.size()-60)/10);

        setUpEquipLoad();
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
    private void setUpEquipLoad(){
        double otnStam = StarterHelper.game.getPlayer().loadOtn();
        loadText.changeText(StarterHelper.game.getPlayer().getLoadText());
        if(otnStam > F_INVENTORY_LOAD_BAR_NO_RUN) otnStam = F_INVENTORY_LOAD_BAR_NO_RUN;
        if(otnStam < F_INVENTORY_LOAD_LIGHT) {
            load.setColor(0, 1, 0, 1);
        } else if (otnStam < F_INVENTORY_LOAD_MEDIUM){
            load.setColor(0, 0, 1, 1);
        } else {
            load.setColor(1, 0, 0, 1);

        }
        load.setSizeX(84*otnStam);
        load.setX(-282+otnStam*84*0.5);
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
            if(input.isKeyJustReleased(I_MOUSE_BUTTON_LEFT)) {
                if (StarterHelper.game.inResort) {
                    int index = getIndex();
                    if (index > -1) {
                        if (cursorX > -161 - 30) {
                            Item saved = StarterHelper.game.getPlayer().dressItem(index, underCursor);
                            StarterHelper.game.getInventory().remove(underCursor);
                            if (saved != null) {
                                StarterHelper.game.getInventory().add(saved);
                            }
                            AudioHelper.playSoundInterface(P_SOUND_INTERFACE_CAMP_DRESS);

                        } else {
                            StarterHelper.game.getPlayer().dressItem(undressIndex, null);
                            StarterHelper.game.getInventory().add(underCursor);
                            AudioHelper.playSoundInterface(P_SOUND_INTERFACE_CAMP_UNDRESS);

                        }
                        setUpGreed();
                    }
                } else {
                    Event.makeEvent(S_EVENT_OUTSIDE_RESORT_AREA);
                }
            }
        } else {
            describe.hide();
        }
    }

    private int getIndex() {
        if(sort <= I_ITEM_SORT_RANGE){
            boolean noFirstWeapon = StarterHelper.game.getPlayer().armaments[I_PLAYER_ARMAMENTS_WEAPON_ONE] == null;
            return noFirstWeapon ? I_PLAYER_ARMAMENTS_WEAPON_ONE : I_PLAYER_ARMAMENTS_WEAPON_TWO;
        }
        if(sort == I_ITEM_SORT_SHIELDS){
            boolean noFirstShied = StarterHelper.game.getPlayer().armaments[I_PLAYER_ARMAMENTS_SHIELD_ONE] == null;
            return noFirstShied ? I_PLAYER_ARMAMENTS_SHIELD_ONE : I_PLAYER_ARMAMENTS_SHIELD_TWO;
        }
        if(sort == I_ITEM_SORT_ARMOURS){
            return I_PLAYER_ARMAMENTS_ARMOR;
        }
        if(sort == I_ITEM_SORT_TALISMANS){
            if(StarterHelper.game.getPlayer().armaments[I_PLAYER_ARMAMENTS_TALISMAN_1] == null) return I_PLAYER_ARMAMENTS_TALISMAN_1;
            if(StarterHelper.game.getPlayer().armaments[I_PLAYER_ARMAMENTS_TALISMAN_2] == null) return I_PLAYER_ARMAMENTS_TALISMAN_2;
            return I_PLAYER_ARMAMENTS_TALISMAN_3;
        }
        if(sort == I_ITEM_SORT_USES){
            if(StarterHelper.game.getPlayer().armaments[I_PLAYER_ARMAMENTS_ITEM_1] == null) return I_PLAYER_ARMAMENTS_ITEM_1;
            if(StarterHelper.game.getPlayer().armaments[I_PLAYER_ARMAMENTS_ITEM_2] == null) return I_PLAYER_ARMAMENTS_ITEM_2;
            if(StarterHelper.game.getPlayer().armaments[I_PLAYER_ARMAMENTS_ITEM_3] == null) return I_PLAYER_ARMAMENTS_ITEM_3;
            return I_PLAYER_ARMAMENTS_ITEM_4;
        }
        return -1;
    }

    @Override
    public void logic() {

    }

    @Override
    public void render() {

    }
}
