package screens.inGame;

import engine.input.MainInput;
import engine.render.Label;
import engine.window.Actor;
import engine.window.Screen;
import engine.helpers.*;
import game.general.Event;
import game.inter.Box;
import game.inter.Simple;
import game.inter.Text;
import game.magic.Effect;
import game.magic.Spell;
import game.players.Player;
import game.world.map.ResortArea;
import game.world.specialObjects.Activator;
import game.world.specialObjects.Chest;
import game.world.specialObjects.Door;
import game.world.specialObjects.Item;
import screens.camp.Camp;
import screens.menus.ESCmenu;

import java.util.ArrayList;
import java.util.List;

import static engine._SetsStrings.*;
import static engine._TextString.*;

public class InGame extends Screen {
    public Screen secondary = null;
    Simple playerInfo = new Simple(S_IN_GAME_PLAYER_INFO_BACK,-400,340,1000,200,50);
    Simple playerInfoHealth = new Simple(S_IN_GAME_PLAYER_INFO_BAR,-371,352,1001,139,5,1,0,0);
    Simple playerInfoArmor = new Simple(S_IN_GAME_PLAYER_INFO_BAR,-371,340,1001,139,5,1,0.6f,0);
    Simple playerInfoStamina = new Simple(S_IN_GAME_PLAYER_INFO_BAR,-371,328,1001,139,5,0,0.7f,0);

    Simple activate = new Simple(S_IN_GAME_ACTIVATE_BACK,0,-300,1000,250,50);
    Text activateText = new Text(S_FONT_MAIN_NAME," ",0,-300,1001, Label.CENTER);

    Simple vale = new Simple(P_BOX_BACK_TEXTURE,0,0,1050,1024,768,0,0,0,F_IN_GAME_VALE_ALPHA);
    Simple time = new Simple(S_IN_GAME_TIME_CIRCLE,512,-384,1000,100,100);

    Text eventText = new Text(S_FONT_SUPPORT_NAME," ",0,+370,1100,Label.CENTER);
    Simple dayAndNight = new Simple(P_BOX_BACK_TEXTURE,0,0,999,1024,768,0,0.0f,0,0.5f);


    Simple weapon = new Simple(P_BOX_BACK_TEXTURE,-485,350,1001,20,20);
    Simple shield = new Simple(P_BOX_BACK_TEXTURE,-465,330,1001,20,20);
    Text ammoText = new Text(S_FONT_SUPPORT_NAME," ",-485,+330,1001,2);

    Simple item = new Simple(P_BOX_BACK_TEXTURE,-485,-350,1001,40,40);
    Simple spell1 = new Simple(P_BOX_BACK_TEXTURE,-485+44,-350,1001,40,40);
    Simple spell2 = new Simple(P_BOX_BACK_TEXTURE,-485+88,-350,1001,40,40);

    Text itemAmmo = new Text(S_FONT_SUPPORT_NAME," ",-462,-375,1002,1);
    List<Simple> effects = new ArrayList<>();

    Box minimap = new Box(P_BOX_BORDER_TEXTURE,P_BOX_BACK_TEXTURE,+512-75,384-75,1001,150,150);

    List<Simple> minimapPlayers = new ArrayList<>();
    List<Simple> minimapTasks = new ArrayList<>();
    List<Simple> minimapResorts = new ArrayList<>();

    private double deathAlpha = 0;
    Simple gameOver = new Simple(S_IN_GAME_DEATH_SCREEN,-0,0,1201,1024,768);
    Simple gameOverWhite = new Simple(P_BOX_BACK_TEXTURE,-0,0,1200,1024,768);

