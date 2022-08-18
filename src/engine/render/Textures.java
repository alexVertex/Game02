package engine.render;

import static engine._SetsStrings.*;
import static org.lwjgl.opengl.GL13.*;
import org.lwjgl.BufferUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;

public class Textures {
    static HashMap<String, Textures> texturesHashMap = new HashMap<>();
    public int id;

    public Textures(String fileName){
        String totalPath = P_TEXTURE_PATH+fileName+P_TEXTURE_EXE;
        BufferedImage bi;
        try {
            int width;
            int height;
            bi = ImageIO.read((new File(totalPath)));
            width = bi.getWidth();
            height = bi.getHeight();
            int[] pixelsRaw = bi.getRGB(0,0,width,height,null,0,width);
            ByteBuffer pixels = BufferUtils.createByteBuffer(width*height*4);
            for(int i = 0; i < width;i++){
                for(int j = 0; j < height;j++){
                    int index = i*height+j;
                    int pixel = pixelsRaw[index];
                    pixels.put((byte) ((pixel >> 16 ) & 0xFF));
                    pixels.put((byte) ((pixel >> 8 ) & 0xFF));
                    pixels.put((byte) (pixel & 0xFF));
                    pixels.put((byte) ((pixel >> 24 ) & 0xFF));
                }
            }
            pixels.flip();
            id = glGenTextures();
            glBindTexture(GL_TEXTURE_2D, id);
            glTexParameterf(GL_TEXTURE_2D,GL_TEXTURE_MIN_FILTER,GL_LINEAR);
            glTexParameterf(GL_TEXTURE_2D,GL_TEXTURE_MAG_FILTER,GL_LINEAR);
            glTexParameterf(GL_TEXTURE_2D,GL_TEXTURE_WRAP_S,GL_REPEAT);
            glTexParameterf(GL_TEXTURE_2D,GL_TEXTURE_WRAP_T,GL_REPEAT);
            glTexImage2D(GL_TEXTURE_2D,0,GL_RGBA,width,height,0,GL_RGBA,GL_UNSIGNED_BYTE,pixels);
        } catch (IOException e){
            System.err.println(totalPath);
            e.printStackTrace();
        }
    }

    public static void bind(String texture) {
        Textures found = texturesHashMap.getOrDefault(texture,null);
        if(found == null){
            found = new Textures(texture);
            texturesHashMap.put(texture,found);
        }
        glBindTexture(GL_TEXTURE_2D, found.id);
    }
}
