package screens.camp;

import engine.helpers.*;
import engine.input.MainInput;
import engine.input.interfaces.Scroller;
import engine.window.Screen;
import game.general.Event;
import game.inter.Box;
import game.inter.Simple;
import game.inter.Text;
import game.story.Quest;
import game.world.map.ResortArea;
import screens.inGame.InGame;

import java.util.ArrayList;
import java.util.List;

import static engine._SetsStrings.*;
import static engine._TextString.S_EVENT_OUTSIDE_RESORT_AREA;

public class Transit extends Screen{
    private final Screen inGame;
    List<ResortArea> resorts = StarterHelper.game.getResorts();
    List<ResortArea> mapResorts = StarterHelper.game.getResorts();

    List<Text> resortAreas = new ArrayList<>();

    Simple chooser = new Simple(P_BOX_BACK_TEXTURE,-185,0,10002,200,20,0,0,0,1);
    Simple chooser1 = new Simple(P_BOX_BACK_TEXTURE,45+50,0,10002,200+100,20,0,0,0,1);

    int[] wheel = {-256,0,512,768};
    Scroller scroll = new Scroller(P_SCROLLER_TEXTURE,270-342,-30,10005,20,300,0,10,P_SOUND_INTERFACE_CLICK,wheel);
    int[] wheel1 = {256,0,512,768};
    Scroller scroll1 = new Scroller(P_SCROLLER_TEXTURE,270,-30,10005,20,300,0,10,P_SOUND_INTERFACE_CLICK,wheel1);

    Box minimap = new Box(P_BOX_BORDER_TEXTURE,P_MINIMAPS_TEXTURES_PATH+StarterHelper.game.current.getMiniMap(),0-190,0-105,10005,150,150);
    List<Text> mappNames = new ArrayList<>();
    List<String> exist = new ArrayList<>();
    Simple resortAreaInMiniMap = new Simple("interface/minimapPlayer",45+50,-999,10012,8,8,0,1,0,1);

    List<Simple> minimapTasks = new ArrayList<>();
    List<Simple> minimapResorts = new ArrayList<>();
    public Transit(Screen parent){
        inGame = parent;
        ActorHelper.addActor(this,chooser);
        ActorHelper.addActor(this,minimap);
        ActorHelper.addActor(this,chooser1);
        ActorHelper.addActor(this,resortAreaInMiniMap);

        InterfaceHelper.addElement(this,scroll);
        InterfaceHelper.addElement(this,scroll1);

        for(int i = 0; i < 7;i++){
            Text text = new Text(S_FONT_SUPPORT_NAME, " ", -282,110-20*i,10002,0);
            mappNames.add(text);
            ActorHelper.addActor(this,text);
        }
        for(int i = 0; i < 15;i++){
            Text text = new Text(S_FONT_SUPPORT_NAME, " ", -50,110-20*i,10002,0);
            resortAreas.add(text);
            ActorHelper.addActor(this,text);
        }
        for(int i = 0; i < 15;i++) {
            Simple minimapPlayer = new Simple("interface/minimapPlayer",512-50,-999,10010,8,8,1,0.0f,0.9f);
            ActorHelper.addActor(this, minimapPlayer);
            minimapTasks.add(minimapPlayer);
        }
        for(int i = 0; i < 10;i++) {
            Simple minimapPlayer = new Simple("interface/minimapPlayer",512-50,-999,10010,8,8,1,0.0f,0.9f);
            ActorHelper.addActor(this, minimapPlayer);
            minimapResorts.add(minimapPlayer);
        }
        setUpGreed();
    }

    public void setUpGreed(){

        int start = scroll.getVal();
        int i = 0;
        int j = start;
        if(j < 0) j = 0;
        for (; j < resorts.size() && i < mappNames.size(); i++) {
            ResortArea el = resorts.get(j);
            if(!exist.contains(el.mapName)) {
                exist.add(el.mapName);
                mappNames.get(i).changeText(el.mapName);
            } else {
                i--;
            }
            j++;
        }
        for (; i < mappNames.size(); i++) {
            mappNames.get(i).changeText(" ");
        }
        scroll.setMax(mappNames.size()-7);
        if(exist.size() > 0) {
            setBonFires(0);
        }
    }

