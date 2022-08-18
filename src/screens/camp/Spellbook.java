package screens.camp;

import engine.core.Mathp;
import engine.input.MainInput;
import engine.input.interfaces.Scroller;
import engine.render.Label;
import engine.window.Screen;
import engine.helpers.ActorHelper;
import engine.helpers.AudioHelper;
import engine.helpers.InterfaceHelper;
import engine.helpers.StarterHelper;
import game.general.Event;
import game.inter.Describe;
import game.inter.Simple;
import game.inter.Text;
import game.magic.Spell;

import java.util.ArrayList;
import java.util.List;

import static engine._SetsStrings.*;
import static engine._TextString.*;

public class Spellbook  extends Screen {

    Describe describe = new Describe(P_BOX_BORDER_TEXTURE,P_BOX_BACK_BLACK_TEXTURE,0,0, 10015,200,200,S_FONT_SUPPORT_NAME," ");

    Screen inGame;

    List<Text> textsVals3 = new ArrayList<>();
    Simple loadOut = new Simple(S_LOAOOUT,-240,100-115,10002,84,260);
    Simple spellGrid = new Simple(S_SPELLBOOK_SLOTS,-51,100-115,10002,260,260);
    Simple sorts = new Simple(S_SPELLS_SORT,-51,-50-115,10002,260,22);
    Simple sortChooser = new Simple(S_SORT_CHOOSER,-161,-50-115,10003,40,22);
    int[] wheel = {0,0,1024,768};
    Scroller scroll = new Scroller(P_SCROLLER_TEXTURE,270,-15,10005,20,260,0,10,P_SOUND_INTERFACE_CLICK,wheel);

    private int sort;
    Text helpText = new Text(S_FONT_SUPPORT_NAME,S_HELP_FOR_SPELLBOOK_TEXT, -240,-50-115,10004, Label.CENTER);

    public Spellbook(Screen parent){
        ActorHelper.addActor(this,loadOut);
        ActorHelper.addActor(this,spellGrid);
        ActorHelper.addActor(this,sorts);
        ActorHelper.addActor(this,sortChooser);
        ActorHelper.addActor(this,describe);
        ActorHelper.addActor(this,helpText);
        InterfaceHelper.addElement(this,scroll);
        for(int i = 0; i < SM_KNOWLEDGE_TEXT.length;i++){
            Text text = new Text(S_FONT_SUPPORT_NAME,SM_KNOWLEDGE_TEXT[i],-282+400,110-20*i,10002,0);
            ActorHelper.addActor(this,text);
            if(i > 1) {
                text = new Text(S_FONT_SUPPORT_NAME, StarterHelper.game.getPlayer().getKnowledge(i-2)+"", -282 + 570, 110 - 20 * i, 10002, 1);
                ActorHelper.addActor(this, text);
                textsVals3.add(text);
            }
        }
        inGame = parent;
        setUpGreed();
    }

    List<Spell> forshow = new ArrayList<>();

    List<Spell> gathered = new ArrayList<>();
    List<Spell> dressed = new ArrayList<>();

    List<Simple> forShowSimples = new ArrayList<>();
    int rowUnderCursor = 0;
    private void setUpGreed() {
        forshow.clear();
        gathered.clear();
        dressed.clear();
        for(Simple el : forShowSimples){
            ActorHelper.remove(el);
        }
        forShowSimples.clear();
        int i = 0;
        for(Spell el : StarterHelper.game.getSpellbook()){
            if(el.sort != sort) continue;
            gathered.add(el);
            i++;
        }
        i = -1;
        for(Spell el : StarterHelper.game.getPlayer().spell1){
            i++;
            if(el == null) continue;
            int x = -161-101 ;
            int y = 95-44 * (i);
            dressed.add(el);
            forShowSimples.add(new Simple(el.texture,x,y,10010,40,40,el.leftTex,el.topTex,el.rightTex,el.botTex,true));
            ActorHelper.addActor(this,forShowSimples.get(forShowSimples.size()-1));
        }
        i = -1;
        for(Spell el : StarterHelper.game.getPlayer().spell2){
            i++;
            if(el == null) continue;
            int x = -161-101+44 ;
            int y = 95-44 * (i);
            dressed.add(el);
            forShowSimples.add(new Simple(el.texture,x,y,10010,40,40,el.leftTex,el.topTex,el.rightTex,el.botTex,true));
            ActorHelper.addActor(this,forShowSimples.get(forShowSimples.size()-1));
        }
        scroll.setMax((gathered.size()-36)/6);
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
        for(int j = start*6, size = gathered.size(); j < size && j < 36+start*6;j++ ) {
            Spell el = gathered.get(j);
            forshow.add(el);
            int x = -161+44 * (i%6);
            int y = 95-44 * (i/6);
            i++;
            forShowSimples.add(new Simple(el.texture, x, y, 10010, 40, 40, el.leftTex, el.topTex, el.rightTex, el.botTex, true));
            ActorHelper.addActor(this,forShowSimples.get(forShowSimples.size()-1));

        }

    }

