package engine.helpers;

import engine._SetsStrings;
import engine.input.MainInput;
import engine.input.interfaces.Element;
import engine.window.Screen;

import java.util.List;

public class InterfaceHelper {

    public static void addElement(Screen screen, Element element) {
        screen.interfaces.add(element);
        element.add();
    }

    static Element locked = null;
    public static void work(Screen screen,MainInput input) {
        if(locked != null){
            boolean lock = locked.work(input);
            if(!lock){
                locked = null;
            }
        } else {
            for (Element el : screen.interfaces) {
                if(el.state == _SetsStrings.I_ELEMENT_STATE_DISABLED) continue;
                boolean lock = el.work(input);
                if (lock) {
                    locked = el;
                    break;
                }
            }
        }
    }

    public static void remove(Element element) {
        element.dispose();
    }

    public static void disposeAll(List<Element> interfaces) {
        for(Element el : interfaces){
            el.dispose();
        }
    }

    public static boolean hasFocused() {
        return locked != null;
    }
}
