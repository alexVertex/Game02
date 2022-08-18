package game.story;

import engine.helpers.StarterHelper;
import game.general.Event;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static engine._SetsStrings.*;
import static engine._TextString.*;

public class DialogData implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public static HashMap<String, DialogData> getDialog(String id) {
        return dialogs.get(id);
    }

    public void resultsUse() {
        played = true;
        for (Line el : results) {
            String[] data = el.text.split(";");
            switch (el.talker){
                case I_DIALOG_RESULT_GAIN_MONEY:
                    StarterHelper.game.gold += Integer.parseInt(data[0]);
                    Event.makeEvent(S_EVENT_GAIN_MONEY + data[0]);
                    break;
            }
        }
    }
    public boolean canGo() {
        boolean good = true;
        for(Line el : startConditions){
            String[] data = el.text.split(";");
            switch (el.talker){
                case I_DIALOG_COND_NO_TALKED:
                    good = !played;
                    break;
                case I_DIALOG_COND_HAVE_ITEMS_IN_INVENTORY:
                    for(String ele : data) {
                        if (!StarterHelper.game.isItemInInventory(ele)) {
                            return false;
                        }
                    }
                    break;
            }
            if(!good){
                return false;
            }
        }
        return true;
    }

    public static class Line implements Serializable{
        @Serial
        private static final long serialVersionUID = 1L;

        public String text;
        public int talker;

        public Line(String text,int talker){
            this.text = text;
            this.talker = talker;
        }
        public Line(Line copy){
            this.text = copy.text;
            this.talker = copy.talker;
        }
    }

    static HashMap<String,HashMap<String,DialogData>> dialogs = new HashMap<>();
    public static void loadDialog(List<String> data,String name){
        int cursor = 0;
        HashMap<String,DialogData> dialogsCompile = new HashMap<>();
        while (cursor < data.size()) {
            String topic = data.get(cursor++);
            DialogData dialogData = new DialogData(topic, true);
            String[] needs = data.get(cursor++).split(";");
            if(needs.length > 1) {
                for (int i = 0; i < needs.length-1; i += 2) {
                    dialogData.startConditions.add(new Line(needs[i + 1].replaceAll(",", ";"), Integer.parseInt(needs[i])));
                }
            }
            String[] resols = data.get(cursor++).split(";");
            if(resols.length > 1) {
                for (int i = 0; i < resols.length-1; i += 2) {
                    dialogData.results.add(new Line(resols[i + 1].replaceAll(",", ";"), Integer.parseInt(resols[i])));
                }
            }
            int lines = Integer.parseInt(data.get(cursor++));
            for (int i = 0; i < lines; i++) {
                String[] dataLine = data.get(cursor++).split(";");
                dialogData.lines.add(new Line(dataLine[1], Integer.parseInt(dataLine[0])));
            }
            dialogsCompile.put(topic,dialogData);

        }
        dialogs.put(name,dialogsCompile);
    }

    public boolean played = false;
    public List<Line> lines = new ArrayList<>();
    public List<Line> startConditions = new ArrayList<>();
    public List<Line> results = new ArrayList<>();

    public DialogData(String name, boolean t){
        this.name = name;
    }
    public String name;
}
