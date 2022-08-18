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
import game.magic.Spell;
import game.players.Player;
import game.world.specialObjects.Item;
import screens.inGame.InGame;

import java.util.ArrayList;
import java.util.List;

import static engine._SetsStrings.*;
import static engine._TextString.*;

public class SpellChangePhaseTwo extends Screen {
    Simple loadOut = new Simple(S_LOAOOUT_SPELL_CHANGE,-240,100-115,10002,84,260);
    Simple slots = new Simple(S_INVENTORY_SLOTS,37,100-115,10002,436,260);
    Describe describe = new Describe(P_BOX_BORDER_TEXTURE,P_BOX_BACK_BLACK_TEXTURE,0,0, 10015,200,200,S_FONT_SUPPORT_NAME," ");
    InGame inGame;
    Text gold = new Text(S_FONT_SUPPORT_NAME," ", -282,130,10003, Label.LEFT);
    int[] wheel = {0,0,1024,768};

    Scroller scroll = new Scroller(P_SCROLLER_TEXTURE,270,-15,10005,20,260,0,10,P_SOUND_INTERFACE_CLICK,wheel);
    Player sellTO ;
    Button cancel = new Button(P_BUTTON_TEXTURE,246,0+270-115,10001,100,50, FontHelper.getFont(S_FONT_MAIN_NAME),S_CANCEL_SYMBOL,"");
    Box main = new Box(P_BOX_BORDER_TEXTURE,P_BOX_BACK_TEXTURE,0,0,10001,600,370,S_FONT_MAIN_NAME,S_SPELL_CHANGE_HEAD);

    Spell workWith, copy,base;

    Simple workWithSimple = null;
    Simple copySimple = null;
    private Spell underCursorSpell;
    private Spell oldUnderCursorSpell;
    List<Item> runes = new ArrayList<>();
    List<Item> initRunes = new ArrayList<>();

