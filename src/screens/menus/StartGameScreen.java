package screens.menus;

import engine.helpers.*;
import engine.input.MainInput;
import engine.input.interfaces.Button;
import engine.input.interfaces.TextBox;
import engine.render.Label;
import engine.window.Screen;
import game.general.Game;
import game.inter.Box;
import game.inter.Text;
import game.players.Player;
import screens.inGame.InGame;
import screens.inGame.LoadingScreen;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static engine._SetsStrings.*;
import static engine._TextString.*;

public class StartGameScreen extends Screen {

    Box main = new Box(P_BOX_BORDER_TEXTURE,P_BOX_BACK_TEXTURE,0,0,10005,400,600,S_FONT_MAIN_NAME,S_ESC_MENU_BUTTONS_TEXT_NEW_GAME);
    Button ok = new Button(P_BUTTON_TEXTURE,0-146,0-270,10006,100,50, FontHelper.getFont(S_FONT_MAIN_NAME),S_OK_SYMBOL,"");
    Button cancel = new Button(P_BUTTON_TEXTURE,0+146,0-270,10006,100,50, FontHelper.getFont(S_FONT_MAIN_NAME),S_CANCEL_SYMBOL,"");
    MainMenu parent;

    Text name = new Text(S_FONT_MAIN_NAME,S_NEW_GAME_NAME,0-190,0+220+25,10006,0);
    TextBox nameVal = new TextBox(P_TEXTBOX_TEXTURE,20,0+220+25,10006,200,32,FontHelper.getFont(S_FONT_SUPPORT_NAME)," ","");

    Text stats = new Text(S_FONT_MAIN_NAME,S_NEW_GAME_ATTRIBUTES,0-190,-40+220+25,10006,0);
    Text strength = new Text(S_FONT_SUPPORT_NAME,S_NEW_GAME_ATTRIBUTE_STRENGTH,0-190,-70+220+25,10006,0);
    Text endurance = new Text(S_FONT_SUPPORT_NAME,S_NEW_GAME_ATTRIBUTE_ENDURANCE,0-190,-90+220+25,10006,0);
    Text agility = new Text(S_FONT_SUPPORT_NAME,S_NEW_GAME_ATTRIBUTE_AGILITY,0-190,-110+220+25,10006,0);
    Text intelligence = new Text(S_FONT_SUPPORT_NAME,S_NEW_GAME_ATTRIBUTE_INTELLIGENCE,0-190,-130+220+25,10006,0);
    Text charm = new Text(S_FONT_SUPPORT_NAME,S_NEW_GAME_ATTRIBUTE_CHARM,0-190,-150+220+25,10006,0);
    Text points = new Text(S_FONT_SUPPORT_NAME,S_NEW_GAME_ATTRIBUTE_FREE,0-190,-200+220+25,10006,0);
    Text freePoints = new Text(S_FONT_SUPPORT_NAME,"10",0,-200+220+25,10006,2);


    Text params = new Text(S_FONT_MAIN_NAME,S_NEW_GAME_PARAMS,0-190,-230+220+25,10006,0);
    Text health = new Text(S_FONT_SUPPORT_NAME,S_NEW_GAME_PARAM_HEALTH,0-190,-250+220+15,10006,0);
    Text stamina = new Text(S_FONT_SUPPORT_NAME,S_NEW_GAME_PARAM_STAMINA,0-190,-270+220+15,10006,0);
    Text load = new Text(S_FONT_SUPPORT_NAME,S_NEW_GAME_PARAM_LOAD,0-190,-290+220+15,10006,0);
    Text speed = new Text(S_FONT_SUPPORT_NAME,S_NEW_GAME_PARAM_SPEED,0-190,-310+220+15,10006,0);
    Text knowledge = new Text(S_FONT_SUPPORT_NAME,S_NEW_GAME_PARAM_KNOWLEDGE,0-190,-330+220+15,10006,0);
    Text speachCraft = new Text(S_FONT_SUPPORT_NAME,S_NEW_GAME_PARAM_SPEECH,0-190,-350+220+15,10006,0);
    Text magicCost = new Text(S_FONT_SUPPORT_NAME,S_NEW_GAME_PARAM_SPELLCOST,0-190,-370+220+15,10006,0);
    Text arms = new Text(S_FONT_SUPPORT_NAME,S_NEW_GAME_PARAM_ARMS,0-190,-390+220+15,10006,0);
    Text merchant = new Text(S_FONT_SUPPORT_NAME,S_NEW_GAME_PARAM_TRADE,0-190,-410+220+15,10006,0);


    private int healthVal;
    private int staminaVal;
    private int loadVal;
    private int speedVal;
    private int knowledgeVal;
    private int speechCraftVal;
    private int magicCostVal;
    private int armsVal;
    private int merchantVal;

