package engine.window;

import engine.input.MainInput;
import engine.input.interfaces.Element;
import engine.helpers.ActorHelper;
import engine.helpers.InterfaceHelper;

import java.util.ArrayList;
import java.util.List;

public abstract class Screen {
    public abstract void input(MainInput input);
    public abstract void logic();
    public abstract void render();
    public List<Element> interfaces = new ArrayList<>();
    public List<Actor> actors = new ArrayList<>();
    public void dispose(){
        ActorHelper.disposeAll(actors);
        InterfaceHelper.disposeAll(interfaces);
    }
}