    @Override
    public void input(MainInput input) {
        InterfaceHelper.work(this,input);

        int underCursor = -1;
        double cursorX = input.getMouseX();
        double cursorY = input.getMouseY();

        if(Math.abs(cursorX-(-185)) < 100) {
            int i = 0;
            for (Text el : mappNames) {
                if(el.getText().equals(" ")) break;
                if(Math.abs(cursorY- (110-i*20)) < 10) {
                    underCursor = i;
                    if(input.isKeyJustReleased(0)){
                        setBonFires(i);
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

        int underCursor1 = -1;
        if(Math.abs(cursorX-(95)) < 150) {
            int i = 0;
            for (Text el : resortAreas) {
                if(el.getText().equals(" ")) break;
                if(Math.abs(cursorY- (110-i*20)) < 10) {
                    underCursor1 = i;
                    if(input.isKeyJustReleased(0)){
                        transit(underCursor1);
                    }
                }
                i++;
            }
        }
        if(underCursor1 == -1){
            chooser1.setColor(0,0,0,0);
            resortAreaInMiniMap.setY(-999);
        } else {
            chooser1.setColor(0.5f,0.5f,0.5f,1);
            chooser1.setY(110-underCursor1*20);
            resortAreaInMiniMap.setX(minimapResorts.get(underCursor1).getX());
            resortAreaInMiniMap.setY(minimapResorts.get(underCursor1).getY());
            resortAreaInMiniMap.setX(-190 -75 + (150 + 4) * (resorts.get(underCursor1).locX / StarterHelper.game.current.getWidth()));
            resortAreaInMiniMap.setY(-105 -75 + (150 + 4) * (resorts.get(underCursor1).locY / StarterHelper.game.current.getHeight()));
        }
    }

    private void transit(int underCursor1) {
        if(StarterHelper.game.inResort) {
            dispose();
            inGame.dispose();
            ((InGame) ScreenHelper.getScreenActive()).secondary = null;
            ResortArea area = mapResorts.get(underCursor1);
            if (!area.mapID.equals(StarterHelper.game.current.getID())) {
                StarterHelper.game.getPlayer().setPos((int) area.locX, (int) area.locY);
                CameraHelper.setPos(-(int) area.locX, -(int) area.locY);
            } else {
                StarterHelper.game.changeMap(area.mapID, (int) area.locX, (int) area.locY);
            }


            StarterHelper.game.restart(false);
            StarterHelper.save();
        } else {
            Event.makeEvent(S_EVENT_OUTSIDE_RESORT_AREA);
        }
    }

    private void setBonFires(int i) {
        mapResorts.clear();
        String mapName = exist.get(i);
        for(ResortArea el : resorts){
            if(el.mapName.equals(mapName)){
                mapResorts.add(el);
            }
        }
        int j = 0;
        int k = 0;
        for(; k < minimapResorts.size() && j < resorts.size();k++,j++) {
            ResortArea el = resorts.get(j);
            Simple simple = minimapResorts.get(k);
            simple.setColor(1, 0.8f, 0, 1);
            simple.setX(-190 -75 + (150 + 4) * (el.locX / StarterHelper.game.current.getWidth()));
            simple.setY(-105 -75 + (150 + 4) * (el.locY / StarterHelper.game.current.getHeight()));
        }
        for(; k < minimapResorts.size();k++) {
            Simple simple = minimapResorts.get(k);
            simple.setColor(0,0,0,0);
        }
        scroll1.setMax(mapResorts.size() - 15);
        Quest.setTransitBlips(minimapTasks,StarterHelper.game.activeQuests,resorts.get(0).mapID);
        drawBonFires();
    }

    private void drawBonFires() {
        int k = scroll1.getValNormal();
        int j = 0;
        for (; k < mapResorts.size() && j < resortAreas.size(); j++,k++) {
            ResortArea el = mapResorts.get(k);
            resortAreas.get(j).changeText(el.name);

        }
        for (; j < resortAreas.size(); j++) {
            resortAreas.get(j).changeText(" ");
        }
    }

    @Override
    public void logic() {

    }

    @Override
    public void render() {

    }
}
