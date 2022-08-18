package engine.audio;

import engine.window.Actor;

import static org.lwjgl.openal.AL10.*;

public class AudioSource {
    public Actor actor;
    public AudioClip clip;
    public float startGain = 1;
    public float baseGain;

    private final int id;

    public AudioSource(float gain){
        id = alGenSources();
        baseGain = gain;
        setGain(1);
    }

    public void play(){
        alSourcePlay(id);
    }

    public void setClip(AudioClip c){
        clip = c;
        alSourcei(id,AL_BUFFER,c.id);
    }

    public void pause(){
        alSourcePause(id);
    }

    public void stop(){
        alSourceStop(id);
    }

    public void setGain(float v){
        alSourcef(id, AL_GAIN,v*v*baseGain);
        startGain = v;
    }

    public void destroy(){
        stop();
        alDeleteSources(id);
    }

    public int getID() {
        return id;
    }

    public boolean isPlaying() {
        return alGetSourcei(getID(), AL_SOURCE_STATE) == AL_PLAYING;
    }
}