    Text gameOverText = new Text(S_FONT_MAIN_NAME,S_IN_GAME_DEATH_SCREEN_TEXT,0,-355,1300,Label.CENTER);
    Simple rest = new Simple(S_IN_GAME_REST,512-13,-384+13,999,26,26,0,0,0.5f,1,true);
    Text itemNameChange = new Text(S_FONT_SUPPORT_NAME,"Название предмета",-500,-315,1003,Label.LEFT);
    int timeShowChanging;
    public InGame(){
        ActorHelper.addActor(this,activate);
        ActorHelper.addActor(this,activateText);
        ActorHelper.addActor(this,playerInfo);
        ActorHelper.addActor(this,playerInfoHealth);
        ActorHelper.addActor(this,playerInfoArmor);
        ActorHelper.addActor(this,playerInfoStamina);
        ActorHelper.addActor(this,vale);
        ActorHelper.addActor(this,time);
        ActorHelper.addActor(this,eventText);
        ActorHelper.addActor(this,dayAndNight);
        ActorHelper.addActor(this,weapon);
        ActorHelper.addActor(this,shield);
        ActorHelper.addActor(this,ammoText);
        ActorHelper.addActor(this,item);
        ActorHelper.addActor(this,spell1);
        ActorHelper.addActor(this,spell2);
        ActorHelper.addActor(this,minimap);
        ActorHelper.addActor(this,itemAmmo);
        ActorHelper.addActor(this,gameOver);
        ActorHelper.addActor(this,gameOverWhite);
        ActorHelper.addActor(this,gameOverText);
        ActorHelper.addActor(this,rest);
        ActorHelper.addActor(this,itemNameChange);

        itemNameChange.hide();
        gameOverText.hide();

        gameOverWhite.setColor(1,1,1,0);

        gameOver.setColor(1,1,1,0);
        ///////
        int T = 9;
        for(int i = 0; i < 25;i++) {
            Simple minimapPlayer = new Simple("interface/minimapPlayer",512-50,384-50,1002,8,8,0.5f,0.5f,0.5f);
            ActorHelper.addActor(this, minimapPlayer);
            minimapPlayers.add(minimapPlayer);
        }
        for(int i = 0; i < 15;i++) {
            Simple minimapPlayer = new Simple("interface/minimapPlayer",512-50,384-50,1002,8,8,1,0.0f,0.9f);
            ActorHelper.addActor(this, minimapPlayer);
            minimapTasks.add(minimapPlayer);
        }
        for(int i = 0; i < 10;i++) {
            Simple minimapPlayer = new Simple("interface/minimapPlayer",512-50,384-50,1001,8,8,1,0.0f,0.9f);
            ActorHelper.addActor(this, minimapPlayer);
            minimapResorts.add(minimapPlayer);
        }
        for(int i = 0; i < 15;i++){
            Simple r = new Simple("interface/status",-430+i*24,310,1000,20,20);
            effects.add(r);
            ActorHelper.addActor(this,r);
        }
        int u = 9;
        ////////
        vale.hide();
        time.setAgnle(Math.toRadians(45+180));
    }
    int curActivate = 0;
    int curActivateMax = 0;

