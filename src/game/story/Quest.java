package game.story;

import engine.helpers.StarterHelper;
import game.inter.Simple;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static engine._SetsStrings.*;

public class Quest implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;


    public static void LoadQuest(List<String> data, String name){
        Quest quest = new Quest();
        quest.name = data.get(0);
        quest.mapID = data.get(1);

        int count = Integer.parseInt(data.get(2));
        for(int i = 0; i < count;i++){
            String[] dat = data.get(i+3).split(";");
            int type = Integer.parseInt(dat[0]);
            if(type == I_QUEST_TASK_START_UP){
                Task task = new Task(type,"",0,0,dat[4].replaceAll(",",";"),"",0);
                quest.taskList.add(task);
            } else if(type == I_QUEST_TASK_FINISH){
                Task task = new Task(type,"",0,0,"","",Integer.parseInt(dat[6]));
                quest.taskList.add(task);
            } else if(type > I_QUEST_TASK_FINISH){
                Task task = new Task(type,dat[1].replaceAll(",",";"),Integer.parseInt(dat[2]),Integer.parseInt(dat[3]),dat[4].replaceAll(",",";"),dat[5],Integer.parseInt(dat[6]));
                quest.taskList.add(task);
            }
        }
        dataBase.put(name,quest);

    }

    static HashMap<String,Quest> dataBase = new HashMap<>();


    public String name, mapID;
    public int status;
    List<Task> taskList = new ArrayList<>();
    public Quest() {    }

    public Quest(String id) {
        Quest copy = dataBase.get(id);
        for(Task el : copy.taskList){
            taskList.add(new Task(el));
        }
        name = copy.name;
        mapID = copy.mapID;
    }
    public static void mainControl(List<Quest> activeQuests, List<Quest> completedQuests, String mapID) {
        for (int i = 0; i < activeQuests.size(); i++) {
            Quest el = activeQuests.get(i);
            if(el.mapID.equals(mapID)) {
                el.logic();
                if (el.status == I_QUEST_FINISHED) {
                    i--;
                    activeQuests.remove(el);
                    completedQuests.add(el);
                }
            }
        }
    }

    private static void setBlips(List<Simple> minimapTasks, List<Quest> activeQuests, String mapID, int startX, int startY, int offsetStart, int offsetAdd){
        int cur = 0;
        for(Quest el : activeQuests){
            if(!el.mapID.equals(mapID))continue;
            int[][] coordsList = el.getCoordsList();
            for(; cur < coordsList.length && cur < minimapTasks.size();cur++) {
                Simple simple = minimapTasks.get(cur);
                simple.setX(startX - offsetStart + offsetAdd * (coordsList[cur][0] / StarterHelper.game.current.getWidth()));
                simple.setY(startY - offsetStart + offsetAdd * (coordsList[cur][1] / StarterHelper.game.current.getHeight()));
                simple.setColor(1,0,0.9f,1);
            }
        }
        for(;cur < minimapTasks.size();cur++){
            Simple simple = minimapTasks.get(cur);
            simple.setColor(0,0,0,0);
        }
    }

    public static void setMinimapsBlips(List<Simple> minimapTasks, List<Quest> activeQuests, String mapID) {
        setBlips(minimapTasks,activeQuests,mapID,512,384,150,154);
    }

    public static void setTransitBlips(List<Simple> minimapTasks, List<Quest> activeQuests, String mapID) {
        setBlips(minimapTasks,activeQuests,mapID,-190,-105,75,154);
    }

    public void logic() {
        if(mapID.equals(StarterHelper.game.current.getID()))
        for(Task el : taskList){
            el.logic(this);
        }
    }

    public int[][] getCoordsList() {
        List<Task> active = new ArrayList<>();
        for(Task el : taskList){
            if(el.status == 1){
                active.add(el);
            }
        }
        int [][] coords = new int[active.size()][2];
        for(int i = 0; i < active.size();i++){
            coords[i][0] = active.get(i).mapX;
            coords[i][1] = active.get(i).mapY;
        }
        return coords;
    }

    public List<Task> getTasks() {
        return taskList;
    }
}
