package engine.helpers;

import engine.window.Actor;
import engine.window.Screen;
import game.inter.Simple;

import java.util.List;

public class ActorHelper {
    public static void addActor(Screen screen, Actor actor){
        screen.actors.add(actor);
        actor.init();
    }
    public static void disposeAll(List<Actor> actors) {
        for(Actor el : actors){
            el.dispose();
        }
    }

    public static void hide(Actor activate) {
        activate.hide();
    }

    public static void show(Actor activate) {
        activate.show();
    }

    public static void remove(Simple el) {
        el.dispose();
    }
}
