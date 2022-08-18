package screens.camp;

import engine.input.MainInput;
import engine.window.Screen;
import engine.helpers.*;
import game.inter.Text;
import game.players.Player;

import java.util.ArrayList;
import java.util.List;

import static engine._SetsStrings.*;
import static engine._TextString.*;

public class Stats extends Screen {

    private final Screen inGame;

    List<Text> textsVals1 = new ArrayList<>();
    List<Text> textsVals2 = new ArrayList<>();
    List<Text> textsVals3 = new ArrayList<>();

    public Stats(Screen parent){
        inGame = parent;
        for(int i = 0; i < SM_CAMP_STATS_MAIN.length;i++){
            Text text = new Text(S_FONT_SUPPORT_NAME,SM_CAMP_STATS_MAIN[i],-282,110-20*i,10002,0);
            ActorHelper.addActor(this,text);
            text = new Text(S_FONT_SUPPORT_NAME," ",-282+190,110-20*i,10002,1);
            ActorHelper.addActor(this,text);
            textsVals1.add(text);
        }
        for(int i = 0; i < SM_CAMP_STATS_DEFENSES.length;i++){
            Text text = new Text(S_FONT_SUPPORT_NAME,SM_CAMP_STATS_DEFENSES[i],-282+200,110-20*i,10002,0);
            ActorHelper.addActor(this,text);
            text = new Text(S_FONT_SUPPORT_NAME," ",-282+390,110-20*i,10002,1);
            ActorHelper.addActor(this,text);
            textsVals2.add(text);
        }
        for(int i = 0; i < SM_CAMP_STATS_OFFENSES.length;i++){
            Text text = new Text(S_FONT_SUPPORT_NAME,SM_CAMP_STATS_OFFENSES[i],-282+400,110-20*i,10002,0);
            ActorHelper.addActor(this,text);
            text = new Text(S_FONT_SUPPORT_NAME," ",-282+570,110-20*i,10002,1);
            ActorHelper.addActor(this,text);
            textsVals3.add(text);
        }
        setUpGreed();
    }

    public void setUpGreed(){

        Player player = StarterHelper.game.getPlayer();
        textsVals1.get(2).changeText(player.getHealthText());
        textsVals1.get(3).changeText(player.getArmorText());
        textsVals1.get(4).changeText(player.getStaminaText());
        textsVals1.get(5).changeText(player.getLoadText());
        textsVals1.get(7).changeText(player.getPoiseText());
        textsVals1.get(8).changeText(player.getSpeedText());
        textsVals1.get(9).changeText(player.getMagicCost());
        textsVals1.get(10).changeText(player.getArmsText());
        textsVals1.get(11).changeText(player.getTrade());


        for(int i = 0; i < 4;i++) {
            textsVals2.get(2+i).changeText(player.getDefense(i)+"");
        }
        for(int i = 0; i < 7;i++) {
            textsVals2.get(7+i).changeText(player.getDefense(4+i)+"");
        }

        textsVals3.get(2).changeText(player.getWeaponDamage1());
        textsVals3.get(3).changeText(player.getWeaponTypeDamage1());
        textsVals3.get(4).changeText(player.getWeaponAmmo1());
        textsVals3.get(5).changeText(player.getWeaponDamage2());
        textsVals3.get(6).changeText(player.getWeaponTypeDamage2());
        textsVals3.get(7).changeText(player.getWeaponAmmo2());

        for(int i = 0; i < 3;i++) {
            textsVals3.get(11+i).changeText(player.getKnowledge(i)+"");
        }
    }

    @Override
    public void input(MainInput input) {
        InterfaceHelper.work(this,input);


    }

    @Override
    public void logic() {

    }

    @Override
    public void render() {

    }
}