    Text val_health = new Text(S_FONT_SUPPORT_NAME,healthVal+"",0,-250+220+15,10006,0);
    Text val_stamina = new Text(S_FONT_SUPPORT_NAME,staminaVal+"",0,-270+220+15,10006,0);
    Text val_load = new Text(S_FONT_SUPPORT_NAME,loadVal+"",0,-290+220+15,10006,0);
    Text val_speed = new Text(S_FONT_SUPPORT_NAME,speedVal+"",0,-310+220+15,10006,0);
    Text val_knowledge = new Text(S_FONT_SUPPORT_NAME,knowledgeVal+"",0,-330+220+15,10006,0);
    Text val_speachCraft = new Text(S_FONT_SUPPORT_NAME,speechCraftVal+"",0,-350+220+15,10006,0);
    Text val_magicCost = new Text(S_FONT_SUPPORT_NAME,magicCostVal+"",0,-370+220+15,10006,0);
    Text val_arms = new Text(S_FONT_SUPPORT_NAME,armsVal+"",0,-390+220+15,10006,0);
    Text val_merchant = new Text(S_FONT_SUPPORT_NAME,merchantVal+"",0,-410+220+15,10006,0);

    List<Button> minus = new ArrayList<>();
    List<Text> values = new ArrayList<>();
    List<Button> plus = new ArrayList<>();

    Text error = new Text(S_FONT_MAIN_NAME,"",0,-410+180+15,10006,2);


    public StartGameScreen(MainMenu mainMenu){
        parent = mainMenu;
        ActorHelper.addActor(this,main);
        ActorHelper.addActor(this,name);
        ActorHelper.addActor(this,stats);
        ActorHelper.addActor(this,strength);
        ActorHelper.addActor(this,endurance);
        ActorHelper.addActor(this,agility);
        ActorHelper.addActor(this,intelligence);
        ActorHelper.addActor(this,charm);
        ActorHelper.addActor(this,points);
        ActorHelper.addActor(this,freePoints);
        ActorHelper.addActor(this,error);

        for(int i = 0; i < 5;i++){
            Button button = new Button(P_LEFT_TEXTURE,-50,-70+220+25-i*20,10006,18,18,FontHelper.getFont(S_FONT_MAIN_NAME)," ",P_SOUND_INTERFACE_CLICK);
            minus.add(button);
            InterfaceHelper.addElement(this,button);
            button = new Button(P_RIGHT_TEXTURE,50,-70+220+25-i*20,10006,18,18,FontHelper.getFont(S_FONT_MAIN_NAME)," ",P_SOUND_INTERFACE_CLICK);
            plus.add(button);
            InterfaceHelper.addElement(this,button);
            Text text = new Text(S_FONT_SUPPORT_NAME,"1",0,-70+220+25-i*20,10006, Label.CENTER);
            values.add(text);
            ActorHelper.addActor(this,text);
        }

        ActorHelper.addActor(this,params);
        ActorHelper.addActor(this,health);
        ActorHelper.addActor(this,stamina);
        ActorHelper.addActor(this,load);
        ActorHelper.addActor(this,speed);
        ActorHelper.addActor(this,knowledge);
        ActorHelper.addActor(this,speachCraft);
        ActorHelper.addActor(this,magicCost);
        ActorHelper.addActor(this,arms);
        ActorHelper.addActor(this,merchant);

        ActorHelper.addActor(this,val_health);
        ActorHelper.addActor(this,val_stamina);
        ActorHelper.addActor(this,val_load);
        ActorHelper.addActor(this,val_speed);
        ActorHelper.addActor(this,val_knowledge);
        ActorHelper.addActor(this,val_speachCraft);
        ActorHelper.addActor(this,val_magicCost);
        ActorHelper.addActor(this,val_arms);
        ActorHelper.addActor(this,val_merchant);

        InterfaceHelper.addElement(this,nameVal);
        InterfaceHelper.addElement(this,ok);
        InterfaceHelper.addElement(this,cancel);
        countParams();

    }

