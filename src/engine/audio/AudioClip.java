package engine.audio;

import org.lwjgl.stb.STBVorbis;
import org.lwjgl.system.MemoryStack;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.HashMap;

import static engine._SetsStrings.*;
import static org.lwjgl.openal.AL10.*;

public class AudioClip {
    final int id;

    private static final HashMap<String,AudioClip> clips = new HashMap<>();

    public AudioClip(String file){
        id = alGenBuffers();
        MemoryStack.stackPush();
        IntBuffer channels = MemoryStack.stackMallocInt(1);
        MemoryStack.stackPush();
        IntBuffer sample = MemoryStack.stackMallocInt(1);
        ShortBuffer raw = STBVorbis.stb_vorbis_decode_filename(P_AUDIO_PATH+file+P_AUDIO_EXE,channels,sample);
        int channelsC = channels.get();
        int sampleC = sample.get();
        MemoryStack.stackPop();
        MemoryStack.stackPop();

        int format = -1;
        if(channelsC == 1) format = AL_FORMAT_MONO16;
        else if(channelsC == 2) format = AL_FORMAT_STEREO16;
        assert raw != null;
        alBufferData(id,format,raw,sampleC);
        clips.put(file,this);
    }

    public static void destroyAll() {
        for (AudioClip el : clips.values()){
            el.destroy();
        }
    }

    public static AudioClip getClip(String sound) {
        AudioClip clip = clips.getOrDefault(sound, null);
        if(clip == null){
            clip = new AudioClip(sound);
        }
        return clip;
    }

    public void destroy(){alDeleteBuffers(id);}
}
