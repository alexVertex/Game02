package engine.helpers;

import engine.core.Mathp;
import engine.audio.Audio;
import engine.window.Window;
import game.players.Player;
import game.world.specialObjects.SoundSource;

import java.util.List;

import static engine._SetsStrings.*;

public class AudioHelper {

    public static void playSoundInterface(String sound){
        Audio.playInterfaceSound(sound);
    }

    public static void playMusic(String music){Audio.playMusic(music);}

    public static void stopMusic() {
        Audio.stopMusic();
        fadeSpeed = 0;
        fade = F_MUSIC_FADE_MAX;
        Audio.musicSetVolume(1);
    }

    public static boolean isMusicPlaying() {
        return Audio.musicStatus();
    }

    public static float fade = F_MUSIC_FADE_MAX;
    public static float fadeSpeed = 0;
    public static float soundBaseGain = Window.getSets().soundVolume;

    public static void startMusicFade() {
        fadeSpeed = -1;
    }
    public static void startMusicRaise() {
        fadeSpeed = +1;
    }
    public static void logic(List<SoundSource> ambient) {
        if(fadeSpeed != 0){
            fade += fadeSpeed;
            if(fade < 0){
                fadeSpeed = 0;
                fade = F_MUSIC_FADE_MAX;
                Audio.musicSetVolume(1);
                Audio.stopMusic();
            } else if (fade > F_MUSIC_FADE_MAX){
                fade = F_MUSIC_FADE_MAX;
                Audio.musicSetVolume(1);
                fadeSpeed = 0;
            } else {
                Audio.musicSetVolume(fade/F_MUSIC_FADE_MAX);
            }
        }
        for(SoundSource el : ambient){
            boolean noSource = el.voice == null;
            double rast = Mathp.rast(-CameraHelper.getX(), -CameraHelper.getY(), el.getX(), el.getY());
            float vol = (float) (1 - rast / el.voiceRast);
            if (vol < 0) vol = 0;
            if(noSource) {
                if(vol > 0) {
                    Audio.setActorSource(el);
                }
            } else {
                el.voice.baseGain = soundBaseGain;
                if (vol < 0) vol = 0;
                if (!el.voice.isPlaying()) {
                    if (vol > 0) {
                        Audio.playAmbientSound(el.voice, el.sound, vol);
                    }
                } else {
                    Audio.setAmbientVolume(el.voice, vol);
                }
            }
        }
    }

    public static boolean musicFading() {
        return fade != F_MUSIC_FADE_MAX;
    }

    public static void setNewBaseGain(float val) {
        soundBaseGain = val;
    }

    public static void actorVoice(Player el) {
        boolean noSource = el.voice == null;
        if(noSource) {
            if (el.mustSay) {
                Audio.setActorSource(el);
            }
        } else {
            el.voice.baseGain = soundBaseGain;
            double rast = Mathp.rast(-CameraHelper.getX(),-CameraHelper.getY(),el.getX(),el.getY());
            float vol = (float) (1-rast/ F_SOUND_PLAYER_DIST);
            if(vol < 0) vol = 0;
            if(el.mustSay || el.voice.isPlaying()){
                if (!el.voice.isPlaying()) {
                    if(vol > 0) {
                        Audio.playAmbientSound(el.voice, el.sayLine, vol);
                    }
                    el.mustSay = false;
                } else {
                    Audio.setAmbientVolume(el.voice,vol);
                }
            }
        }
    }

    public static void generalFadeMusicControl(boolean inBattle) {
        if(inBattle) {
            if(AudioHelper.musicFading()){
                AudioHelper.startMusicRaise();
            } else {
                AudioHelper.stopMusic();
            }
        } else {
            AudioHelper.startMusicFade();
        }
    }

    public static void preload(String music) {
        Audio.preload(music);
    }
}