    public SpellChangePhaseTwo(InGame ingame, Player player, Spell underCursor){
        ActorHelper.addActor(this,loadOut);
        ActorHelper.addActor(this,slots);
        ActorHelper.addActor(this,describe);
        ActorHelper.addActor(this,gold);
        ActorHelper.addActor(this,main);
        InterfaceHelper.addElement(this,scroll);

        InterfaceHelper.addElement(this,cancel);
        gold.changeText(S_GOLD+StarterHelper.game.gold);
        describe.hide();
        this.inGame = ingame;
        sellTO = player;
        workWith = underCursor;
        copy = new Spell(workWith);
        base = new Spell(workWith.id,0,0);

        workWithSimple = new Simple(workWith.texture,-161-88-13,51,10003,40,40
                ,Spell.TILES_TOTAL*workWith.getPartX(),Spell.TILES_TOTAL*workWith.getPartY()
                ,Spell.TILES_TOTAL*(workWith.getPartX()+1),Spell.TILES_TOTAL*(workWith.getPartY()+1),true);
        copySimple = new Simple(copy.texture,-161-88-13+44,51+44,10003,40,40
                ,Spell.TILES_TOTAL*copy.getPartX(),Spell.TILES_TOTAL*copy.getPartY()
                ,Spell.TILES_TOTAL*(copy.getPartX()+1),Spell.TILES_TOTAL*(copy.getPartY()+1),true);

        ActorHelper.addActor(this,workWithSimple);
        ActorHelper.addActor(this,copySimple);

        String[] runesSplit = workWith.runes.split(";");
        for(String ell : runesSplit){
            if(ell.equals("")) continue;
            runes.add(new Item(ell,"",0,0));
            initRunes.add(runes.get(runes.size()-1));
        }
        setUpGreed();
    }

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
        for(Item el : StarterHelper.game.getInventory()){
            if(el.sort != I_ITEM_SORT_RUNES) continue;

            gathered.add(el);
            i++;
        }
        i = -1;
        for(Item el : runes){
            i++;
            if(el == null) continue;
            int x = -161-101+44 * (i%2);
            int y = 95-44 * (i/2+2);
            forShowSimples.add(new Simple(el.getSprite().texture,x,y,10010,40,40,el.getSprite().leftTex,el.getSprite().topTex,el.getSprite().rightTex,el.getSprite().botTex,true));
            ActorHelper.addActor(this,forShowSimples.get(forShowSimples.size()-1));
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

    List<Item> enteredRunes = new ArrayList<>();
    Item oldUnderCursor = null;
    Item underCursor = null;
    int undressIndex = 0;
    private void setUnderCursor(double cursorX, double cursorY){
        oldUnderCursor = underCursor;
        underCursor = null;
        oldUnderCursorSpell = underCursorSpell;
        underCursorSpell = null;
        int i = 0;
        for(Item el : forshow){
            int x = -161+44 * (i%10);
            int y = 95-44 * (i/10);
            if(Mathp.isPointInRect(x,y,40,40,cursorX,cursorY)){
                underCursor = el;
            }
            i++;
        }
        if(Mathp.isPointInRect(workWithSimple.getX(),workWithSimple.getY(),40,40,cursorX,cursorY)){
            underCursorSpell = workWith;
        }
        if(Mathp.isPointInRect(copySimple.getX(),copySimple.getY(),40,40,cursorX,cursorY)){
            underCursorSpell = copy;
        }
        i = -1;
        for(Item el : runes){
            i++;
            int x = -161-101+44 * (i%2);
            int y = 95-44 * (i/2+2);
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
            inGame.secondary = new SpellChange(inGame,sellTO);
            for(Item el : enteredRunes){
                StarterHelper.game.getInventory().add(el);
            }
            for(Item el : initRunes){
                StarterHelper.game.getInventory().remove(el);
            }
            return;
        }
        double cursorX = input.getMouseX();
        double cursorY = input.getMouseY();
        InterfaceHelper.work(this,input);
        int getScrollVal = scroll.getVal();
        if(getScrollVal != -1){
            chooseSimples(getScrollVal);
        }

        setUnderCursor(cursorX,cursorY);
        if(underCursor != null){
            if(input.isKeyJustReleased(0)){
                if (cursorX > -161 - 30) {
                    if(runes.size() < 8) {
                        runes.add(underCursor);
                        StarterHelper.game.getInventory().remove(underCursor);
                        enteredRunes.add(underCursor);
                        runeAdded();
                        setUpGreed();
                    }
                } else {
                    runes.remove(underCursor);
                    StarterHelper.game.getInventory().add(underCursor);
                    runeRemove();
                    setUpGreed();
                }
            }
        }
        if(underCursorSpell != null){
            if(input.isKeyJustReleased(0)){
                if(underCursorSpell.equals(workWith)){
                    AudioHelper.playSoundInterface(P_SOUND_INTERFACE_CANCEL);
                    dispose();
                    inGame.secondary = new SpellChange(inGame,sellTO);
                    for(Item el : enteredRunes){
                        StarterHelper.game.getInventory().add(el);
                    }
                    for(Item el : initRunes){
                        StarterHelper.game.getInventory().remove(el);
                    }
                    return;
                } else {
                    if(totalCost <= StarterHelper.game.gold) {
                        StarterHelper.game.gold -= totalCost;
                        StarterHelper.game.getSpellbook().remove(workWith);
                        StarterHelper.game.getSpellbook().add(copy);
                        copy.runes = "";
                        for (Item el : runes) {
                            copy.runes += el.id + ";";
                        }
                        AudioHelper.playSoundInterface(P_SOUND_INTERFACE_OK);
                        dispose();
                        inGame.secondary = new SpellChange(inGame, sellTO);
                    }
                    return;
                }
            }
        }
        if(underCursor != null){
            describe.show();
            describe.setPos(cursorX,cursorY,true);
            if(underCursor != oldUnderCursor) {
                describe.setText(underCursor.getDescription());
            }
        } else if(underCursorSpell != null){
            describe.show();
            describe.setPos(cursorX,cursorY,true);
            if(underCursorSpell != oldUnderCursorSpell) {
                describe.setText(underCursorSpell.getDescription());
            }
        }else {
            describe.hide();
        }
    }
    int totalCost = 0;
    private void runeAdded() {
        int what = underCursor.whatToRaise;
        double how = underCursor.raiseMult/10.0;
        if(what == 0){
            double basePower = base.effect.power1*how;
            copy.effect.power1 += basePower;
        }
        copy.difficult += underCursor.difficult;
        countStaminaNeed();
    }
    private void runeRemove() {
        int what = underCursor.whatToRaise;
        double how = underCursor.raiseMult/10.0;
        if(what == 0){
            double basePower = base.effect.power1*how;
            copy.effect.power1 -= basePower;
        }
        copy.difficult += underCursor.difficult;
        countStaminaNeed();
    }

    private void countStaminaNeed(){
        int add = 0;
        int minus = 0;
        for(Item el : runes){
            if(el.whatToRaise == 1){
                minus += el.staminaNeed;
            } else {
                add += el.staminaNeed;
            }
        }
        double power = base.staminaNeed + add + minus;
        if(power < base.staminaNeed){
            power = base.staminaNeed;
        }
        copy.staminaNeed = power;

        totalCost = 0;
        for(Item el : runes){
            if(!initRunes.contains(el)){
                totalCost += el.cost;
            }
        }
        for(Item el : initRunes){
            if(!runes.contains(el)){
                totalCost += el.cost;
            }
        }
        if(totalCost != 0) {
            totalCost += copy.cost;
            totalCost *= 1.2;
            gold.changeText(S_GOLD + StarterHelper.game.gold + " - " + totalCost);
        } else {
            gold.changeText(S_GOLD + StarterHelper.game.gold);
        }
        System.out.println(totalCost);
    }
    @Override
    public void logic() {

    }

    @Override
    public void render() {

    }
}
