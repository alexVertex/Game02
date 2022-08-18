package engine.window;

import engine.helpers.ControlHelper;
import engine.render.Sprite;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static engine._SetsStrings.*;

public class WindowSettings {

    public CharSequence name = S_WINDOW_NAME;
    int[][] videoModes = {{640,480},{800,600},{1024,768},{1280,960}};
    public int vidRes = I_SETS_DEF_VIDRES;
    int width = I_SETS_DEF_WIDTH;
    int height = I_SETS_DEF_HEIGHT;
    public double frameCap = D_WINDOW_UPS_CAP;
    public int vidMode = I_SETS_DEF_VIDMODE;
    public float brightness = F_SETS_DEF_BRIGHTNESS;
    public float musicVolume = F_SETS_DEF_MUSIC_VOLUME;
    public float soundVolume = F_SETS_DEF_SOUND_VOLUME;
    public int showDecals = I_SETS_DEF_SHOW_DECALS;

    public WindowSettings(){
        try {
            List<String> strings = new ArrayList<>();
            BufferedReader br;

            br = new BufferedReader(new FileReader(P_SETS_PATH));
            String line;
            while ((line = br.readLine()) != null) {
                strings.add(line);
            }
            br.close();

            vidRes = Integer.parseInt(strings.get(I_SETS_READ_LINE_VIDRES));
            width = videoModes[vidRes][0];
            height = videoModes[vidRes][1];
            vidMode = Integer.parseInt(strings.get(I_SETS_READ_LINE_VIDMODE));
            brightness = Float.parseFloat(strings.get(I_SETS_READ_LINE_BRIGHTNESS));
            musicVolume = Float.parseFloat(strings.get(I_SETS_READ_LINE_MUSIC_VOLUME));
            soundVolume = Float.parseFloat(strings.get(I_SETS_READ_LINE_SOUND_VOLUME));
            showDecals = Integer.parseInt(strings.get(I_SETS_READ_LINE_SHOW_DECALS));
            for(int i = 0; i < ControlHelper.codes.length;i++){
                ControlHelper.changeCode(i,Integer.parseInt(strings.get(6+i)));
            }
        } catch (Exception e){
            restore();
        }
    }

    private void restore() {
        vidRes = I_SETS_DEF_VIDRES;
        width = I_SETS_DEF_WIDTH;
        height = I_SETS_DEF_HEIGHT;
        vidMode = I_SETS_DEF_VIDMODE;
        brightness = F_SETS_DEF_BRIGHTNESS;
        musicVolume = F_SETS_DEF_MUSIC_VOLUME;
        soundVolume = F_SETS_DEF_SOUND_VOLUME;
        showDecals = I_SETS_DEF_SHOW_DECALS;
        makeFile();
    }

    public void makeFile() {
        if(brightness < 1)
            Sprite.setGlobalBrightness(brightness);
        BufferedWriter bw;
        try {
            bw = new BufferedWriter(new FileWriter(P_SETS_PATH,false));
            bw.write(vidRes+"\n");
            bw.write(vidMode+"\n");
            bw.write(brightness+"\n");
            bw.write(musicVolume+"\n");
            bw.write(soundVolume+"\n");
            bw.write(showDecals+"\n");
            for(int i = 0; i < ControlHelper.codes.length;i++){
                bw.write(ControlHelper.codes[i]+"\n");
            }
            bw.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public float mult() {
        return (float)width/(float)I_SETS_DEF_WIDTH;
    }
}