    @Override
    public void input(MainInput input) {
        if(StarterHelper.game.mainPlayerKilled){
            if(input.isKeyJustReleased(ControlHelper.GAME_RESTART)){
                deathAlpha = 0;
                StarterHelper.game.restart(true);
                gameOverWhite.setColor(1,1,1, 0);
                gameOver.setColor(1,1,1, 0);
                gameOverText.hide();
                AudioHelper.stopMusic();
            }
            return;
        }
        if(secondary != null) {
            secondary.input(input);
            if(secondary == null){
                vale.hide();
            }
            return;
        }
        StarterHelper.game.input(input);
        List<Actor> activators = StarterHelper.game.scanForActivators();
        if(activators.size() == 0){
            ActorHelper.hide(activate);
            ActorHelper.hide(activateText);
        } else {
            curActivateMax = activators.size();
            if(curActivate >= curActivateMax){
                curActivate = 0;
            }
            if(activators.size() == 1){
                activate.setTexture(S_IN_GAME_ACTIVATE_BACK);
            } else {
                activate.setTexture(S_IN_GAME_ACTIVATE_MULT_BACK);
            }
            ActorHelper.show(activate);
            ActorHelper.show(activateText);
            Actor actor = activators.get(curActivate);
            String text = S_IN_GAME_ACTIVATE_TEXT_USE;
            if(actor instanceof Item) text = S_IN_GAME_ACTIVATE_TEXT_PICK;
            if(actor instanceof Door) {
                text = ((Door)actor).getState() ? S_IN_GAME_ACTIVATE_TEXT_OPEN : S_IN_GAME_ACTIVATE_TEXT_CLOSE;
            }
            if(actor instanceof Activator) text = S_IN_GAME_ACTIVATE_TEXT_USE;
            if(actor instanceof Chest) {
                text = ((Chest)actor).getState() ? S_IN_GAME_ACTIVATE_TEXT_SEARCH : " ";
            }
            if(actor instanceof Player) text = S_IN_GAME_ACTIVATE_TEXT_SPEAK;

            activateText.changeText(text);
            if( input.isKeyJustPressed(ControlHelper.ACTIVATE)){
                StarterHelper.game.activate(actor);
            }
            if( input.isKeyJustPressed(ControlHelper.SWITCH_ACTIVATE)){
                curActivate++;
                if(curActivate == curActivateMax){
                    curActivate = 0;
                }
            }
        }

        CameraHelper.setPos((float) -StarterHelper.game.getPlayer().getX(), (float) -StarterHelper.game.getPlayer().getY());
        if(input.isKeyJustReleased(ControlHelper.CAMP_MENU)){
            secondary = new Camp(this);
            if(StarterHelper.game.inResort){
                StarterHelper.game.addActivatedResort();
            }
            vale.show();
        }
        if(input.isKeyJustReleased(ControlHelper.ESC_MENU)){
            secondary = new ESCmenu(this);
            vale.show();
        }
        ///////
        int i = 0;
        int j = 0;
        for(; i < minimapPlayers.size() && j < StarterHelper.game.getPlayers().size();i++,j++){
            Player el = StarterHelper.game.getPlayers().get(j);
            Simple simple = minimapPlayers.get(i);
            if(el.visible){
                if(i == 0){
                    simple.setColor(0, 1, 0, 1);
                } else {
                    if(el.playerStatus == 0) {
                        simple.setColor(1, 0, 0, 1);
                    } else {
                        simple.setColor(1, 1, 0, 1);
                    }
                }
                simple.setX(512-150+(150+4)*(el.getX()/StarterHelper.game.current.getWidth()));
                simple.setY(384-150+(150+4)*(el.getY()/StarterHelper.game.current.getHeight()));

            } else {
                i--;
            }
        }
        for(; i < minimapPlayers.size();i++) {
            Simple simple = minimapPlayers.get(i);
            simple.setColor(0,0,0,0);
        }
        StarterHelper.game.setQuestToMinimap(minimapTasks);

        i = 0;
        j = 0;
        List<ResortArea> resorts = StarterHelper.game.getResorts();
        for(; i < minimapResorts.size() && j < resorts.size();i++,j++) {
            ResortArea el = resorts.get(j);
            Simple simple = minimapResorts.get(i);
            simple.setColor(1, 0.8f, 0, 1);
            simple.setX(512 - 150 + (150 + 4) * (el.locX / StarterHelper.game.current.getWidth()));
            simple.setY(384 - 150 + (150 + 4) * (el.locY / StarterHelper.game.current.getHeight()));
        }
        for(; i < minimapResorts.size();i++) {
            Simple simple = minimapResorts.get(i);
            simple.setColor(0,0,0,0);
        }
        ///////
    }

