package engine.helpers;

import engine.window.Window;
import game.general.Game;
import game.general.LoaderRes;
import screens.inGame.InGame;
import screens.inGame.LoadingScreen;

import java.io.*;

import static engine._SetsStrings.*;

public class StarterHelper {

    public static Game game;

    public static void startGame() {
        LoaderRes.loadAllRes();
        //ScreenHelper.setScreenActive(new MainMenu());

        //ScreenHelper.setScreenActive(new InGame());
        //game = new Game();
        //StarterHelper.game.loadMap("trest");
        ScreenHelper.setScreenActive(new LoadingScreen("trest"));
    }

    public static void endGame() {
        Window.close();
    }

    public static void save() {
        String fileName = P_SAVE_PATH+game.saveName+P_SAVE_EXE;
        FileOutputStream outputStream;
        try {
            outputStream = new FileOutputStream(fileName);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(StarterHelper.game);
            objectOutputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void load(String loadName) {
        ScreenHelper.setScreenActive(new LoadingScreen(P_SAVE_PATH+loadName));
    }
}
