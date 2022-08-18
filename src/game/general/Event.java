package game.general;

import java.util.ArrayList;
import java.util.List;

import static engine._SetsStrings.I_EVENT_TIME_END_TIME;

public class Event {

    public static boolean eventChanged = false;
    static int time = 0;
    static List<String> events = new ArrayList<>();

    public static void makeEvent(String text){
        boolean canAdd = events.size() == 0 || !events.contains(text);
        if(canAdd) {
            events.add(text);
            if(events.size() == 1){
                eventChanged = true;
            }
        }
    }

    public static void logic() {
        if(events.size() > 0){
            time ++;
            if(time > I_EVENT_TIME_END_TIME){
                time = 0;
                events.remove(0);
                eventChanged = true;
            }
        }
    }

    public static String getLastEvent() {
        if(events.size() > 0){
            return events.get(0);
        }
        return " ";
    }
}