    Spell oldUnderCursor = null;
    Spell underCursor = null;
    int undressIndex = 0;
    private void setUnderCursor(double cursorX, double cursorY){
        oldUnderCursor = underCursor;
        underCursor = null;
        int i = 0;
        for(Spell el : forshow){
            if(el.sort != sort) continue;
            int x = -161+44 * (i%6);
            int y = 95-44 * (i/6);
            if(Mathp.isPointInRect(x,y,40,40,cursorX,cursorY)){
                underCursor = el;
            }
            i++;
        }
        i = -1;
        for(Spell el : StarterHelper.game.getPlayer().spell1){
            i++;
            if(el == null) continue;
            int x = -161-101;
            int y = 95-44 * (i);
            if(Mathp.isPointInRect(x,y,40,40,cursorX,cursorY)){
                underCursor = el;
                undressIndex = i;
                rowUnderCursor = 0;

            }
        }
        i = -1;
        for(Spell el : StarterHelper.game.getPlayer().spell2){
            i++;
            if(el == null) continue;
            int x = -161-101+44;
            int y = 95-44 * (i);
            if(Mathp.isPointInRect(x,y,40,40,cursorX,cursorY)){
                underCursor = el;
                undressIndex = i;
                rowUnderCursor = 1;

            }
        }
    }

    @Override
    public void input(MainInput input) {
        double cursorX = input.getMouseX();
        double cursorY = input.getMouseY();
        InterfaceHelper.work(this, input);
        int getScrollVal = scroll.getVal();
        if(getScrollVal != -1){
            chooseSimples(getScrollVal);
        }
        if (Math.abs(-165 - cursorY) < 10) {
            for (int i = 0; i < 6; i++) {
                if (Math.abs((-161 + i * 44) - cursorX) < 20) {
                    if (input.isKeyJustReleased(I_MOUSE_BUTTON_LEFT)) {
                        sort = i;
                        sortChooser.setX(-161 + i * 44);
                        setUpGreed();
                        AudioHelper.playSoundInterface(P_SOUND_INTERFACE_CLICK);
                        break;
                    }
                }
            }
        }
        setUnderCursor(cursorX, cursorY);
        if (underCursor != null) {
            describe.show();
            describe.setPos(cursorX, cursorY, true);
            if (underCursor != oldUnderCursor) {
                describe.setText(underCursor.getDescription());
            }
            if ((input.isKeyJustReleased(I_MOUSE_BUTTON_LEFT) || input.isKeyJustReleased(I_MOUSE_BUTTON_RIGHT))  ) {
                if(StarterHelper.game.inResort) {
                    int row = input.isKeyJustReleased(I_MOUSE_BUTTON_LEFT) ? I_PLAYER_SPELL_LEFT_ROW : I_PLAYER_SPELL_RIGHT_ROW;
                    if (cursorX > -161 - 30) {
                        int index = getIndex(row);
                        if (index > -1) {
                            Spell saved = StarterHelper.game.getPlayer().dressSpell(row, index, underCursor);
                            StarterHelper.game.getSpellbook().remove(underCursor);
                            if (saved != null) {
                                StarterHelper.game.getSpellbook().add(saved);
                            }
                            AudioHelper.playSoundInterface(P_SOUND_INTERFACE_CAMP_DRESS);
                        }
                    } else {
                        StarterHelper.game.getPlayer().dressSpell(rowUnderCursor, undressIndex, null);
                        StarterHelper.game.getSpellbook().add(underCursor);
                        AudioHelper.playSoundInterface(P_SOUND_INTERFACE_CAMP_UNDRESS);
                    }
                } else {
                    Event.makeEvent(S_EVENT_OUTSIDE_RESORT_AREA);
                }
                setUpGreed();

            }
        } else {
            describe.hide();
        }
    }

    private int getIndex(int row) {
        Spell[] spells = row == I_PLAYER_SPELL_LEFT_ROW ? StarterHelper.game.getPlayer().spell1 : StarterHelper.game.getPlayer().spell2;

        for(int i = 0; i < 6; i++){
            if(spells[i] == null){
                return i;
            }
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