    @Override
    public void logic() {

        if(StarterHelper.game.mainPlayerKilled){
            if(deathAlpha == 0){
                AudioHelper.playMusic(P_MUSIC_DEATH_MUSIC);
            }
            deathAlpha += F_IN_GAME_DEATH_ADD_ALPHA;
            if(deathAlpha < F_IN_GAME_DEATH_ALPHA_SECOND_PHASE){
                gameOverWhite.setColor(1,1,1, (float) deathAlpha);
            } else {
                if(deathAlpha > F_IN_GAME_DEATH_ALPHA_MAX){
                    deathAlpha = F_IN_GAME_DEATH_ALPHA_MAX;
                    gameOverText.show();
                }
                gameOver.setColor(1,1,1, (float) deathAlpha-F_IN_GAME_DEATH_ALPHA_SECOND_PHASE);
            }
        }
        if(timeShowChanging>0){
            timeShowChanging--;
            if(timeShowChanging == 0){
                itemNameChange.changeText(" ");
            }
        }
        if(secondary == null) {
            StarterHelper.game.logic();
        }
        if(StarterHelper.game.mapChanged){
            minimap.changeBack(P_MINIMAPS_TEXTURES_PATH+StarterHelper.game.current.getMiniMap());
            StarterHelper.game.mapChanged = false;
        }
        if(Event.eventChanged) {
            eventText.changeText(StarterHelper.game.getEvent());
            Event.eventChanged = false;

        }
        AudioHelper.logic(StarterHelper.game.getSounds());
        double otnStam = StarterHelper.game.getPlayer().staminaOtn();
        playerInfoStamina.setSizeX(139*otnStam);
        playerInfoStamina.setX(-441+otnStam*139*0.5);
        otnStam = StarterHelper.game.getPlayer().healthOtn();
        playerInfoHealth.setSizeX(139*otnStam);
        playerInfoHealth.setX(-441+otnStam*139*0.5);
        otnStam = StarterHelper.game.getPlayer().armorOtn();
        playerInfoArmor.setSizeX(139*otnStam);
        playerInfoArmor.setX(-441+otnStam*139*0.5);
        if(StarterHelper.game.changedWeapon) {setUpSimpleEquip(weapon, StarterHelper.game.getPlayer().getCurWeapon(),S_IN_GAME_FIST); StarterHelper.game.changedWeapon = false;}
        if(StarterHelper.game.changedShield) {setUpSimpleEquip(shield, StarterHelper.game.getPlayer().getCurShield(),S_IN_GAME_FIST);StarterHelper.game.changedShield = false;}
        if(StarterHelper.game.changedItem) {setUpSimpleEquip(item, StarterHelper.game.getPlayer().curItem,S_IN_GAME_EMPTY);StarterHelper.game.changedItem = false;}
        if(StarterHelper.game.changedSpell1) {setUpSimpleEquip(spell1, StarterHelper.game.getPlayer().curSpell1,S_IN_GAME_EMPTY);StarterHelper.game.changedSpell1 = false;}
        if(StarterHelper.game.changedSpell2) {setUpSimpleEquip(spell2, StarterHelper.game.getPlayer().curSpell2,S_IN_GAME_EMPTY);StarterHelper.game.changedSpell2 = false;}
        int charges = StarterHelper.game.getPlayer().getItemCharges();
        itemAmmo.changeText(charges == I_ITEM_NO_USE_AMMO ? " " : charges+"");

        int getAmmo = StarterHelper.game.getPlayer().getAmmo();
        if(getAmmo == I_ITEM_NO_USE_AMMO){
            ammoText.changeText(" ");
        } else {
            ammoText.changeText(getAmmo+"");
        }
        StarterHelper.game.setTime(time, dayAndNight);
        int y = 0;/////////////////////////////
        for(int i = 0; i < effects.size();i++){
            if(i < StarterHelper.game.getPlayer().effectList.size()){
                Effect el = StarterHelper.game.getPlayer().effectList.get(i);
                effects.get(i).setCuts(el.id%16,el.id/16,0.0625f);
                effects.get(i).show();
            } else {
                effects.get(i).hide();
            }
        }
        ////////////////////////////////
        rest.setCutsX(StarterHelper.game.inResort ? I_REST_IN_RESORT : I_REST_NOT_IN_RESORT,F_REST_CUT_PART);
        Event.logic();
    }

    private void setUpSimpleEquip(Simple simple, Item item,String defaultTex){
        if(item != null) {
            simple.setTexture(item.getTexture());
            simple.setCuts(item.getPartX(),item.getPartY(),F_SPELLS_TEXTURE_PART_CUT);
            itemNameChange.changeText(item.getName());
        } else {
            simple.setTexture(defaultTex);
            simple.setCuts(0,1,1);
            itemNameChange.changeText(S_EQUIP_DEFAULT_NAME);
        }
        timeShowChanging = I_CHANGE_ITEM_TIME;

    }

    private void setUpSimpleEquip(Simple simple, Spell spell, String defaultTex){
        if(spell != null) {
            simple.setTexture(spell.getTexture());
            simple.setCuts(spell.getPartX(),spell.getPartY(),F_SPELLS_TEXTURE_PART_CUT);
            itemNameChange.changeText(spell.getName());
            timeShowChanging = I_CHANGE_ITEM_TIME;
        } else {
            simple.setTexture(defaultTex);
            simple.setCuts(0,1,1);
        }
    }

    @Override
    public void render() {
        SpriteHelper.renderSprites();
    }

    public void clearGame() {
        StarterHelper.game.clear();
    }
}
