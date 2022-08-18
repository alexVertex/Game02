package game.story;

import engine.core.Mathp;
import engine.window.Actor;
import game.general.Event;
import engine.helpers.AudioHelper;
import engine.helpers.StarterHelper;
import game.players.Player;
import game.world.specialObjects.Activator;

import java.io.Serial;
import java.io.Serializable;

import static engine._SetsStrings.*;
import static engine._TextString.*;

public class Task implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String params;
    public String name;

    int mapX,mapY;
    int type, x,y,rast;
    public int status = I_QUEST_TASK_STATUS_DISABLED;
    String next;
    int needCount;

    public Task(int type, String params, int mapX, int mapY, String next,String name,int needCount){
        this.type = type;
        this.params = params;
        this.mapX = mapX;
        this.mapY = mapY;
        this.next = next;
        this.name = name;
        if(type == I_QUEST_TASK_MOVE_TO || type == I_QUEST_TASK_MOVE_OUT){
            String[] split = this.params.split(";");
            this.x = Integer.parseInt(split[0])*I_ACTOR_DEF_SIZE;
            this.y = Integer.parseInt(split[1])*I_ACTOR_DEF_SIZE;
            this.rast = Integer.parseInt(split[2])*I_ACTOR_DEF_SIZE;
        }
        this.needCount = needCount;
    }

    public Task(Task el) {
        this.type = el.type;
        this.x = el.x;
        this.y = el.y;
        this.rast = el.rast;
        this.mapX = el.mapX;
        this.mapY = el.mapY;
        this.next = el.next;
        if(type == I_QUEST_TASK_START_UP){
            status = I_QUEST_TASK_STATUS_ENABLED;
        }
        this.name = el.name;
        this.needCount = el.needCount;
        this.params = el.params;
    }

    public void logic(Quest quest) {
        if(status == I_QUEST_TASK_STATUS_ENABLED){
            switch (type) {
                case I_QUEST_TASK_MOVE_TO -> {
                    double curRast = Mathp.rast(StarterHelper.game.getPlayer().getX(), StarterHelper.game.getPlayer().getY(), x, y);
                    if (curRast < rast) {
                        completeTask(quest);
                    }
                }
                case I_QUEST_TASK_MOVE_OUT -> {
                    double curRast1 = Mathp.rast(StarterHelper.game.getPlayer().getX(), StarterHelper.game.getPlayer().getY(), x, y);
                    if (curRast1 > rast) {
                        completeTask(quest);
                    }
                }
                case I_QUEST_TASK_ITEM, I_QUEST_TASK_KILL -> {
                    String[] ids = params.split(";");
                    for (String id : ids) {
                        Actor a = Actor.base.getOrDefault(id, null);
                        if (a != null) {
                            return;
                        }
                    }
                    completeTask(quest);
                }
                case I_QUEST_TASK_SEE -> {
                    boolean good = true;
                    String[] ids1 = params.split(";");
                    for (String id : ids1) {
                        Actor a = Actor.base.getOrDefault(id, null);
                        if (a != null && !((Player) a).visible) {
                            good = false;
                            break;
                        }
                    }
                    if (good) {
                        completeTask(quest);
                    }
                }
                case I_QUEST_TASK_ACTIVATOR -> {
                    boolean good1 = true;
                    String[] ids2 = params.split(";");
                    for (String id : ids2) {
                        Actor a = Actor.base.getOrDefault(id, null);
                        if (a != null && ((Activator) a).getState()) {
                            good1 = false;
                            break;
                        }
                    }
                    if (good1) {
                        completeTask(quest);
                    }
                }
                case I_QUEST_TASK_START_UP, I_QUEST_TASK_FINISH -> completeTask(quest);
            }
        }
    }

    private void completeTask(Quest quest){
        if(type == I_QUEST_TASK_START_UP){
            AudioHelper.playSoundInterface(P_SOUND_INTERFACE_QUEST_ADDED);
            Event.makeEvent(S_EVENT_TASK_ADD_QUEST + quest.name);
            activateTasks(quest);
        } else if(type == I_QUEST_TASK_FINISH){
            AudioHelper.playSoundInterface(P_SOUND_INTERFACE_QUEST_COMPLETE);
            quest.status = I_QUEST_FINISHED;
            Event.makeEvent(S_EVENT_TASK_COMPLETE_QUEST + quest.name);
        } else {
            Event.makeEvent(S_EVENT_TASK_COMPLETE_TASK + name);
            AudioHelper.playSoundInterface(P_SOUND_INTERFACE_QUEST_COMPLETE_TASK);
            activateTasks(quest);
        }
        status = I_QUEST_TASK_STATUS_COMPLETED;
    }

    private void activateTasks(Quest quest){
        if(next.equals("")) return;
        String[] split = next.split(";");
        for(String el : split){
            int val = Integer.parseInt(el);
            quest.taskList.get(val).needCount--;
            if(quest.taskList.get(val).needCount == 0) {
                quest.taskList.get(val).status = I_QUEST_TASK_STATUS_ENABLED;
                if (quest.taskList.get(val).type != I_QUEST_TASK_FINISH) {
                    Event.makeEvent(S_EVENT_TASK_ADD_TASK + quest.taskList.get(val).name);
                }
            }
        }
    }

    public boolean isGoingToJournal() {
        return status > I_QUEST_TASK_STATUS_DISABLED && type > I_QUEST_TASK_FINISH;
    }
}
