package engine.audio;

import engine.window.Actor;
import engine.window.Window;
import engine.helpers.AudioHelper;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALC10;
import org.lwjgl.openal.ALCCapabilities;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static engine._SetsStrings.*;
import static org.lwjgl.openal.AL10.*;

public class Audio {

    private static AudioSource music ;
    private static final List<AudioSource> interfaces = new ArrayList<>();
    private static final List<AudioSource> voice = new ArrayList<>();

    private static int curInterface = 0;
    private static int curActor = 0;

    private static long device;
    public static void init(){
        device = ALC10.alcOpenDevice((ByteBuffer) null);
        ALCCapabilities deviceCaps = ALC.createCapabilities(device);
        long contex = ALC10.alcCreateContext(device,(IntBuffer) null);
        ALC10.alcMakeContextCurrent(contex);
        AL.createCapabilities(deviceCaps);
        for(int i = 0; i < I_AUDIO_SOURCE_INTERFACE_COUNT; i++){
            interfaces.add(new AudioSource(Window.getSets().soundVolume));
        }
        for(int i = 0; i < I_AUDIO_SOURCE_ACTOR_COUNT; i++){
            voice.add(new AudioSource(Window.getSets().soundVolume));
        }
        music = new AudioSource(Window.getSets().musicVolume);
    }

    public static void destroy(){
        AudioClip.destroyAll();
        for(AudioSource el : interfaces){
            el.destroy();
        }
        ALC10.alcCloseDevice(device);
    }

    public static void playInterfaceSound(String sound) {
        if(sound.equals("")) return;
        AudioClip clip = AudioClip.getClip(sound);
        AudioSource source = interfaces.get(curInterface);
        curInterface++;
        if(curInterface == interfaces.size()){
            curInterface = 0;
        }
        source.setClip(clip);
        source.play();
    }

    public static void playMusic(String sound) {
        AudioClip clip = AudioClip.getClip(sound);
        music.stop();
        music.setClip(clip);
        music.play();
    }

    public static void preload(String sound) {
        AudioClip.getClip(sound);
    }

    public static void stopMusic() {
        music.stop();
    }

    public static boolean musicStatus() {
        return alGetSourcei(music.getID(), AL_SOURCE_STATE) == AL_PLAYING;
    }

    public static void musicSetVolume(float fade) {
        music.setGain(fade);
    }

    public static void musicSetBaseGain(float gain){
        music.baseGain = gain;
        music.setGain(music.startGain);
    }

    public static void soundSetBaseGain(float val) {
        for(AudioSource el : interfaces){
            el.baseGain = val;
            el.setGain(el.startGain);
        }
        AudioHelper.setNewBaseGain(val);
    }

    public static void playAmbientSound(AudioSource voice, String name, float vol) {
        if(name.equals("")) return;
        AudioClip clip = AudioClip.getClip(name);
        voice.setClip(clip);
        voice.setGain(vol);
        voice.play();
    }

    public static void setAmbientVolume(AudioSource voice, float vol) {
        voice.setGain(vol);
    }

    public static void setActorSource(Actor el) {
        AudioSource source = voice.get(curActor);
        curActor++;
        if(curActor == voice.size()){
            curActor = 0;
        }
        source.actor = el;
        el.voice = source;
    }
}
