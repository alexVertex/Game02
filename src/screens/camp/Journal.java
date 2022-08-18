package screens.camp;

import engine.input.MainInput;
import engine.input.interfaces.Scroller;
import engine.render.Label;
import engine.window.Screen;
import engine.helpers.ActorHelper;
import engine.helpers.InterfaceHelper;
import engine.helpers.StarterHelper;
import game.inter.Simple;
import game.inter.Text;
import game.story.Quest;
import game.story.Task;

import java.util.ArrayList;
import java.util.List;

import static engine._SetsStrings.*;

public class Journal extends Screen{
    private final Screen inGame;
    List<Quest> quests = StarterHelper.game.getQuests();
    List<Task> tasks = new ArrayList<>();

    List<Text> questsNames = new ArrayList<>();
    List<Text> taskNames = new ArrayList<>();
    Simple chooser = new Simple(P_BOX_BACK_TEXTURE,-185,0,10002,200,20,0,0,0,1);
    int[] wheel = {-256,0,512,768};
    Scroller scroll = new Scroller(P_SCROLLER_TEXTURE,270-342,-30,10005,20,300,0,10,P_SOUND_INTERFACE_CLICK,wheel);
    int[] wheel1 = {256,0,512,768};
    Scroller scroll1 = new Scroller(P_SCROLLER_TEXTURE,270,-30,10005,20,300,0,10,P_SOUND_INTERFACE_CLICK,wheel1);

    public Journal(Screen parent){
        inGame = parent;
        ActorHelper.addActor(this,chooser);
        InterfaceHelper.addElement(this,scroll);
        InterfaceHelper.addElement(this,scroll1);

        for(int i = 0; i < 15;i++){
            Text text = new Text(S_FONT_SUPPORT_NAME, " ", -282,110-20*i,10002, Label.LEFT);
            questsNames.add(text);
            ActorHelper.addActor(this,text);
            text = new Text(S_FONT_SUPPORT_NAME, " ", -50,110-20*i,10002,Label.LEFT);
            taskNames.add(text);
            ActorHelper.addActor(this,text);
        }
        scroll.setMax(quests.size()-15);
        setUpGreed();
    }


    public void setUpGreed(){
        int start = scroll.getVal();
        int i = 0;
        int j = start;
        if(j < 0) j = 0;
        for (; j < quests.size() && i < questsNames.size(); i++) {
            Quest el = quests.get(j);
            questsNames.get(i).changeText(el.name);
            if(el.status == I_QUEST_FINISHED){
                questsNames.get(i).setColor(0,1,0);
            }
            j++;
        }
        for (; i < questsNames.size(); i++) {
            questsNames.get(i).changeText(" ");
        }
    }
    int choosen = -1;

    @Override
    public void input(MainInput input) {
        InterfaceHelper.work(this,input);
        if(scroll.getVal() != -1){
            setUpGreed();
        }
        if(scroll1.getVal() != -1){
            drawTask();
        }
        int underCursor = -1;
        double cursorX = input.getMouseX();
        double cursorY = input.getMouseY();
        if(Math.abs(cursorX-(-185)) < 100) {
            int i = 0;
            for (Text el : questsNames) {
                if(el.getText().equals(" ")) break;
                if(Math.abs(cursorY- (110-i*20)) < 10) {
                    underCursor = i;
                    if(input.isKeyJustReleased(0)){
                        setTasks(i);
                        choosen = i;
                    }
                }
                i++;

            }
        }
        if(underCursor == -1){
            chooser.setColor(0,0,0,0);
        } else {
            chooser.setColor(0.5f,0.5f,0.5f,1);
            chooser.setY(110-underCursor*20);

        }
    }

    private void setTasks(int i) {
        Quest loc = quests.get(i+scroll.getValNormal());
        tasks.clear();
        for(Task el : loc.getTasks()){
            if(el.isGoingToJournal()){
                tasks.add(el);
            }
        }

        scroll1.setMax(tasks.size()-15);
        drawTask();

    }

    private void drawTask(){
        int k = scroll1.getValNormal();
        int j = 0;
        for (; k < tasks.size() && j < taskNames.size(); j++,k++) {
            Task el = tasks.get(k);
            taskNames.get(j).changeText(el.name);
            if(el.status == I_QUEST_TASK_STATUS_COMPLETED) {
                taskNames.get(j).setColor(0, 1, 0);
            } else {
                taskNames.get(j).setDefColor();

            }
        }
        for (; j < taskNames.size(); j++) {
            taskNames.get(j).changeText(" ");
        }
    }
    @Override
    public void logic() {

    }

    @Override
    public void render() {

    }
}
