package screens.inGame;

import engine.input.MainInput;
import engine.window.Screen;
import game.general.Game;
import engine.helpers.*;
import game.inter.Simple;
import game.inter.Text;
import game.players.Player;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import static engine._SetsStrings.P_SAVE_PATH;

public class LoadingScreen extends Screen {


    public LoadingScreen(boolean b, String s) {
        loadName = s;
        continueLoad = true;
        ActorHelper.addActor(this,loading);
        ActorHelper.addActor(this,gameOverText);
    }

    class LoadThread extends Thread{
        public boolean loading;
        public void run(){
            try {

                //FileInputStream fileInputStream = new FileInputStream(P_SAVE_PATH+name);
                FileInputStream fileInputStream = new FileInputStream(loadName);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                StarterHelper.game = (Game) objectInputStream.readObject();
                loadStatus = 2;
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            loading = false;
        }
    }

    class LoadThreadFirst extends Thread{
        public boolean loading;
        public void run(){

            StarterHelper.game = new Game();
            StarterHelper.game.loadMap(loadName);
            loadStatus = 2;
        }
    }
    class LoadThreadBonfire extends Thread{
        public boolean loading;
        public void run(){

            StarterHelper.game.loadMap(loadName);
            loadStatus = 2;
        }
    }
    LoadThread loadThread = new LoadThread();
    Simple loading = new Simple("interface/LoadingBack",0,0,11000,1024,768);
    String loadName;
    int loadStatus = 0;
    Text gameOverText = new Text("main","ПОДОЖДИТЕ, ИДЁТ ЗАГРУЗКА.",0,-355,11001,2);

    boolean initLoad = false;
    boolean bonfireLoad = false;
    boolean continueLoad = false;
    boolean newGameLoad = false;

    int placeX, placeY;
    public LoadingScreen(String loadName){
        ActorHelper.addActor(this,loading);
        ActorHelper.addActor(this,gameOverText);
        initLoad = true;
        this.loadName = loadName;
    }

    public LoadingScreen(String mapID, int x, int y) {
        ActorHelper.addActor(this,loading);
        ActorHelper.addActor(this,gameOverText);
        bonfireLoad = true;
        this.loadName = mapID;
        placeX = x;
        placeY = y;
    }
    String saveName;
    double[] params;
    public LoadingScreen(String mapID, int x, int y,String saveName, double[] params) {
        ActorHelper.addActor(this,loading);
        ActorHelper.addActor(this,gameOverText);
        this.loadName = mapID;
        this.saveName = saveName;
        this.params = params;
        placeX = x;
        placeY = y;
        newGameLoad = true;
    }
    public LoadingScreen(String mapID, int x, int y,String saveName) {
        ActorHelper.addActor(this,loading);
        ActorHelper.addActor(this,gameOverText);
        bonfireLoad = true;
        this.loadName = mapID;
        placeX = x;
        placeY = y;
        this.saveName = saveName;
    }
    @Override
    public void input(MainInput input) {
        if(loadStatus == 2) {
            if (input.isKeyJustReleased(ControlHelper.GAME_RESTART)) {
                loadStatus = 3;
            }
        }
    }

    @Override
    public void logic() {
        if(initLoad) {
            if (loadStatus == 0) {
                LoadThreadFirst loadFirst = new LoadThreadFirst();
                loadFirst.start();
                loadStatus = 1;
            } else if (loadStatus == 2) {
                gameOverText.changeText("ЗАГРУЗКА ЗАВЕРШЕНА. ДЛЯ ПРОДОЛЖЕНИЯ НАЖМИТЕ ПРОБЕЛ.");
            } else if (loadStatus == 3) {
                StarterHelper.game.current.load(true);
                StarterHelper.game.getPlayer().init();
                StarterHelper.game.getPlayer().putOnWorld("player");
                loadStatus = 4;
            } else if (loadStatus == 4) {
                dispose();
                ScreenHelper.setScreenActive(new InGame());
            }
        }
        if(bonfireLoad) {
            if (loadStatus == 0) {
                LoadThreadBonfire loadFirst = new LoadThreadBonfire();
                loadFirst.start();
                loadStatus = 1;
            } else if (loadStatus == 2) {
                gameOverText.changeText("ЗАГРУЗКА ЗАВЕРШЕНА. ДЛЯ ПРОДОЛЖЕНИЯ НАЖМИТЕ ПРОБЕЛ.");
            } else if (loadStatus == 3) {
                StarterHelper.game.load();
                StarterHelper.game.getPlayer().init();
                StarterHelper.game.getPlayer().putOnWorld("player");
                loadStatus = 4;
            } else if (loadStatus == 4) {
                dispose();
                ScreenHelper.setScreenActive(new InGame());
            }
        }
        if(continueLoad) {
            if (loadStatus == 0) {
                LoadThread loadFirst = new LoadThread();
                loadFirst.start();
                loadStatus = 1;
            } else if (loadStatus == 2) {
                gameOverText.changeText("ЗАГРУЗКА ЗАВЕРШЕНА. ДЛЯ ПРОДОЛЖЕНИЯ НАЖМИТЕ ПРОБЕЛ.");
            } else if (loadStatus == 3) {
                StarterHelper.game.load(false);

                loadStatus = 4;
            } else if (loadStatus == 4) {
                dispose();
                ScreenHelper.setScreenActive(new InGame());
            }
        }
        if(newGameLoad) {
            if (loadStatus == 0) {
                LoadThreadBonfire loadFirst = new LoadThreadBonfire();
                loadFirst.start();
                loadStatus = 1;
            } else if (loadStatus == 2) {
                gameOverText.changeText("ЗАГРУЗКА ЗАВЕРШЕНА. ДЛЯ ПРОДОЛЖЕНИЯ НАЖМИТЕ ПРОБЕЛ.");
            } else if (loadStatus == 3) {
                StarterHelper.game.load(true);
                StarterHelper.game.getPlayer().init();
                StarterHelper.game.getPlayer().putOnWorld("player");
                StarterHelper.game.saveName = saveName;
                setPlayerParams(StarterHelper.game.getPlayer());
                loadStatus = 4;
            } else if (loadStatus == 4) {
                dispose();
                ScreenHelper.setScreenActive(new InGame());
            }
        }
    }

    private void setPlayerParams(Player player) {
        player.health = (int)params[0];
        player.healthMax = (int)params[0];
        player.stamina = (int)params[1];
        player.staminaMax = (int)params[1];
        player.loadMax = (int)params[2];
        player.speed = (int)params[3];
        player.setKnowledge(0,(int)params[4]);
        player.setKnowledge(1,(int)params[4]);
        player.setKnowledge(2,(int)params[5]);
        player.magicCost = (int)params[6];
        player.arms = ((int)params[7]);
        player.trade = ((int)params[8]);

    }

    @Override
    public void render() {
        SpriteHelper.renderSprites();
    }

    public void start() {
        loading.show();
    }

    public void finish() {
        loading.hide();
    }
}