    private void countParams() {
        int strengthVal = Integer.parseInt(values.get(0).getText());
        int enduranceVal = Integer.parseInt(values.get(1).getText());
        int agilityVal = Integer.parseInt(values.get(2).getText());
        int intelligenceVal = Integer.parseInt(values.get(3).getText());
        int charmVal = Integer.parseInt(values.get(4).getText());

        healthVal = I_HEALTH_BASE + (enduranceVal-I_HEALTH_ENDURANCE_MINUS) * I_HEALTH_ENDURANCE_MULT;
        staminaVal = I_STAMINA_BASE + (enduranceVal-I_STAMINA_ENDURANCE_MINUS) * I_STAMINA_ENDURANCE_MULT + (strengthVal - I_STAMINA_STRENGTH_MINUS) * I_STAMINA_STRENGTH_MULT;
        loadVal = I_LOAD_BASE + (strengthVal-I_LOAD_STRENGTH_MINUS) * I_LOAD_STRENGTH_MULT;;
        speedVal = I_SPEED_BASE + (agilityVal-I_SPEED_AGILITY_MINUS)*I_SPEED_AGILITY_MULT;
        knowledgeVal = I_KNOWLEDGE_BASE+intelligenceVal*I_INTELLIGENCE_MULT;
        speechCraftVal = I_SPEECH_BASE + charmVal*I_SPEECH_CHARM_MULT;
        magicCostVal = I_MAGICCOST_BASE - (intelligenceVal-I_MAGICCOST_INTELLEGENCE_MINUS) * I_MAGICCOST_INTELLEGENCE_MULT;
        armsVal = I_ARMS_BASE + agilityVal * I_ARMS_AGILITY_MULT;
        merchantVal = I_TRADE_BASE - (charmVal-I_TRADE_CHARM_MINUS) * I_TRADE_CHARM_MULT;

        val_health.changeText(healthVal + "");
        val_stamina.changeText(staminaVal + "");
        val_load.changeText(loadVal + "");
        val_speed.changeText(speedVal + "");
        val_knowledge.changeText(knowledgeVal + "");
        val_speachCraft.changeText(speechCraftVal + "");
        val_magicCost.changeText(magicCostVal + "");
        val_arms.changeText(armsVal + "");
        val_merchant.changeText(merchantVal + "");
    }

    int free = I_INIT_FREE_POINTS;
    int[] paramsVal = {I_INIT_ATTRIBUTE_VALUE,I_INIT_ATTRIBUTE_VALUE,I_INIT_ATTRIBUTE_VALUE,I_INIT_ATTRIBUTE_VALUE,I_INIT_ATTRIBUTE_VALUE};
    @Override
    public void input(MainInput input) {
        boolean wasLocked = InterfaceHelper.hasFocused();
        InterfaceHelper.work(this,input);
        if(wasLocked){
            return;
        }
        for (int i = 0, minusSize = minus.size(); i < minusSize; i++) {
            Button el = minus.get(i);
            if (el.worked()) {
                if (paramsVal[i] > I_INIT_ATTRIBUTE_VALUE){
                    paramsVal[i] --;
                    free++;
                    freePoints.changeText(free+"");
                    values.get(i).changeText(paramsVal[i]+"");
                    countParams();
                }
            }
            el = plus.get(i);
            if (el.worked()) {
                if (paramsVal[i] < I_MAX_ATTRIBUTE_VALUE && free > 0){
                    paramsVal[i] ++;
                    free--;
                    freePoints.changeText(free+"");
                    values.get(i).changeText(paramsVal[i]+"");
                    countParams();
                }
            }
        }
        if(input.isKeyJustReleased(ControlHelper.ESC_MENU) || cancel.worked()){
            parent.secondary = null;
            dispose();
            AudioHelper.playSoundInterface(P_SOUND_INTERFACE_CANCEL);
        }
        if(ok.worked()){
            if(free == 0 && nameVal.getText().length() > 0) {
                parent.secondary = null;
                parent.dispose();
                dispose();
                final File folder = new File(P_SAVE_PATH);
                int count = Objects.requireNonNull(folder.listFiles()).length + 1;

                ScreenHelper.setScreenActive(new LoadingScreen(S_NEW_GAME_START_MAP,I_START_GAME_COORDS_X,I_START_GAME_COORDS_Y,count+" "+nameVal.getText(),params()));
                //ScreenHelper.setScreenActive(new InGame());
                //StarterHelper.game = new Game();
                //setPlayerParams(StarterHelper.game.getPlayer());
                AudioHelper.stopMusic();
            } else {
                if(free > 0){
                    error.changeText(S_NEW_GAME_ERROR_FREE_POINTS);
                } else {
                    error.changeText(S_NEW_GAME_ERROR_NO_NAME);
                }
            }
        }
    }

    private double[] params(){
        return new double[]{healthVal,staminaVal,loadVal,speedVal,knowledgeVal,speechCraftVal,magicCostVal,armsVal,merchantVal};
    }

    private void setPlayerParams(Player player) {
        player.health = healthVal;
        player.healthMax = healthVal;
        player.stamina = staminaVal;
        player.staminaMax = staminaVal;
        player.loadMax = loadVal;
        player.speed = speedVal;
        player.setKnowledge(0,knowledgeVal);
        player.setKnowledge(1,knowledgeVal);
        player.setKnowledge(2,speechCraftVal);
        player.magicCost = magicCostVal;
        player.arms = (armsVal);
        player.trade = (merchantVal);

    }

    @Override
    public void logic() {

    }

    @Override
    public void render() {

    }
}
